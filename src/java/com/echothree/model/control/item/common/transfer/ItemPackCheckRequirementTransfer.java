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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemPackCheckRequirementTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private String minimumQuantity;
    private String maximumQuantity;
    
    /** Creates a new instance of ItemPackCheckRequirementTransfer */
    public ItemPackCheckRequirementTransfer(ItemTransfer item, UnitOfMeasureTypeTransfer unitOfMeasureType, String minimumQuantity,
            String maximumQuantity) {
        this.item = item;
        this.unitOfMeasureType = unitOfMeasureType;
        this.minimumQuantity = minimumQuantity;
        this.maximumQuantity = maximumQuantity;
    }
    
    public ItemTransfer getItem() {
        return item;
    }
    
    public void setItem(ItemTransfer item) {
        this.item = item;
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }
    
    public String getMinimumQuantity() {
        return minimumQuantity;
    }
    
    public void setMinimumQuantity(String minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }
    
    public String getMaximumQuantity() {
        return maximumQuantity;
    }
    
    public void setMaximumQuantity(String maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }
    
}
