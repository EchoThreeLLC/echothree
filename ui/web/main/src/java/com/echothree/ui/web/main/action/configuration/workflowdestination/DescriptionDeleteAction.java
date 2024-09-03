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

package com.echothree.ui.web.main.action.configuration.workflowdestination;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowDestinationDescriptionForm;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/WorkflowDestination/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowDestination/Description", redirect = true)
    }
)
public class DescriptionDeleteAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        var workflowName = request.getParameter(ParameterConstants.WORKFLOW_NAME);
        var workflowStepName = request.getParameter(ParameterConstants.WORKFLOW_STEP_NAME);
        var workflowDestinationName = request.getParameter(ParameterConstants.WORKFLOW_DESTINATION_NAME);
        
        try {
            var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
            var commandForm = WorkflowUtil.getHome().getDeleteWorkflowDestinationDescriptionForm();
            
            commandForm.setWorkflowName(workflowName);
            commandForm.setWorkflowStepName(workflowStepName);
            commandForm.setWorkflowDestinationName(workflowDestinationName);
            commandForm.setLanguageIsoName(languageIsoName);
            
            WorkflowUtil.getHome().deleteWorkflowDestinationDescription(getUserVisitPK(request), commandForm);
            
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.WORKFLOW_NAME, workflowName);
            parameters.put(ParameterConstants.WORKFLOW_STEP_NAME, workflowStepName);
            parameters.put(ParameterConstants.WORKFLOW_DESTINATION_NAME, workflowDestinationName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}