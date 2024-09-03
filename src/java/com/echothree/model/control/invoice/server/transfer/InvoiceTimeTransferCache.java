// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.invoice.common.transfer.InvoiceTimeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTimeTypeTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceTime;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceTimeTransferCache
        extends BaseInvoiceTransferCache<InvoiceTime, InvoiceTimeTransfer> {
    
    /** Creates a new instance of InvoiceTimeTransferCache */
    public InvoiceTimeTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);
    }
    
    public InvoiceTimeTransfer getInvoiceTimeTransfer(InvoiceTime invoiceTime) {
        var invoiceTimeTransfer = get(invoiceTime);
        
        if(invoiceTimeTransfer == null) {
            var invoiceTimeType = invoiceControl.getInvoiceTimeTypeTransfer(userVisit, invoiceTime.getInvoiceTimeType());
            var unformattedTime = invoiceTime.getTime();
            var time = formatTypicalDateTime(unformattedTime);
            
            invoiceTimeTransfer = new InvoiceTimeTransfer(invoiceTimeType, unformattedTime, time);
            put(invoiceTime, invoiceTimeTransfer);
        }
        
        return invoiceTimeTransfer;
    }
    
}
