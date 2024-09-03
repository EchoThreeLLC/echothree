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

package com.echothree.ui.web.main.action.humanresources.employeeprintergroupuse;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.GetEmployeeForm;
import com.echothree.control.user.employee.common.result.GetEmployeeResult;
import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.form.GetPartyPrinterGroupUseForm;
import com.echothree.control.user.printer.common.result.GetPartyPrinterGroupUseResult;
import com.echothree.model.control.printer.common.transfer.PartyPrinterGroupUseTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseEmployeePrinterGroupUseAction<A
        extends ActionForm>
        extends MainBaseAction<A> {

    public static void setupEmployee(HttpServletRequest request, String partyName)
            throws NamingException {
        var commandForm = EmployeeUtil.getHome().getGetEmployeeForm();

        commandForm.setPartyName(partyName);

        var commandResult = EmployeeUtil.getHome().getEmployee(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEmployeeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.EMPLOYEE, result.getEmployee());
    }

    public void setupEmployee(HttpServletRequest request)
            throws NamingException {
        setupEmployee(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public static PartyPrinterGroupUseTransfer getPartyPrinterGroupUseTransfer(HttpServletRequest request, String partyName, String printerGroupUseTypeName)
            throws NamingException {
        var commandForm = PrinterUtil.getHome().getGetPartyPrinterGroupUseForm();

        commandForm.setPartyName(partyName);
        commandForm.setPrinterGroupUseTypeName(printerGroupUseTypeName);

        var commandResult = PrinterUtil.getHome().getPartyPrinterGroupUse(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyPrinterGroupUseResult)executionResult.getResult();

        return result.getPartyPrinterGroupUse();
    }

    public static void setupPartyPrinterGroupUseTransfer(HttpServletRequest request, String partyName, String printerGroupUseTypeName)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_PRINTER_GROUP_USE, getPartyPrinterGroupUseTransfer(request, partyName, printerGroupUseTypeName));
    }

}
