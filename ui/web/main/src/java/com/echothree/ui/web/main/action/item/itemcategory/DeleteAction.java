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

package com.echothree.ui.web.main.action.item.itemcategory;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.DeleteItemCategoryForm;
import com.echothree.control.user.item.common.form.GetItemCategoryForm;
import com.echothree.control.user.item.common.result.GetItemCategoryResult;
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
    path = "/Item/ItemCategory/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ItemCategoryDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemCategory/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemcategory/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ItemCategory.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemCategoryName(findParameter(request, ParameterConstants.ITEM_CATEGORY_NAME, actionForm.getItemCategoryName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetItemCategoryForm commandForm = ItemUtil.getHome().getGetItemCategoryForm();
        
        commandForm.setItemCategoryName(actionForm.getItemCategoryName());
        
        CommandResult commandResult = ItemUtil.getHome().getItemCategory(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetItemCategoryResult result = (GetItemCategoryResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.ITEM_CATEGORY, result.getItemCategory());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteItemCategoryForm commandForm = ItemUtil.getHome().getDeleteItemCategoryForm();

        commandForm.setItemCategoryName(actionForm.getItemCategoryName());

        return ItemUtil.getHome().deleteItemCategory(getUserVisitPK(request), commandForm);
    }
    
}
