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

package com.echothree.ui.web.main.action.configuration.workflowentrancestep;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.form.CreateWorkflowEntranceStepForm;
import com.echothree.control.user.workflow.common.form.GetWorkflowEntranceForm;
import com.echothree.control.user.workflow.common.result.GetWorkflowEntranceResult;
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
    path = "/Configuration/WorkflowEntranceStep/Add",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowEntranceStepAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowEntranceStep/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowentrancestep/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName()));
        actionForm.setWorkflowEntranceName(findParameter(request, ParameterConstants.WORKFLOW_ENTRANCE_NAME, actionForm.getWorkflowEntranceName()));
        actionForm.setEntranceWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getEntranceWorkflowName()));
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetWorkflowEntranceForm commandForm = WorkflowUtil.getHome().getGetWorkflowEntranceForm();

        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowEntranceName(actionForm.getWorkflowEntranceName());
        
        CommandResult commandResult = WorkflowUtil.getHome().getWorkflowEntrance(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetWorkflowEntranceResult result = (GetWorkflowEntranceResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.WORKFLOW_ENTRANCE, result.getWorkflowEntrance());
        }
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateWorkflowEntranceStepForm commandForm = WorkflowUtil.getHome().getCreateWorkflowEntranceStepForm();

        commandForm.setWorkflowName(actionForm.getWorkflowName());
        commandForm.setWorkflowEntranceName(actionForm.getWorkflowEntranceName());
        commandForm.setEntranceWorkflowName(actionForm.getEntranceWorkflowName());
        commandForm.setEntranceWorkflowStepName(actionForm.getEntranceWorkflowStepChoice());

        return WorkflowUtil.getHome().createWorkflowEntranceStep(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName());
        parameters.put(ParameterConstants.WORKFLOW_ENTRANCE_NAME, actionForm.getWorkflowEntranceName());
    }
    
}
