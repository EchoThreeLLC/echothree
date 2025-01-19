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

package com.echothree.model.control.picklist.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PicklistAliasTransfer
        extends BaseTransfer {
    
    private PicklistTransfer picklist;
    private PicklistAliasTypeTransfer picklistAliasType;
    private String alias;
    
    /** Creates a new instance of PicklistAliasTransfer */
    public PicklistAliasTransfer(PicklistTransfer picklist, PicklistAliasTypeTransfer picklistAliasType, String alias) {
        this.picklist = picklist;
        this.picklistAliasType = picklistAliasType;
        this.alias = alias;
    }

    public PicklistTransfer getPicklist() {
        return picklist;
    }

    public void setPicklist(PicklistTransfer picklist) {
        this.picklist = picklist;
    }

    public PicklistAliasTypeTransfer getPicklistAliasType() {
        return picklistAliasType;
    }

    public void setPicklistAliasType(PicklistAliasTypeTransfer picklistAliasType) {
        this.picklistAliasType = picklistAliasType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
