// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.party.server.graphql;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.server.command.GetCustomerCommand;
import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.server.command.GetEmployeeCommand;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.server.command.GetCompanyCommand;
import com.echothree.control.user.party.server.command.GetDateTimeFormatCommand;
import com.echothree.control.user.party.server.command.GetDepartmentCommand;
import com.echothree.control.user.party.server.command.GetDepartmentsCommand;
import com.echothree.control.user.party.server.command.GetDivisionCommand;
import com.echothree.control.user.party.server.command.GetDivisionsCommand;
import com.echothree.control.user.party.server.command.GetLanguageCommand;
import com.echothree.control.user.party.server.command.GetPartyAliasCommand;
import com.echothree.control.user.party.server.command.GetPartyAliasTypeCommand;
import com.echothree.control.user.party.server.command.GetPartyAliasTypesCommand;
import com.echothree.control.user.party.server.command.GetPartyAliasesCommand;
import com.echothree.control.user.party.server.command.GetPartyRelationshipCommand;
import com.echothree.control.user.party.server.command.GetPartyRelationshipsCommand;
import com.echothree.control.user.party.server.command.GetPartyTypeCommand;
import com.echothree.control.user.party.server.command.GetPartyTypesCommand;
import com.echothree.control.user.party.server.command.GetTimeZoneCommand;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.server.command.GetVendorCommand;
import com.echothree.control.user.warehouse.server.command.GetWarehouseCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.GraphQlSecurityCommand;
import graphql.schema.DataFetchingEnvironment;
import javax.naming.NamingException;

public interface PartySecurityUtils {

    static boolean getHasPartyTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPartyTypeCommand.class);
    }

    static boolean getHasPartyTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPartyTypesCommand.class);
    }

    static boolean getHasPartyAliasTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPartyAliasTypeCommand.class);
    }

    static boolean getHasPartyAliasTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPartyAliasTypesCommand.class);
    }

    static boolean getHasPartyAliasAccess(final DataFetchingEnvironment env, final Party targetParty) {
        try {
            var commandForm = PartyUtil.getHome().getGetPartyAliasForm();

            commandForm.setPartyName(targetParty.getLastDetail().getPartyName());

            return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPartyAliasCommand.class, commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    static boolean getHasPartyAliasesAccess(final DataFetchingEnvironment env, final Party targetParty) {
        try {
            var commandForm = PartyUtil.getHome().getGetPartyAliasesForm();

            commandForm.setPartyName(targetParty.getLastDetail().getPartyName());

            return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPartyAliasesCommand.class, commandForm);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    static boolean getHasPartyRelationshipAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPartyRelationshipCommand.class);
    }

    static boolean getHasPartyRelationshipsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPartyRelationshipsCommand.class);
    }

    static boolean getHasLanguageAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetLanguageCommand.class);
    }

    static boolean getHasTimeZoneAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetTimeZoneCommand.class);
    }

    static boolean getHasDateTimeFormatAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetDateTimeFormatCommand.class);
    }

    static boolean getHasDivisionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetDivisionsCommand.class);
    }

    static boolean getHasDepartmentsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetDepartmentsCommand.class);
    }

    static boolean getHasPartyAccess(final DataFetchingEnvironment env, final Party targetParty) {
        var partyDetail = targetParty.getLastDetail();
        var partyTypeEnum = PartyTypes.valueOf(partyDetail.getPartyType().getPartyTypeName());
        Class<? extends GraphQlSecurityCommand> command;
        BaseForm baseForm = null;

        switch(partyTypeEnum) {
            case CUSTOMER ->
            {
                command = GetCustomerCommand.class;

                // GetCustomerCommand has a security() function that needs the form to be available.
                try {
                    var commandForm = CustomerUtil.getHome().getGetCustomerForm();

                    commandForm.setPartyName(partyDetail.getPartyName());
                    baseForm = commandForm;
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
            case EMPLOYEE ->
            {
                command = GetEmployeeCommand.class;

                // GetEmployeeCommand has a security() function that needs the form to be available.
                try {
                    var commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

                    commandForm.setPartyName(partyDetail.getPartyName());
                    baseForm = commandForm;
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
            case VENDOR ->
            {
                command = GetVendorCommand.class;

                // GetVendorCommand has a security() function that needs the form to be available.
                try {
                    var commandForm = VendorUtil.getHome().getGetVendorForm();

                    commandForm.setPartyName(partyDetail.getPartyName());
                    baseForm = commandForm;
                } catch (NamingException ex) {
                    throw new RuntimeException(ex);
                }
            }
            case COMPANY ->
            {
                command = GetCompanyCommand.class;
            }
            case DIVISION ->
            {
                command = GetDivisionCommand.class;
            }
            case DEPARTMENT ->
            {
                command = GetDepartmentCommand.class;
            }
            case WAREHOUSE ->
            {
                command = GetWarehouseCommand.class;
            }
            default -> throw new RuntimeException("Unhandled PartyType: " + partyTypeEnum);
        };

        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(command, baseForm);
    }

}
