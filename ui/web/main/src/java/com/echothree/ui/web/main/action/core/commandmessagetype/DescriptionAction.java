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

package com.echothree.ui.web.main.action.core.commandmessagetype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetCommandMessageTypeDescriptionsForm;
import com.echothree.control.user.core.common.result.GetCommandMessageTypeDescriptionsResult;
import com.echothree.model.control.core.common.transfer.CommandMessageTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/CommandMessageType/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/core/commandmessagetype/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String commandMessageTypeName = request.getParameter(ParameterConstants.COMMAND_MESSAGE_TYPE_NAME);
        GetCommandMessageTypeDescriptionsForm commandForm = CoreUtil.getHome().getGetCommandMessageTypeDescriptionsForm();

        commandForm.setCommandMessageTypeName(commandMessageTypeName);

        CommandResult commandResult = CoreUtil.getHome().getCommandMessageTypeDescriptions(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCommandMessageTypeDescriptionsResult result = (GetCommandMessageTypeDescriptionsResult) executionResult.getResult();
            CommandMessageTypeTransfer commandMessageTypeTransfer = result.getCommandMessageType();

            request.setAttribute(AttributeConstants.COMMAND_MESSAGE_TYPE, commandMessageTypeTransfer);
            request.setAttribute(AttributeConstants.COMMAND_MESSAGE_TYPE_DESCRIPTIONS, result.getCommandMessageTypeDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }

        return mapping.findForward(forwardKey);
    }
    
}
