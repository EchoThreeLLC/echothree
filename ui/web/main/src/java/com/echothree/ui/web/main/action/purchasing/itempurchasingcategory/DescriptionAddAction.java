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

package com.echothree.ui.web.main.action.purchasing.itempurchasingcategory;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.form.CreateItemPurchasingCategoryDescriptionForm;
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
    path = "/Purchasing/ItemPurchasingCategory/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "ItemPurchasingCategoryDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/ItemPurchasingCategory/Description", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/itempurchasingcategory/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String itemPurchasingCategoryName = request.getParameter(ParameterConstants.ITEM_PURCHASING_CATEGORY_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionAddActionForm actionForm = (DescriptionAddActionForm)form;
                
                if(wasPost(request)) {
                    CreateItemPurchasingCategoryDescriptionForm commandForm = VendorUtil.getHome().getCreateItemPurchasingCategoryDescriptionForm();
                    
                    if(itemPurchasingCategoryName == null)
                        itemPurchasingCategoryName = actionForm.getItemPurchasingCategoryName();
                    
                    commandForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                    commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
                    commandForm.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = VendorUtil.getHome().createItemPurchasingCategoryDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.ITEM_PURCHASING_CATEGORY_NAME, itemPurchasingCategoryName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            parameters.put(ParameterConstants.ITEM_PURCHASING_CATEGORY_NAME, itemPurchasingCategoryName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}