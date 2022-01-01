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

package com.echothree.ui.web.main.action.configuration.workflowdestinationpartytype;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.form.CreateWorkflowDestinationPartyTypeForm;
import com.echothree.control.user.workflow.common.form.GetWorkflowDestinationForm;
import com.echothree.control.user.workflow.common.result.GetWorkflowDestinationResult;
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
    path = "/Configuration/WorkflowDestinationPartyType/Add",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowDestinationPartyTypeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowDestinationPartyType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowdestinationpartytype/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName()));
        actionForm.setWorkflowStepName(findParameter(request, ParameterConstants.WORKFLOW_STEP_NAME, actionForm.getWorkflowStepName()));
        actionForm.setWorkflowDestinationName(findParameter(request, ParameterConstants.WORKFLOW_DESTINATION_NAME, actionForm.getWorkflowDestinationName()));
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetWorkflowDestinationForm commandForm = WorkflowUtil.getHome().getGetWorkflowDestinationForm();
        
        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowStepName(actionForm.getWorkflowStepName());
        commandForm.setWorkflowDestinationName(actionForm.getWorkflowDestinationName());

        CommandResult commandResult = WorkflowUtil.getHome().getWorkflowDestination(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetWorkflowDestinationResult result = (GetWorkflowDestinationResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.WORKFLOW_DESTINATION, result.getWorkflowDestination());
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateWorkflowDestinationPartyTypeForm commandForm = WorkflowUtil.getHome().getCreateWorkflowDestinationPartyTypeForm();

        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowStepName(actionForm.getWorkflowStepName());
        commandForm.setWorkflowDestinationName(actionForm.getWorkflowDestinationName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeChoice());

        return WorkflowUtil.getHome().createWorkflowDestinationPartyType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName());
        parameters.put(ParameterConstants.WORKFLOW_STEP_NAME, actionForm.getWorkflowStepName());
        parameters.put(ParameterConstants.WORKFLOW_DESTINATION_NAME, actionForm.getWorkflowDestinationName());
    }
    
}
