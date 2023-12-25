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
import com.echothree.control.user.core.common.form.CreateApplicationEditorForm;
import com.echothree.control.user.core.common.form.GetApplicationForm;
import com.echothree.control.user.core.common.result.GetApplicationResult;
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
    path = "/Core/ApplicationEditor/Add",
    mappingClass = SecureActionMapping.class,
    name = "ApplicationEditorAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/ApplicationEditor/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/applicationeditor/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {

    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setApplicationName(findParameter(request, ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName()));
    }
    
    @Override
    public void setupDefaults(AddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetApplicationForm commandForm = CoreUtil.getHome().getGetApplicationForm();

        commandForm.setApplicationName(actionForm.getApplicationName());
        
        CommandResult commandResult = CoreUtil.getHome().getApplication(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetApplicationResult result = (GetApplicationResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.APPLICATION, result.getApplication());
        }
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateApplicationEditorForm commandForm = CoreUtil.getHome().getCreateApplicationEditorForm();

        commandForm.setApplicationName(actionForm.getApplicationName());
        commandForm.setEditorName(actionForm.getEditorChoice());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());

        return CoreUtil.getHome().createApplicationEditor(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName());
    }
    
}
