// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.employee.common.form.DeleteLeaveReasonDescriptionForm;
import com.echothree.control.user.employee.common.form.GetLeaveReasonDescriptionForm;
import com.echothree.control.user.employee.common.result.GetLeaveReasonDescriptionResult;
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
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/LeaveReason/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "LeaveReasonDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/LeaveReason/Description", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leavereason/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.LeaveReasonDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setLeaveReasonName(findParameter(request, ParameterConstants.LEAVE_REASON_NAME, actionForm.getLeaveReasonName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetLeaveReasonDescriptionForm commandForm = EmployeeUtil.getHome().getGetLeaveReasonDescriptionForm();
        
        commandForm.setLeaveReasonName(actionForm.getLeaveReasonName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = EmployeeUtil.getHome().getLeaveReasonDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetLeaveReasonDescriptionResult result = (GetLeaveReasonDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.LEAVE_REASON_DESCRIPTION, result.getLeaveReasonDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteLeaveReasonDescriptionForm commandForm = EmployeeUtil.getHome().getDeleteLeaveReasonDescriptionForm();

        commandForm.setLeaveReasonName(actionForm.getLeaveReasonName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return EmployeeUtil.getHome().deleteLeaveReasonDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.LEAVE_REASON_NAME, actionForm.getLeaveReasonName());
    }
    
}
