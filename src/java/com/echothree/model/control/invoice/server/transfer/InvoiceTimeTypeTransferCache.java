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

package com.echothree.model.control.invoice.server.transfer;

import com.echothree.model.control.invoice.remote.transfer.InvoiceTimeTypeTransfer;
import com.echothree.model.control.invoice.server.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeType;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceTimeTypeTransferCache
        extends BaseInvoiceTransferCache<InvoiceTimeType, InvoiceTimeTypeTransfer> {
    
    /** Creates a new instance of InvoiceTimeTypeTransferCache */
    public InvoiceTimeTypeTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceTimeTypeTransfer getInvoiceTimeTypeTransfer(InvoiceTimeType invoiceTimeType) {
        InvoiceTimeTypeTransfer invoiceTimeTypeTransfer = get(invoiceTimeType);
        
        if(invoiceTimeTypeTransfer == null) {
            InvoiceTimeTypeDetail invoiceTimeTypeDetail = invoiceTimeType.getLastDetail();
            String invoiceTimeTypeName = invoiceTimeTypeDetail.getInvoiceTimeTypeName();
            Boolean isDefault = invoiceTimeTypeDetail.getIsDefault();
            Integer sortOrder = invoiceTimeTypeDetail.getSortOrder();
            String description = invoiceControl.getBestInvoiceTimeTypeDescription(invoiceTimeType, getLanguage());
            
            invoiceTimeTypeTransfer = new InvoiceTimeTypeTransfer(invoiceTimeTypeName, isDefault, sortOrder, description);
            put(invoiceTimeType, invoiceTimeTypeTransfer);
        }
        
        return invoiceTimeTypeTransfer;
    }
    
}
