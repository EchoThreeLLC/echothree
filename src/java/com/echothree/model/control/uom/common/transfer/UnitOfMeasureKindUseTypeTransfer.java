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

package com.echothree.model.control.uom.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class UnitOfMeasureKindUseTypeTransfer
        extends BaseTransfer {
    
    private String unitOfMeasureKindUseTypeName;
    private Boolean allowMultiple;
    private Boolean allowFractionDigits;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of UnitOfMeasureKindUseTypeTransfer */
    public UnitOfMeasureKindUseTypeTransfer(String unitOfMeasureKindUseTypeName, Boolean allowMultiple, Boolean allowFractionDigits, Boolean isDefault,
            Integer sortOrder, String description) {
        this.unitOfMeasureKindUseTypeName = unitOfMeasureKindUseTypeName;
        this.allowMultiple = allowMultiple;
        this.allowFractionDigits = allowFractionDigits;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the unitOfMeasureKindUseTypeName.
     * @return the unitOfMeasureKindUseTypeName
     */
    public String getUnitOfMeasureKindUseTypeName() {
        return unitOfMeasureKindUseTypeName;
    }

    /**
     * Sets the unitOfMeasureKindUseTypeName.
     * @param unitOfMeasureKindUseTypeName the unitOfMeasureKindUseTypeName to set
     */
    public void setUnitOfMeasureKindUseTypeName(String unitOfMeasureKindUseTypeName) {
        this.unitOfMeasureKindUseTypeName = unitOfMeasureKindUseTypeName;
    }

    /**
     * Returns the allowMultiple.
     * @return the allowMultiple
     */
    public Boolean getAllowMultiple() {
        return allowMultiple;
    }

    /**
     * Sets the allowMultiple.
     * @param allowMultiple the allowMultiple to set
     */
    public void setAllowMultiple(Boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    /**
     * Returns the allowFractionDigits.
     * @return the allowFractionDigits
     */
    public Boolean getAllowFractionDigits() {
        return allowFractionDigits;
    }

    /**
     * Sets the allowFractionDigits.
     * @param allowFractionDigits the allowFractionDigits to set
     */
    public void setAllowFractionDigits(Boolean allowFractionDigits) {
        this.allowFractionDigits = allowFractionDigits;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
