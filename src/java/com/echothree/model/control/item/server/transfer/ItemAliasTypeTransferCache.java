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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.ItemAliasChecksumTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemAliasTypeTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.entity.ItemAliasTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemAliasTypeTransferCache
        extends BaseItemTransferCache<ItemAliasType, ItemAliasTypeTransfer> {
    
    /** Creates a new instance of ItemAliasTypeTransferCache */
    public ItemAliasTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemAliasTypeTransfer getTransfer(ItemAliasType itemAliasType) {
        ItemAliasTypeTransfer itemAliasTypeTransfer = get(itemAliasType);
        
        if(itemAliasTypeTransfer == null) {
            ItemAliasTypeDetail itemAliasTypeDetail = itemAliasType.getLastDetail();
            String itemAliasTypeName = itemAliasTypeDetail.getItemAliasTypeName();
            String validationPattern = itemAliasTypeDetail.getValidationPattern();
            ItemAliasChecksumTypeTransfer itemAliasChecksumType = itemControl.getItemAliasChecksumTypeTransfer(userVisit, itemAliasTypeDetail.getItemAliasChecksumType());
            Boolean allowMultiple = itemAliasTypeDetail.getAllowMultiple();
            Boolean isDefault = itemAliasTypeDetail.getIsDefault();
            Integer sortOrder = itemAliasTypeDetail.getSortOrder();
            String description = itemControl.getBestItemAliasTypeDescription(itemAliasType, getLanguage());
            
            itemAliasTypeTransfer = new ItemAliasTypeTransfer(itemAliasTypeName, validationPattern, itemAliasChecksumType, allowMultiple, isDefault, sortOrder,
                    description);
            put(itemAliasType, itemAliasTypeTransfer);
        }
        
        return itemAliasTypeTransfer;
    }
    
}
