// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.commandmessage;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.CreateCommandMessageTranslationForm;
import com.echothree.control.user.core.common.form.GetCommandMessageForm;
import com.echothree.control.user.core.common.result.GetCommandMessageResult;
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
    path = "/Core/CommandMessage/TranslationAdd",
    mappingClass = SecureActionMapping.class,
    name = "CommandMessageTranslationAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/CommandMessage/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/core/commandmessage/translationAdd.jsp")
    }
)
public class TranslationAddAction
        extends MainBaseAddAction<TranslationAddActionForm> {

    @Override
    public void setupParameters(TranslationAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setCommandMessageTypeName(findParameter(request, ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName()));
        actionForm.setCommandMessageKey(findParameter(request, ParameterConstants.COMMAND_MESSAGE_KEY, actionForm.getCommandMessageKey()));
    }
    
    @Override
    public void setupTransfer(TranslationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetCommandMessageForm commandForm = CoreUtil.getHome().getGetCommandMessageForm();

        commandForm.setCommandMessageTypeName(actionForm.getCommandMessageTypeName());
        commandForm.setCommandMessageKey(actionForm.getCommandMessageKey());
        
        CommandResult commandResult = CoreUtil.getHome().getCommandMessage(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCommandMessageResult result = (GetCommandMessageResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.COMMAND_MESSAGE, result.getCommandMessage());
        }
    }
    
    @Override
    public CommandResult doAdd(TranslationAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateCommandMessageTranslationForm commandForm = CoreUtil.getHome().getCreateCommandMessageTranslationForm();

        commandForm.setCommandMessageTypeName(actionForm.getCommandMessageTypeName());
        commandForm.setCommandMessageKey(actionForm.getCommandMessageKey());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setTranslation(actionForm.getTranslation());

        return CoreUtil.getHome().createCommandMessageTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName());
        parameters.put(ParameterConstants.COMMAND_MESSAGE_KEY, actionForm.getCommandMessageKey());
    }
    
}
