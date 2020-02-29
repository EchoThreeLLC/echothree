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

package com.echothree.ui.web.main.action.configuration.communicationeventpurpose;

import com.echothree.control.user.communication.common.CommunicationUtil;
import com.echothree.control.user.communication.common.form.GetCommunicationEventPurposeDescriptionsForm;
import com.echothree.control.user.communication.common.result.GetCommunicationEventPurposeDescriptionsResult;
import com.echothree.model.control.communication.common.transfer.CommunicationEventPurposeTransfer;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/CommunicationEventPurpose/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/communicationeventpurpose/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            String communicationEventPurposeName = request.getParameter(ParameterConstants.COMMUNICATION_EVENT_PURPOSE_NAME);
            GetCommunicationEventPurposeDescriptionsForm commandForm = CommunicationUtil.getHome().getGetCommunicationEventPurposeDescriptionsForm();
            
            commandForm.setCommunicationEventPurposeName(communicationEventPurposeName);
            
            CommandResult commandResult = CommunicationUtil.getHome().getCommunicationEventPurposeDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCommunicationEventPurposeDescriptionsResult result = (GetCommunicationEventPurposeDescriptionsResult)executionResult.getResult();
            CommunicationEventPurposeTransfer communicationEventPurposeTransfer = result.getCommunicationEventPurpose();
            
            request.setAttribute(AttributeConstants.COMMUNICATION_EVENT_PURPOSE, communicationEventPurposeTransfer);
            request.setAttribute(AttributeConstants.COMMUNICATION_EVENT_PURPOSE_NAME, communicationEventPurposeTransfer.getCommunicationEventPurposeName());
            request.setAttribute(AttributeConstants.COMMUNICATION_EVENT_PURPOSE_DESCRIPTIONS, result.getCommunicationEventPurposeDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}