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

package com.echothree.ui.web.main.action.item.itemalias;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.CreateItemAliasForm;
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
    path = "/Item/ItemAlias/Add",
    mappingClass = SecureActionMapping.class,
    name = "ItemAliasAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemAlias/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemalias/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String itemName = request.getParameter(ParameterConstants.ITEM_NAME);
        
        try {
            AddActionForm actionForm = (AddActionForm)form;
            CreateItemAliasForm commandForm = ItemUtil.getHome().getCreateItemAliasForm();
            
            if(itemName == null)
                itemName = actionForm.getItemName();
            
            if(wasPost(request)) {
                commandForm.setItemName(itemName);
                commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeChoice());
                commandForm.setItemAliasTypeName(actionForm.getItemAliasTypeChoice());
                commandForm.setAlias(actionForm.getAlias());
                
                CommandResult commandResult = ItemUtil.getHome().createItemAlias(getUserVisitPK(request), commandForm);
                
                if(commandResult.hasErrors()) {
                    setCommandResultAttribute(request, commandResult);
                    forwardKey = ForwardConstants.FORM;
                } else {
                    forwardKey = ForwardConstants.DISPLAY;
                }
            } else {
                actionForm.setItemName(itemName);
                actionForm.setupItemAliasTypeChoices();
                forwardKey = ForwardConstants.FORM;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.ITEM_NAME, itemName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.ITEM_NAME, itemName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}