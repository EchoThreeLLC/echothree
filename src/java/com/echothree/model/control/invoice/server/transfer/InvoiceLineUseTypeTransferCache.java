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

package com.echothree.model.control.invoice.server.transfer;

import com.echothree.model.control.invoice.common.transfer.InvoiceLineUseTypeTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceLineUseType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceLineUseTypeTransferCache
        extends BaseInvoiceTransferCache<InvoiceLineUseType, InvoiceLineUseTypeTransfer> {
    
    /** Creates a new instance of InvoiceLineUseTypeTransferCache */
    public InvoiceLineUseTypeTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);
    }
    
    public InvoiceLineUseTypeTransfer getInvoiceLineUseTypeTransfer(InvoiceLineUseType invoiceLineUseType) {
        var invoiceLineUseTypeTransfer = get(invoiceLineUseType);
        
        if(invoiceLineUseTypeTransfer == null) {
            var invoiceLineUseTypeName = invoiceLineUseType.getInvoiceLineUseTypeName();
            var description = invoiceControl.getBestInvoiceLineUseTypeDescription(invoiceLineUseType, getLanguage());
            
            invoiceLineUseTypeTransfer = new InvoiceLineUseTypeTransfer(invoiceLineUseTypeName, description);
            put(invoiceLineUseType, invoiceLineUseTypeTransfer);
        }
        
        return invoiceLineUseTypeTransfer;
    }
    
}
