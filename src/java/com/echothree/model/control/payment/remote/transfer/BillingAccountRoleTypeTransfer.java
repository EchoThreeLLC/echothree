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

package com.echothree.model.control.payment.remote.transfer;

import com.echothree.util.remote.transfer.BaseTransfer;

public class BillingAccountRoleTypeTransfer
        extends BaseTransfer {
    
    private String billingAccountRoleTypeName;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of BillingAccountRoleTypeTransfer */
    public BillingAccountRoleTypeTransfer(String billingAccountRoleTypeName, Integer sortOrder, String description) {
        this.billingAccountRoleTypeName = billingAccountRoleTypeName;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getBillingAccountRoleTypeName() {
        return billingAccountRoleTypeName;
    }
    
    public void setBillingAccountRoleTypeName(String billingAccountRoleTypeName) {
        this.billingAccountRoleTypeName = billingAccountRoleTypeName;
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
