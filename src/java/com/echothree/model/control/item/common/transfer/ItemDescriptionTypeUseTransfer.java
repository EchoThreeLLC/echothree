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

package com.echothree.model.control.item.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ItemDescriptionTypeUseTransfer
        extends BaseTransfer {
    
    private ItemDescriptionTypeTransfer itemDescriptionType;
    private ItemDescriptionTypeUseTypeTransfer itemDescriptionTypeUseType;
    
    /** Creates a new instance of ItemDescriptionTypeUseTransfer */
    public ItemDescriptionTypeUseTransfer(ItemDescriptionTypeTransfer itemDescriptionType, ItemDescriptionTypeUseTypeTransfer itemDescriptionTypeUseType) {
        this.itemDescriptionType = itemDescriptionType;
        this.itemDescriptionTypeUseType = itemDescriptionTypeUseType;
    }

    /**
     * Returns the itemDescriptionType.
     * @return the itemDescriptionType
     */
    public ItemDescriptionTypeTransfer getItemDescriptionType() {
        return itemDescriptionType;
    }

    /**
     * Sets the itemDescriptionType.
     * @param itemDescriptionType the itemDescriptionType to set
     */
    public void setItemDescriptionType(ItemDescriptionTypeTransfer itemDescriptionType) {
        this.itemDescriptionType = itemDescriptionType;
    }

    /**
     * Returns the itemDescriptionTypeUseType.
     * @return the itemDescriptionTypeUseType
     */
    public ItemDescriptionTypeUseTypeTransfer getItemDescriptionTypeUseType() {
        return itemDescriptionTypeUseType;
    }

    /**
     * Sets the itemDescriptionTypeUseType.
     * @param itemDescriptionTypeUseType the itemDescriptionTypeUseType to set
     */
    public void setItemDescriptionTypeUseType(ItemDescriptionTypeUseTypeTransfer itemDescriptionTypeUseType) {
        this.itemDescriptionTypeUseType = itemDescriptionTypeUseType;
    }
    
}
