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

package com.echothree.ui.web.main.action.item.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.GetItemDescriptionsForm;
import com.echothree.control.user.item.common.result.GetItemDescriptionsResult;
import com.echothree.model.control.item.common.ItemOptions;
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
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Item/Item/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/item/item/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            GetItemDescriptionsForm commandForm = ItemUtil.getHome().getGetItemDescriptionsForm();
            String itemName = request.getParameter(ParameterConstants.ITEM_NAME);
            String itemDescriptionTypeUseTypeName = request.getParameter(ParameterConstants.ITEM_DESCRIPTION_TYPE_USE_TYPE_NAME);

            commandForm.setItemName(itemName);
            commandForm.setItemDescriptionTypeUseTypeName(itemDescriptionTypeUseTypeName);

            Set<String> options = new HashSet<>();
            options.add(ItemOptions.ItemDescriptionIncludeString);
            options.add(ItemOptions.ItemDescriptionIncludeImageDescription);
            commandForm.setOptions(options);
            
            CommandResult commandResult = ItemUtil.getHome().getItemDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetItemDescriptionsResult result = (GetItemDescriptionsResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.ITEM, result.getItem());
            request.setAttribute(AttributeConstants.ITEM_DESCRIPTIONS, result.getItemDescriptions());
            
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}