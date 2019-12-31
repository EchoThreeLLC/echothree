// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.application;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.CreateApplicationDescriptionForm;
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
    path = "/Core/Application/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "ApplicationDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Application/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/application/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setApplicationName(findParameter(request, ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
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
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateApplicationDescriptionForm commandForm = CoreUtil.getHome().getCreateApplicationDescriptionForm();

        commandForm.setApplicationName( actionForm.getApplicationName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return CoreUtil.getHome().createApplicationDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName());
    }
    
}
