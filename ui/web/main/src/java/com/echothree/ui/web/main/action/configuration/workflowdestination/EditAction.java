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

package com.echothree.ui.web.main.action.configuration.workflowdestination;

import com.echothree.control.user.workflow.common.WorkflowUtil;
import com.echothree.control.user.workflow.common.edit.WorkflowDestinationEdit;
import com.echothree.control.user.workflow.common.form.EditWorkflowDestinationForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationResult;
import com.echothree.control.user.workflow.common.spec.WorkflowDestinationSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/WorkflowDestination/Edit",
    mappingClass = SecureActionMapping.class,
    name = "WorkflowDestinationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/WorkflowDestination/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/workflowdestination/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, WorkflowDestinationSpec, WorkflowDestinationEdit, EditWorkflowDestinationForm, EditWorkflowDestinationResult> {
    
    @Override
    protected WorkflowDestinationSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        WorkflowDestinationSpec spec = WorkflowUtil.getHome().getWorkflowDestinationSpec();
        
        spec.setWorkflowName(findParameter(request, ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName()));
        spec.setWorkflowStepName(findParameter(request, ParameterConstants.WORKFLOW_STEP_NAME, actionForm.getWorkflowStepName()));
        spec.setWorkflowDestinationName(findParameter(request, ParameterConstants.ORIGINAL_WORKFLOW_DESTINATION_NAME, actionForm.getOriginalWorkflowDestinationName()));
        
        return spec;
    }
    
    @Override
    protected WorkflowDestinationEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        WorkflowDestinationEdit edit = WorkflowUtil.getHome().getWorkflowDestinationEdit();

        edit.setWorkflowDestinationName(actionForm.getWorkflowDestinationName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditWorkflowDestinationForm getForm()
            throws NamingException {
        return WorkflowUtil.getHome().getEditWorkflowDestinationForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditWorkflowDestinationResult result, WorkflowDestinationSpec spec, WorkflowDestinationEdit edit) {
        actionForm.setWorkflowName(spec.getWorkflowName());
        actionForm.setWorkflowStepName(spec.getWorkflowStepName());
        actionForm.setOriginalWorkflowDestinationName(spec.getWorkflowDestinationName());
        actionForm.setWorkflowDestinationName(edit.getWorkflowDestinationName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditWorkflowDestinationForm commandForm)
            throws Exception {
        return WorkflowUtil.getHome().editWorkflowDestination(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.WORKFLOW_NAME, actionForm.getWorkflowName());
        parameters.put(ParameterConstants.WORKFLOW_STEP_NAME, actionForm.getWorkflowStepName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditWorkflowDestinationResult result) {
        request.setAttribute(AttributeConstants.WORKFLOW_DESTINATION, result.getWorkflowDestination());
    }

}
