// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.inventory.common.transfer.AllocationPriorityTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.user.server.entity.UserVisit;

public class AllocationPriorityTransferCache
        extends BaseInventoryTransferCache<AllocationPriority, AllocationPriorityTransfer> {
    
    /** Creates a new instance of AllocationPriorityTransferCache */
    public AllocationPriorityTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public AllocationPriorityTransfer getTransfer(AllocationPriority allocationPriority) {
        var allocationPriorityTransfer = get(allocationPriority);
        
        if(allocationPriorityTransfer == null) {
            var allocationPriorityDetail = allocationPriority.getLastDetail();
            var allocationPriorityName = allocationPriorityDetail.getAllocationPriorityName();
            var priority = allocationPriorityDetail.getPriority();
            var isDefault = allocationPriorityDetail.getIsDefault();
            var sortOrder = allocationPriorityDetail.getSortOrder();
            var description = inventoryControl.getBestAllocationPriorityDescription(allocationPriority, getLanguage());
            
            allocationPriorityTransfer = new AllocationPriorityTransfer(allocationPriorityName, priority, isDefault, sortOrder, description);
            put(allocationPriority, allocationPriorityTransfer);
        }
        
        return allocationPriorityTransfer;
    }
    
}
