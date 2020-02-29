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

package com.echothree.model.control.invoice.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class InvoiceRoleTypeTransfer
        extends BaseTransfer {
    
    private String invoiceRoleTypeUseTypeName;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of InvoiceRoleTypeTransfer */
    public InvoiceRoleTypeTransfer(String invoiceRoleTypeUseTypeName, Integer sortOrder, String description) {
        this.invoiceRoleTypeUseTypeName = invoiceRoleTypeUseTypeName;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getInvoiceRoleTypeUseTypeName() {
        return invoiceRoleTypeUseTypeName;
    }
    
    public void setInvoiceRoleTypeUseTypeName(String invoiceRoleTypeUseTypeName) {
        this.invoiceRoleTypeUseTypeName = invoiceRoleTypeUseTypeName;
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
