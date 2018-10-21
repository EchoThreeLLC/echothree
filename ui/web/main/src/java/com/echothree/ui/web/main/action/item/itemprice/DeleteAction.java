// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.item.itemprice;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.remote.form.DeleteItemPriceForm;
import com.echothree.control.user.item.remote.form.GetItemPriceForm;
import com.echothree.control.user.item.remote.result.GetItemPriceResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Item/ItemPrice/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ItemPriceDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemPrice/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemprice/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ItemPrice.name();
    }

    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setItemName(findParameter(request, ParameterConstants.ITEM_NAME, actionForm.getItemName()));
        actionForm.setInventoryConditionName(findParameter(request, ParameterConstants.INVENTORY_CONDITION_NAME, actionForm.getInventoryConditionName()));
        actionForm.setUnitOfMeasureTypeName(findParameter(request, ParameterConstants.UNIT_OF_MEASURE_TYPE_NAME, actionForm.getUnitOfMeasureTypeName()));
        actionForm.setCurrencyIsoName(findParameter(request, ParameterConstants.CURRENCY_ISO_NAME, actionForm.getCurrencyIsoName()));
    }

    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetItemPriceForm commandForm = ItemUtil.getHome().getGetItemPriceForm();

        commandForm.setItemName(actionForm.getItemName());
        commandForm.setInventoryConditionName(actionForm.getInventoryConditionName());
        commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeName());
        commandForm.setCurrencyIsoName(actionForm.getCurrencyIsoName());

        CommandResult commandResult = ItemUtil.getHome().getItemPrice(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetItemPriceResult result = (GetItemPriceResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.ITEM_PRICE, result.getItemPrice());
    }

    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteItemPriceForm commandForm = ItemUtil.getHome().getDeleteItemPriceForm();

        commandForm.setItemName(actionForm.getItemName());
        commandForm.setInventoryConditionName(actionForm.getInventoryConditionName());
        commandForm.setUnitOfMeasureTypeName(actionForm.getUnitOfMeasureTypeName());
        commandForm.setCurrencyIsoName(actionForm.getCurrencyIsoName());

        return ItemUtil.getHome().deleteItemPrice(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_NAME, actionForm.getItemName());
    }

}
