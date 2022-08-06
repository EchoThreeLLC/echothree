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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemPricesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.item.server.factory.ItemPriceFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetItemPricesCommand
        extends BaseMultipleEntitiesCommand<ItemPrice, GetItemPricesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemPricesCommand */
    public GetItemPricesCommand(UserVisitPK userVisitPK, GetItemPricesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    Item item;

    @Override
    protected Collection<ItemPrice> getEntities() {
        var itemControl = Session.getModelController(ItemControl.class);
        Collection<ItemPrice> itemPrices = null;

        item = ItemLogic.getInstance().getItemByName(this, form.getItemName());

        if(!hasExecutionErrors()) {
            itemPrices = itemControl.getItemPricesByItem(item);
        }

        return itemPrices;
    }

    @Override
    protected BaseResult getTransfers(Collection<ItemPrice> entities) {
        var result = ItemResultFactory.getGetItemPricesResult();

        if(entities != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(ItemPriceFactory.class)) {
                result.setItemPriceCount(itemControl.countItemPricesByItem(item));
            }

            result.setItem(itemControl.getItemTransfer(userVisit, item));
            result.setItemPrices(itemControl.getItemPriceTransfers(userVisit, entities));
        }

        return result;
    }

}
