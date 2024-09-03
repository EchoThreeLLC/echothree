// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.inventory.common.transfer.PartyInventoryLevelTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.inventory.server.entity.PartyInventoryLevel;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyInventoryLevelTransferCache
        extends BaseInventoryTransferCache<PartyInventoryLevel, PartyInventoryLevelTransfer> {
    
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyInventoryLevelTransferCache */
    public PartyInventoryLevelTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public PartyInventoryLevelTransfer getTransfer(PartyInventoryLevel partyInventoryLevel) {
        var partyInventoryLevelTransfer = get(partyInventoryLevel);
        
        if(partyInventoryLevelTransfer == null) {
            var partyTransfer = partyControl.getPartyTransfer(userVisit, partyInventoryLevel.getParty());
            var item = partyInventoryLevel.getItem();
            var itemTransfer = itemControl.getItemTransfer(userVisit, item);
            var inventoryConditionTransfer = inventoryControl.getInventoryConditionTransfer(userVisit, partyInventoryLevel.getInventoryCondition());
            var unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
            var minimumInventory = formatUnitOfMeasure(unitOfMeasureKind, partyInventoryLevel.getMinimumInventory());
            var maximumInventory = formatUnitOfMeasure(unitOfMeasureKind, partyInventoryLevel.getMaximumInventory());
            var reorderQuantity = formatUnitOfMeasure(unitOfMeasureKind, partyInventoryLevel.getReorderQuantity());
            
            partyInventoryLevelTransfer = new PartyInventoryLevelTransfer(partyTransfer, itemTransfer, inventoryConditionTransfer,
                    minimumInventory, maximumInventory, reorderQuantity);
            put(partyInventoryLevel, partyInventoryLevelTransfer);
        }
        
        return partyInventoryLevelTransfer;
    }
    
}
