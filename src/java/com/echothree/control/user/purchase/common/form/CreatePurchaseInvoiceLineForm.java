// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.purchase.common.form;

import com.echothree.control.user.accounting.common.spec.GlAccountSpec;
import com.echothree.control.user.inventory.common.spec.InventoryConditionSpec;
import com.echothree.control.user.invoice.common.spec.InvoiceLineTypeSpec;
import com.echothree.control.user.item.common.spec.ItemSpec;
import com.echothree.control.user.purchase.common.spec.PurchaseInvoiceSpec;

public interface CreatePurchaseInvoiceLineForm
        extends PurchaseInvoiceSpec, InvoiceLineTypeSpec, GlAccountSpec, ItemSpec, InventoryConditionSpec {
    
    String getInvoiceLineSequence();
    void setInvoiceLineSequence(String invoiceLineSequence);
    
    String getParentInvoiceLine();
    void setParentInvoiceLine(String parentInvoiceLine);
    
    String getAmount();
    void setAmount(String amount);
    
    String getDescription();
    void setDescription(String description);
    
    String getUnitOfMeasureTypeName();
    void setUnitOfMeasureTypeName(String unitOfMeasureTypeName);
    
    String getQuantity();
    void setQuantity(String quantity);
    
}
