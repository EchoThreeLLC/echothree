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

package com.echothree.model.control.item.remote.transfer;

import com.echothree.model.control.uom.remote.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class ItemUnitOfMeasureTypeTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of ItemUnitOfMeasureTypeTransfer */
    public ItemUnitOfMeasureTypeTransfer(ItemTransfer item, UnitOfMeasureTypeTransfer unitOfMeasureType, Boolean isDefault,
            Integer sortOrder) {
        this.item = item;
        this.unitOfMeasureType = unitOfMeasureType;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
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
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
