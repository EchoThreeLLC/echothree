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

package com.echothree.ui.web.main.action.item.relateditem;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.DeleteRelatedItemForm;
import com.echothree.control.user.item.common.form.GetRelatedItemForm;
import com.echothree.control.user.item.common.result.GetRelatedItemResult;
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
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Item/RelatedItem/Delete",
    mappingClass = SecureActionMapping.class,
    name = "RelatedItemDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/RelatedItem/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/relateditem/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.RelatedItem.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setRelatedItemTypeName(findParameter(request, ParameterConstants.RELATED_ITEM_TYPE_NAME, actionForm.getRelatedItemTypeName()));
        actionForm.setFromItemName(findParameter(request, ParameterConstants.FROM_ITEM_NAME, actionForm.getFromItemName()));
        actionForm.setToItemName(findParameter(request, ParameterConstants.TO_ITEM_NAME, actionForm.getToItemName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetRelatedItemForm commandForm = ItemUtil.getHome().getGetRelatedItemForm();
        
        commandForm.setRelatedItemTypeName(actionForm.getRelatedItemTypeName());
        commandForm.setFromItemName(actionForm.getFromItemName());
        commandForm.setToItemName(actionForm.getToItemName());
        
        CommandResult commandResult = ItemUtil.getHome().getRelatedItem(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetRelatedItemResult result = (GetRelatedItemResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.RELATED_ITEM, result.getRelatedItem());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteRelatedItemForm commandForm = ItemUtil.getHome().getDeleteRelatedItemForm();

        commandForm.setRelatedItemTypeName(actionForm.getRelatedItemTypeName());
        commandForm.setFromItemName(actionForm.getFromItemName());
        commandForm.setToItemName(actionForm.getToItemName());

        return ItemUtil.getHome().deleteRelatedItem(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getFromItemName());
    }
    
}