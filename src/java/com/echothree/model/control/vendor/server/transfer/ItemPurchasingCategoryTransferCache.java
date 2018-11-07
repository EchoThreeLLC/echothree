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

package com.echothree.model.control.vendor.server.transfer;

import com.echothree.model.control.vendor.common.transfer.ItemPurchasingCategoryTransfer;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategoryDetail;

public class ItemPurchasingCategoryTransferCache
        extends BaseVendorTransferCache<ItemPurchasingCategory, ItemPurchasingCategoryTransfer> {
    
    /** Creates a new instance of ItemPurchasingCategoryTransferCache */
    public ItemPurchasingCategoryTransferCache(UserVisit userVisit, VendorControl vendorControl) {
        super(userVisit, vendorControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ItemPurchasingCategoryTransfer getItemPurchasingCategoryTransfer(ItemPurchasingCategory itemPurchasingCategory) {
        ItemPurchasingCategoryTransfer itemPurchasingCategoryTransfer = get(itemPurchasingCategory);
        
        if(itemPurchasingCategoryTransfer == null) {
            ItemPurchasingCategoryDetail itemPurchasingCategoryDetail = itemPurchasingCategory.getLastDetail();
            String itemPurchasingCategoryName = itemPurchasingCategoryDetail.getItemPurchasingCategoryName();
            ItemPurchasingCategory parentItemPurchasingCategory = itemPurchasingCategoryDetail.getParentItemPurchasingCategory();
            ItemPurchasingCategoryTransfer parentItemPurchasingCategoryTransfer = parentItemPurchasingCategory == null? null: getItemPurchasingCategoryTransfer(parentItemPurchasingCategory);
            Boolean isDefault = itemPurchasingCategoryDetail.getIsDefault();
            Integer sortOrder = itemPurchasingCategoryDetail.getSortOrder();
            String description = vendorControl.getBestItemPurchasingCategoryDescription(itemPurchasingCategory, getLanguage());
            
            itemPurchasingCategoryTransfer = new ItemPurchasingCategoryTransfer(itemPurchasingCategoryName,
                    parentItemPurchasingCategoryTransfer, isDefault, sortOrder, description);
            put(itemPurchasingCategory, itemPurchasingCategoryTransfer);
        }
        
        return itemPurchasingCategoryTransfer;
    }
    
}
