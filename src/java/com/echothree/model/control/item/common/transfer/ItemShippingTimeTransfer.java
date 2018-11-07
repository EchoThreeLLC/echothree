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

package com.echothree.model.control.item.common.transfer;

import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ItemShippingTimeTransfer
        extends BaseTransfer {
    
    private ItemTransfer item;
    private CustomerTypeTransfer customerType;
    private Long unformattedShippingStartTime;
    private String shippingStartTime;
    private Long unformattedShippingEndTime;
    private String shippingEndTime;
    
    /** Creates a new instance of ItemShippingTimeTransfer */
    public ItemShippingTimeTransfer(ItemTransfer item, CustomerTypeTransfer customerType, Long unformattedShippingStartTime, String shippingStartTime,
            Long unformattedShippingEndTime, String shippingEndTime) {
        this.item = item;
        this.customerType = customerType;
        this.unformattedShippingStartTime = unformattedShippingStartTime;
        this.shippingStartTime = shippingStartTime;
        this.unformattedShippingEndTime = unformattedShippingEndTime;
        this.shippingEndTime = shippingEndTime;
    }

    /**
     * @return the item
     */
    public ItemTransfer getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(ItemTransfer item) {
        this.item = item;
    }

    /**
     * @return the customerType
     */
    public CustomerTypeTransfer getCustomerType() {
        return customerType;
    }

    /**
     * @param customerType the customerType to set
     */
    public void setCustomerType(CustomerTypeTransfer customerType) {
        this.customerType = customerType;
    }

    /**
     * @return the unformattedShippingStartTime
     */
    public Long getUnformattedShippingStartTime() {
        return unformattedShippingStartTime;
    }

    /**
     * @param unformattedShippingStartTime the unformattedShippingStartTime to set
     */
    public void setUnformattedShippingStartTime(Long unformattedShippingStartTime) {
        this.unformattedShippingStartTime = unformattedShippingStartTime;
    }

    /**
     * @return the shippingStartTime
     */
    public String getShippingStartTime() {
        return shippingStartTime;
    }

    /**
     * @param shippingStartTime the shippingStartTime to set
     */
    public void setShippingStartTime(String shippingStartTime) {
        this.shippingStartTime = shippingStartTime;
    }

    /**
     * @return the unformattedShippingEndTime
     */
    public Long getUnformattedShippingEndTime() {
        return unformattedShippingEndTime;
    }

    /**
     * @param unformattedShippingEndTime the unformattedShippingEndTime to set
     */
    public void setUnformattedShippingEndTime(Long unformattedShippingEndTime) {
        this.unformattedShippingEndTime = unformattedShippingEndTime;
    }

    /**
     * @return the shippingEndTime
     */
    public String getShippingEndTime() {
        return shippingEndTime;
    }

    /**
     * @param shippingEndTime the shippingEndTime to set
     */
    public void setShippingEndTime(String shippingEndTime) {
        this.shippingEndTime = shippingEndTime;
    }
    
 }
