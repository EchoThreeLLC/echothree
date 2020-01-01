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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InventoryLocationGroupDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<InventoryLocationGroupDescription, InventoryLocationGroupDescriptionTransfer> {
    
    /** Creates a new instance of InventoryLocationGroupDescriptionTransferCache */
    public InventoryLocationGroupDescriptionTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public InventoryLocationGroupDescriptionTransfer getTransfer(InventoryLocationGroupDescription inventoryLocationGroupDescription) {
        InventoryLocationGroupDescriptionTransfer inventoryLocationGroupDescriptionTransfer = get(inventoryLocationGroupDescription);
        
        if(inventoryLocationGroupDescriptionTransfer == null) {
            InventoryLocationGroupTransferCache inventoryLocationGroupTransferCache = inventoryControl.getInventoryTransferCaches(userVisit).getInventoryLocationGroupTransferCache();
            InventoryLocationGroupTransfer inventoryLocationGroupTransfer = inventoryLocationGroupTransferCache.getTransfer(inventoryLocationGroupDescription.getInventoryLocationGroup());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, inventoryLocationGroupDescription.getLanguage());
            
            inventoryLocationGroupDescriptionTransfer = new InventoryLocationGroupDescriptionTransfer(languageTransfer, inventoryLocationGroupTransfer, inventoryLocationGroupDescription.getDescription());
            put(inventoryLocationGroupDescription, inventoryLocationGroupDescriptionTransfer);
        }
        
        return inventoryLocationGroupDescriptionTransfer;
    }
    
}
