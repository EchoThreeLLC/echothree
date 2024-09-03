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

import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupVolumeTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupVolume;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class InventoryLocationGroupVolumeTransferCache
        extends BaseInventoryTransferCache<InventoryLocationGroupVolume, InventoryLocationGroupVolumeTransfer> {
    
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    /** Creates a new instance of InventoryLocationGroupVolumeTransferCache */
    public InventoryLocationGroupVolumeTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public InventoryLocationGroupVolumeTransfer getTransfer(InventoryLocationGroupVolume inventoryLocationGroupVolume) {
        var inventoryLocationGroupVolumeTransfer = get(inventoryLocationGroupVolume);
        
        if(inventoryLocationGroupVolumeTransfer == null) {
            var inventoryLocationGroupTransfer = inventoryControl.getInventoryLocationGroupTransfer(userVisit, inventoryLocationGroupVolume.getInventoryLocationGroup());
            var volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
            var height = formatUnitOfMeasure(volumeUnitOfMeasureKind, inventoryLocationGroupVolume.getHeight());
            var width = formatUnitOfMeasure(volumeUnitOfMeasureKind, inventoryLocationGroupVolume.getWidth());
            var depth = formatUnitOfMeasure(volumeUnitOfMeasureKind, inventoryLocationGroupVolume.getDepth());
            
            inventoryLocationGroupVolumeTransfer = new InventoryLocationGroupVolumeTransfer(inventoryLocationGroupTransfer, height, width, depth);
            put(inventoryLocationGroupVolume, inventoryLocationGroupVolumeTransfer);
        }
        
        return inventoryLocationGroupVolumeTransfer;
    }
    
}
