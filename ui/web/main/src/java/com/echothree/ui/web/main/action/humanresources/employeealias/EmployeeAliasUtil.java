// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.employeealias;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetPartyAliasForm;
import com.echothree.control.user.party.common.result.GetPartyAliasResult;
import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.GetEmployeeForm;
import com.echothree.control.user.employee.common.result.GetEmployeeResult;
import com.echothree.model.control.employee.common.transfer.EmployeeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

public class EmployeeAliasUtil {

    private EmployeeAliasUtil() {
        super();
    }

    private static class EmployeeAliasUtilHolder {
        static EmployeeAliasUtil instance = new EmployeeAliasUtil();
    }

    public static EmployeeAliasUtil getInstance() {
        return EmployeeAliasUtilHolder.instance;
    }

    public void setupEmployee(HttpServletRequest request, String partyName)
            throws NamingException {
        GetEmployeeForm commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

        commandForm.setPartyName(partyName);

        CommandResult commandResult = EmployeeUtil.getHome().getEmployee(MainBaseAction.getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEmployeeResult result = (GetEmployeeResult)executionResult.getResult();
            EmployeeTransfer employee = result.getEmployee();

            if(employee != null) {
                request.setAttribute(AttributeConstants.EMPLOYEE, employee);
            }
        }
    }

    public void setupEmployee(HttpServletRequest request)
            throws NamingException {
        setupEmployee(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public void setupPartyAliasTransfer(HttpServletRequest request, String partyName, String partyAliasTypeName)
            throws NamingException {
        GetPartyAliasForm commandForm = PartyUtil.getHome().getGetPartyAliasForm();

        commandForm.setPartyName(partyName);
        commandForm.setPartyAliasTypeName(partyAliasTypeName);

        CommandResult commandResult = PartyUtil.getHome().getPartyAlias(MainBaseAction.getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyAliasResult result = (GetPartyAliasResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_ALIAS, result.getPartyAlias());
    }

}
