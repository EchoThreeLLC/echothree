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

package com.echothree.model.control.picklist.remote.transfer;

import com.echothree.util.remote.transfer.BaseTransfer;

public class PicklistAliasTypeTransfer
        extends BaseTransfer {
    
    private PicklistTypeTransfer picklistType;
    private String picklistAliasTypeName;
    private String validationPattern;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of PicklistAliasTypeTransfer */
    public PicklistAliasTypeTransfer(PicklistTypeTransfer picklistType, String picklistAliasTypeName, String validationPattern,
            Boolean isDefault, Integer sortOrder, String description) {
        this.picklistType = picklistType;
        this.picklistAliasTypeName = picklistAliasTypeName;
        this.validationPattern = validationPattern;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public PicklistTypeTransfer getPicklistType() {
        return picklistType;
    }

    public void setPicklistType(PicklistTypeTransfer picklistType) {
        this.picklistType = picklistType;
    }

    public String getPicklistAliasTypeName() {
        return picklistAliasTypeName;
    }

    public void setPicklistAliasTypeName(String picklistAliasTypeName) {
        this.picklistAliasTypeName = picklistAliasTypeName;
    }

    public String getValidationPattern() {
        return validationPattern;
    }

    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
