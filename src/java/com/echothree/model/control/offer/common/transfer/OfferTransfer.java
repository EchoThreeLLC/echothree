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

package com.echothree.model.control.offer.common.transfer;

import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.party.common.transfer.DepartmentTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class OfferTransfer
        extends BaseTransfer {
    
    private String offerName;
    private SequenceTransfer salesOrderSequence;
    private DepartmentTransfer department;
    private SelectorTransfer offerItemSelector;
    private FilterTransfer offerItemPriceFilter;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<OfferNameElementTransfer> offerNameElements;
    private ListWrapper<OfferCustomerTypeTransfer> offerCustomerTypes;
            
    /** Creates a new instance of OfferTransfer */
    public OfferTransfer(String offerName, SequenceTransfer salesOrderSequence, DepartmentTransfer department, SelectorTransfer offerItemSelector,
            FilterTransfer offerItemPriceFilter, Boolean isDefault, Integer sortOrder, String description) {
        this.offerName = offerName;
        this.salesOrderSequence = salesOrderSequence;
        this.department = department;
        this.offerItemSelector = offerItemSelector;
        this.offerItemPriceFilter = offerItemPriceFilter;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the offerName
     */
    public String getOfferName() {
        return offerName;
    }

    /**
     * @param offerName the offerName to set
     */
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    /**
     * @return the salesOrderSequence
     */
    public SequenceTransfer getSalesOrderSequence() {
        return salesOrderSequence;
    }

    /**
     * @param salesOrderSequence the salesOrderSequence to set
     */
    public void setSalesOrderSequence(SequenceTransfer salesOrderSequence) {
        this.salesOrderSequence = salesOrderSequence;
    }

    /**
     * @return the department
     */
    public DepartmentTransfer getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(DepartmentTransfer department) {
        this.department = department;
    }

    /**
     * @return the offerItemSelector
     */
    public SelectorTransfer getOfferItemSelector() {
        return offerItemSelector;
    }

    /**
     * @param offerItemSelector the offerItemSelector to set
     */
    public void setOfferItemSelector(SelectorTransfer offerItemSelector) {
        this.offerItemSelector = offerItemSelector;
    }

    /**
     * @return the offerItemPriceFilter
     */
    public FilterTransfer getOfferItemPriceFilter() {
        return offerItemPriceFilter;
    }

    /**
     * @param offerItemPriceFilter the offerItemPriceFilter to set
     */
    public void setOfferItemPriceFilter(FilterTransfer offerItemPriceFilter) {
        this.offerItemPriceFilter = offerItemPriceFilter;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the offerNameElements
     */
    public ListWrapper<OfferNameElementTransfer> getOfferNameElements() {
        return offerNameElements;
    }

    /**
     * @param offerNameElements the offerNameElements to set
     */
    public void setOfferNameElements(ListWrapper<OfferNameElementTransfer> offerNameElements) {
        this.offerNameElements = offerNameElements;
    }

    /**
     * @return the offerCustomerTypes
     */
    public ListWrapper<OfferCustomerTypeTransfer> getOfferCustomerTypes() {
        return offerCustomerTypes;
    }

    /**
     * @param offerCustomerTypes the offerCustomerTypes to set
     */
    public void setOfferCustomerTypes(ListWrapper<OfferCustomerTypeTransfer> offerCustomerTypes) {
        this.offerCustomerTypes = offerCustomerTypes;
    }
    
}
