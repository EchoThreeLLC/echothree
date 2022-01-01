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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.InventoryConditionUseTypeTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InventoryConditionUseTypeTransferCache
        extends BaseInventoryTransferCache<InventoryConditionUseType, InventoryConditionUseTypeTransfer> {
    
    /** Creates a new instance of InventoryConditionUseTypeTransferCache */
    public InventoryConditionUseTypeTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public InventoryConditionUseTypeTransfer getTransfer(InventoryConditionUseType inventoryConditionUseType) {
        InventoryConditionUseTypeTransfer inventoryConditionUseTypeTransfer = get(inventoryConditionUseType);
        
        if(inventoryConditionUseTypeTransfer == null) {
            String inventoryConditionUseTypeName = inventoryConditionUseType.getInventoryConditionUseTypeName();
            Boolean isDefault = inventoryConditionUseType.getIsDefault();
            Integer sortOrder = inventoryConditionUseType.getSortOrder();
            String description = inventoryControl.getBestInventoryConditionUseTypeDescription(inventoryConditionUseType, getLanguage());
            
            inventoryConditionUseTypeTransfer = new InventoryConditionUseTypeTransfer(inventoryConditionUseTypeName, isDefault,
                    sortOrder, description);
            put(inventoryConditionUseType, inventoryConditionUseTypeTransfer);
        }
        
        return inventoryConditionUseTypeTransfer;
    }
    
}
