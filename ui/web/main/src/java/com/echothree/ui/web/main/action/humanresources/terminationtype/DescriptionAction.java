// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.web.main.action.humanresources.terminationtype;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.form.GetTerminationTypeDescriptionsForm;
import com.echothree.control.user.employee.common.result.GetTerminationTypeDescriptionsResult;
import com.echothree.model.control.employee.common.transfer.TerminationTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/TerminationType/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/terminationtype/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            String terminationTypeName = request.getParameter(ParameterConstants.TERMINATION_TYPE_NAME);
            GetTerminationTypeDescriptionsForm commandForm = EmployeeUtil.getHome().getGetTerminationTypeDescriptionsForm();
            
            commandForm.setTerminationTypeName(terminationTypeName);
            
            CommandResult commandResult = EmployeeUtil.getHome().getTerminationTypeDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetTerminationTypeDescriptionsResult result = (GetTerminationTypeDescriptionsResult)executionResult.getResult();
            TerminationTypeTransfer terminationTypeTransfer = result.getTerminationType();
            
            request.setAttribute(AttributeConstants.TERMINATION_TYPE, terminationTypeTransfer);
            request.setAttribute(AttributeConstants.TERMINATION_TYPE_NAME, terminationTypeTransfer.getTerminationTypeName());
            request.setAttribute(AttributeConstants.TERMINATION_TYPE_DESCRIPTIONS, result.getTerminationTypeDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}