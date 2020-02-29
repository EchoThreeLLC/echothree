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

package com.echothree.ui.web.main.action.core.commandmessage;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.CommandMessageEdit;
import com.echothree.control.user.core.common.form.EditCommandMessageForm;
import com.echothree.control.user.core.common.result.EditCommandMessageResult;
import com.echothree.control.user.core.common.spec.CommandMessageSpec;
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
    path = "/Core/CommandMessage/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CommandMessageEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/CommandMessage/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/commandmessage/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, CommandMessageSpec, CommandMessageEdit, EditCommandMessageForm, EditCommandMessageResult> {
    
    @Override
    protected CommandMessageSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        CommandMessageSpec spec = CoreUtil.getHome().getCommandMessageSpec();
        
        spec.setCommandMessageTypeName(findParameter(request, ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName()));
        spec.setCommandMessageKey(findParameter(request, ParameterConstants.ORIGINAL_COMMAND_MESSAGE_KEY, actionForm.getOriginalCommandMessageKey()));
        
        return spec;
    }
    
    @Override
    protected CommandMessageEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        CommandMessageEdit edit = CoreUtil.getHome().getCommandMessageEdit();

        edit.setCommandMessageKey(actionForm.getCommandMessageKey());
        edit.setTranslation(actionForm.getTranslation());

        return edit;
    }
    
    @Override
    protected EditCommandMessageForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditCommandMessageForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditCommandMessageResult result, CommandMessageSpec spec, CommandMessageEdit edit) {
        actionForm.setCommandMessageTypeName(spec.getCommandMessageTypeName());
        actionForm.setOriginalCommandMessageKey(spec.getCommandMessageKey());
        actionForm.setCommandMessageKey(edit.getCommandMessageKey());
        actionForm.setTranslation(edit.getTranslation());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCommandMessageForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editCommandMessage(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditCommandMessageResult result) {
        request.setAttribute(AttributeConstants.COMMAND_MESSAGE, result.getCommandMessage());
    }

}
