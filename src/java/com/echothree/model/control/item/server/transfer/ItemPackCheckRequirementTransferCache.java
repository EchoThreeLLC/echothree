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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.ItemPackCheckRequirementTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.ItemPackCheckRequirement;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemPackCheckRequirementTransferCache
        extends BaseItemTransferCache<ItemPackCheckRequirement, ItemPackCheckRequirementTransfer> {
    
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ItemPackCheckRequirementTransferCache */
    public ItemPackCheckRequirementTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public ItemPackCheckRequirementTransfer getTransfer(ItemPackCheckRequirement itemPackCheckRequirement) {
        var itemPackCheckRequirementTransfer = get(itemPackCheckRequirement);
        
        if(itemPackCheckRequirementTransfer == null) {
            var item = itemControl.getItemTransfer(userVisit, itemPackCheckRequirement.getItem());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemPackCheckRequirement.getUnitOfMeasureType());
            var longMinimumQuantity = itemPackCheckRequirement.getMinimumQuantity();
            var minimumQuantity = longMinimumQuantity == null ? null : longMinimumQuantity.toString();
            var longMaximumQuantity = itemPackCheckRequirement.getMaximumQuantity();
            var maximumQuantity = longMaximumQuantity == null ? null : longMaximumQuantity.toString();
            
            itemPackCheckRequirementTransfer = new ItemPackCheckRequirementTransfer(item, unitOfMeasureType, minimumQuantity, maximumQuantity);
            put(itemPackCheckRequirement, itemPackCheckRequirementTransfer);
        }
        
        return itemPackCheckRequirementTransfer;
    }
    
}
