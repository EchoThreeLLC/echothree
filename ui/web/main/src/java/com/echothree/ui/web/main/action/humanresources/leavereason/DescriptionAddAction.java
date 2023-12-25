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

package com.echothree.ui.web.main.action.humanresources.leavereason;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.CreateLeaveReasonDescriptionForm;
import com.echothree.control.user.employee.common.form.GetLeaveReasonForm;
import com.echothree.control.user.employee.common.result.GetLeaveReasonResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/LeaveReason/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "LeaveReasonDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/LeaveReason/Description", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leavereason/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setLeaveReasonName(findParameter(request, ParameterConstants.LEAVE_REASON_NAME, actionForm.getLeaveReasonName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetLeaveReasonForm commandForm = EmployeeUtil.getHome().getGetLeaveReasonForm();

        commandForm.setLeaveReasonName(actionForm.getLeaveReasonName());
        
        CommandResult commandResult = EmployeeUtil.getHome().getLeaveReason(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetLeaveReasonResult result = (GetLeaveReasonResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.LEAVE_REASON, result.getLeaveReason());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateLeaveReasonDescriptionForm commandForm = EmployeeUtil.getHome().getCreateLeaveReasonDescriptionForm();

        commandForm.setLeaveReasonName( actionForm.getLeaveReasonName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return EmployeeUtil.getHome().createLeaveReasonDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.LEAVE_REASON_NAME, actionForm.getLeaveReasonName());
    }
    
}
