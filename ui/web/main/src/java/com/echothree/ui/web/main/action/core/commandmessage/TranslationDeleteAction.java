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

package com.echothree.ui.web.main.action.core.commandmessage;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.DeleteCommandMessageTranslationForm;
import com.echothree.control.user.core.common.form.GetCommandMessageTranslationForm;
import com.echothree.control.user.core.common.result.GetCommandMessageTranslationResult;
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
    path = "/Core/CommandMessage/TranslationDelete",
    mappingClass = SecureActionMapping.class,
    name = "CommandMessageTranslationDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/CommandMessage/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/core/commandmessage/translationDelete.jsp")
    }
)
public class TranslationDeleteAction
        extends MainBaseDeleteAction<TranslationDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.CommandMessageTranslation.name();
    }
    
    @Override
    public void setupParameters(TranslationDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setCommandMessageTypeName(findParameter(request, ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName()));
        actionForm.setCommandMessageKey(findParameter(request, ParameterConstants.COMMAND_MESSAGE_KEY, actionForm.getCommandMessageKey()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(TranslationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetCommandMessageTranslationForm commandForm = CoreUtil.getHome().getGetCommandMessageTranslationForm();
        
        commandForm.setCommandMessageTypeName(actionForm.getCommandMessageTypeName());
        commandForm.setCommandMessageKey(actionForm.getCommandMessageKey());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = CoreUtil.getHome().getCommandMessageTranslation(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCommandMessageTranslationResult result = (GetCommandMessageTranslationResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.COMMAND_MESSAGE_TRANSLATION, result.getCommandMessageTranslation());
        }
    }
    
    @Override
    public CommandResult doDelete(TranslationDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteCommandMessageTranslationForm commandForm = CoreUtil.getHome().getDeleteCommandMessageTranslationForm();

        commandForm.setCommandMessageTypeName(actionForm.getCommandMessageTypeName());
        commandForm.setCommandMessageKey(actionForm.getCommandMessageKey());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return CoreUtil.getHome().deleteCommandMessageTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName());
        parameters.put(ParameterConstants.COMMAND_MESSAGE_KEY, actionForm.getCommandMessageKey());
    }
    
}
