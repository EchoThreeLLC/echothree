// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.GetEmployeeResultsFacetsForm;
import com.echothree.control.user.search.common.result.GetEmployeeResultsFacetsResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;

public class GetEmployeeResultsFacetsCommand
        extends BaseGetResultsFacetsCommand<GetEmployeeResultsFacetsForm, GetEmployeeResultsFacetsResult> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Employee.name(), SecurityRoles.Search.name())
                ))
        ));
    }

    /** Creates a new instance of GetEmployeeResultsFacetsCommand */
    public GetEmployeeResultsFacetsCommand(UserVisitPK userVisitPK, GetEmployeeResultsFacetsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION);
    }

    @Override
    protected BaseResult execute() {
        return execute(ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name(), SearchKinds.EMPLOYEE.name(),
                SearchResultFactory.getGetEmployeeResultsFacetsResult());
    }

}
