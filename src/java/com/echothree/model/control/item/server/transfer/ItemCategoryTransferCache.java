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

import com.echothree.model.control.item.common.transfer.ItemCategoryTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemCategoryDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemCategoryTransferCache
        extends BaseItemTransferCache<ItemCategory, ItemCategoryTransfer> {
    
    /** Creates a new instance of ItemCategoryTransferCache */
    public ItemCategoryTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemCategoryTransfer getTransfer(ItemCategory itemCategory) {
        ItemCategoryTransfer itemCategoryTransfer = get(itemCategory);
        
        if(itemCategoryTransfer == null) {
            ItemCategoryDetail itemCategoryDetail = itemCategory.getLastDetail();
            String itemCategoryName = itemCategoryDetail.getItemCategoryName();
            ItemCategory parentItemCategory = itemCategoryDetail.getParentItemCategory();
            ItemCategoryTransfer parentItemCategoryTransfer = parentItemCategory == null ? null : getTransfer(parentItemCategory);
            Boolean isDefault = itemCategoryDetail.getIsDefault();
            Integer sortOrder = itemCategoryDetail.getSortOrder();
            String description = itemControl.getBestItemCategoryDescription(itemCategory, getLanguage());
            
            itemCategoryTransfer = new ItemCategoryTransfer(itemCategoryName, parentItemCategoryTransfer, isDefault, sortOrder, description);
            put(itemCategory, itemCategoryTransfer);
        }
        
        return itemCategoryTransfer;
    }
    
}
