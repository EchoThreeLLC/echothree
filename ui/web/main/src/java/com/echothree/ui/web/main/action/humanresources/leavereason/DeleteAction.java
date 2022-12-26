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

package com.echothree.ui.web.main.action.humanresources.leavereason;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.DeleteLeaveReasonForm;
import com.echothree.control.user.employee.common.form.GetLeaveReasonForm;
import com.echothree.control.user.employee.common.result.GetLeaveReasonResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/LeaveReason/Delete",
    mappingClass = SecureActionMapping.class,
    name = "LeaveReasonDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/LeaveReason/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leavereason/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.LeaveReason.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setLeaveReasonName(findParameter(request, ParameterConstants.LEAVE_REASON_NAME, actionForm.getLeaveReasonName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetLeaveReasonForm commandForm = EmployeeUtil.getHome().getGetLeaveReasonForm();
        
        commandForm.setLeaveReasonName(actionForm.getLeaveReasonName());
        
        CommandResult commandResult = EmployeeUtil.getHome().getLeaveReason(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetLeaveReasonResult result = (GetLeaveReasonResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.LEAVE_REASON, result.getLeaveReason());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteLeaveReasonForm commandForm = EmployeeUtil.getHome().getDeleteLeaveReasonForm();

        commandForm.setLeaveReasonName(actionForm.getLeaveReasonName());

        return EmployeeUtil.getHome().deleteLeaveReason(getUserVisitPK(request), commandForm);
    }
    
}
