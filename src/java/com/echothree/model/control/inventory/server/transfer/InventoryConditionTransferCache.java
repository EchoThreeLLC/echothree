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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InventoryConditionTransferCache
        extends BaseInventoryTransferCache<InventoryCondition, InventoryConditionTransfer> {
    
    /** Creates a new instance of InventoryConditionTransferCache */
    public InventoryConditionTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public InventoryConditionTransfer getTransfer(InventoryCondition inventoryCondition) {
        InventoryConditionTransfer inventoryConditionTransfer = get(inventoryCondition);
        
        if(inventoryConditionTransfer == null) {
            InventoryConditionDetail inventoryConditionDetail = inventoryCondition.getLastDetail();
            String inventoryConditionName = inventoryConditionDetail.getInventoryConditionName();
            Boolean isDefault = inventoryConditionDetail.getIsDefault();
            Integer sortOrder = inventoryConditionDetail.getSortOrder();
            String description = inventoryControl.getBestInventoryConditionDescription(inventoryCondition, getLanguage());
            
            inventoryConditionTransfer = new InventoryConditionTransfer(inventoryConditionName, isDefault, sortOrder, description);
            put(inventoryCondition, inventoryConditionTransfer);
        }
        
        return inventoryConditionTransfer;
    }
    
}
