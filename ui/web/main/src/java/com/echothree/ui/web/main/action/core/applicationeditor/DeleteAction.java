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

package com.echothree.ui.web.main.action.core.applicationeditor;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.DeleteApplicationEditorForm;
import com.echothree.control.user.core.common.form.GetApplicationEditorForm;
import com.echothree.control.user.core.common.result.GetApplicationEditorResult;
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
    path = "/Core/ApplicationEditor/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ApplicationEditorDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/ApplicationEditor/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/applicationeditor/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ApplicationEditor.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setApplicationName(findParameter(request, ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName()));
        actionForm.setEditorName(findParameter(request, ParameterConstants.EDITOR_NAME, actionForm.getEditorName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetApplicationEditorForm commandForm = CoreUtil.getHome().getGetApplicationEditorForm();
        
        commandForm.setApplicationName(actionForm.getApplicationName());
        commandForm.setEditorName(actionForm.getEditorName());
        
        CommandResult commandResult = CoreUtil.getHome().getApplicationEditor(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetApplicationEditorResult result = (GetApplicationEditorResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.APPLICATION_EDITOR, result.getApplicationEditor());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteApplicationEditorForm commandForm = CoreUtil.getHome().getDeleteApplicationEditorForm();

        commandForm.setApplicationName(actionForm.getApplicationName());
        commandForm.setEditorName(actionForm.getEditorName());

        return CoreUtil.getHome().deleteApplicationEditor(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName());
    }
    
}
