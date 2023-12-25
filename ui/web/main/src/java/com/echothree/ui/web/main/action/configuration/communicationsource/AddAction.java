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

package com.echothree.ui.web.main.action.configuration.communicationsource;

import com.echothree.control.user.communication.common.CommunicationUtil;
import com.echothree.control.user.communication.common.form.CreateCommunicationSourceForm;
import com.echothree.model.control.communication.common.CommunicationConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.util.common.command.CommandResult;
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
    path = "/Configuration/CommunicationSource/Add",
    mappingClass = SecureActionMapping.class,
    name = "CommunicationSourceAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/CommunicationSource/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/communicationsource/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            if(forwardKey == null) {
                AddActionForm actionForm = (AddActionForm)form;
                
                if(wasPost(request)) {
                    CreateCommunicationSourceForm commandForm = CommunicationUtil.getHome().getCreateCommunicationSourceForm();
                    
                    commandForm.setCommunicationSourceName(actionForm.getCommunicationSourceName());
                    commandForm.setCommunicationSourceTypeName(CommunicationConstants.CommunicationSourceType_EMAIL);
                    commandForm.setSortOrder(actionForm.getSortOrder());
                    commandForm.setServerName(actionForm.getServerChoice());
                    commandForm.setUsername(actionForm.getUsername());
                    commandForm.setPassword(actionForm.getPassword());
                    commandForm.setReceiveWorkEffortScopeName(actionForm.getReceiveWorkEffortScopeChoice());
                    commandForm.setSendWorkEffortScopeName(actionForm.getSendWorkEffortScopeChoice());
                    commandForm.setReviewEmployeeSelectorName(actionForm.getReviewEmployeeSelectorChoice());
                    commandForm.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = CommunicationUtil.getHome().createCommunicationSource(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setSortOrder("1");
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}