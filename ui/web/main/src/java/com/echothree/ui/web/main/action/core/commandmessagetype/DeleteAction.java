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

package com.echothree.ui.web.main.action.core.commandmessagetype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.DeleteCommandMessageTypeForm;
import com.echothree.control.user.core.common.form.GetCommandMessageTypeForm;
import com.echothree.control.user.core.common.result.GetCommandMessageTypeResult;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Core/CommandMessageType/Delete",
    mappingClass = SecureActionMapping.class,
    name = "CommandMessageTypeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/CommandMessageType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/commandmessagetype/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.CommandMessageType.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setCommandMessageTypeName(findParameter(request, ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetCommandMessageTypeForm commandForm = CoreUtil.getHome().getGetCommandMessageTypeForm();
        
        commandForm.setCommandMessageTypeName(actionForm.getCommandMessageTypeName());
        
        CommandResult commandResult = CoreUtil.getHome().getCommandMessageType(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetCommandMessageTypeResult result = (GetCommandMessageTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.COMMAND_MESSAGE_TYPE, result.getCommandMessageType());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteCommandMessageTypeForm commandForm = CoreUtil.getHome().getDeleteCommandMessageTypeForm();

        commandForm.setCommandMessageTypeName(actionForm.getCommandMessageTypeName());

        return CoreUtil.getHome().deleteCommandMessageType(getUserVisitPK(request), commandForm);
    }
    
}
