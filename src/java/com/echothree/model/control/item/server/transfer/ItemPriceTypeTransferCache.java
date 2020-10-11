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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.ItemProperties;
import com.echothree.model.control.item.common.transfer.ItemPriceTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import java.util.Set;

public class ItemPriceTypeTransferCache
        extends BaseItemTransferCache<ItemPriceType, ItemPriceTypeTransfer> {

    TransferProperties transferProperties;
    boolean filterItemPriceTypeName;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;

    /**
     * Creates a new instance of ItemPriceTypeTransferCache
     */
    public ItemPriceTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(ItemPriceTypeTransfer.class);

            if(properties != null) {
                filterItemPriceTypeName = !properties.contains(ItemProperties.ITEM_PRICE_TYPE_NAME);
                filterIsDefault = !properties.contains(ItemProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(ItemProperties.SORT_ORDER);
                filterDescription = !properties.contains(ItemProperties.DESCRIPTION);
            }
        }

    }

    @Override
    public ItemPriceTypeTransfer getTransfer(ItemPriceType itemPriceType) {
        ItemPriceTypeTransfer itemPriceTypeTransfer = get(itemPriceType);

        if(itemPriceTypeTransfer == null) {
            String itemPriceTypeName = filterItemPriceTypeName ? null : itemPriceType.getItemPriceTypeName();
            Boolean isDefault = filterIsDefault ? null : itemPriceType.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : itemPriceType.getSortOrder();
            String description = filterDescription ? null : itemControl.getBestItemPriceTypeDescription(itemPriceType, getLanguage());

            itemPriceTypeTransfer = new ItemPriceTypeTransfer(itemPriceTypeName, isDefault, sortOrder, description);
            put(itemPriceType, itemPriceTypeTransfer);
        }

        return itemPriceTypeTransfer;
    }

}
