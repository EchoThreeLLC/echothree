// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.core.messagetype;

import com.echothree.control.user.message.common.MessageUtil;
import com.echothree.control.user.message.common.form.CreateMessageTypeForm;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Core/MessageType/Add",
    mappingClass = SecureActionMapping.class,
    name = "MessageTypeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/MessageType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/messagetype/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String componentVendorName = request.getParameter(ParameterConstants.COMPONENT_VENDOR_NAME);
        String entityTypeName = request.getParameter(ParameterConstants.ENTITY_TYPE_NAME);
        
        try {
            if(forwardKey == null) {
                AddActionForm actionForm = (AddActionForm)form;
                
                if(wasPost(request)) {
                    CreateMessageTypeForm commandForm = MessageUtil.getHome().getCreateMessageTypeForm();
                    
                    if(componentVendorName == null)
                        componentVendorName = actionForm.getComponentVendorName();
                    if(entityTypeName == null)
                        entityTypeName = actionForm.getEntityTypeName();
                    
                    commandForm.setComponentVendorName(componentVendorName);
                    commandForm.setEntityTypeName(entityTypeName);
                    commandForm.setMessageTypeName(actionForm.getMessageTypeName());
                    commandForm.setMimeTypeUsageTypeName(actionForm.getMimeTypeUsageTypeChoice());
                    commandForm.setSortOrder(actionForm.getSortOrder());
                    commandForm.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = MessageUtil.getHome().createMessageType(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setComponentVendorName(componentVendorName);
                    actionForm.setEntityTypeName(entityTypeName);
                    actionForm.setSortOrder("1");
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));

        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            request.setAttribute(AttributeConstants.ENTITY_TYPE_NAME, entityTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, componentVendorName);
            parameters.put(ParameterConstants.ENTITY_TYPE_NAME, entityTypeName);
            customActionForward.setParameters(parameters);
        }
        
        
        return customActionForward;
    }
    
}
