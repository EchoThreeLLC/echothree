// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.invoice.common.transfer.InvoiceLineTypeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTypeTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.invoice.server.entity.InvoiceLineTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceLineTypeTransferCache
        extends BaseInvoiceTransferCache<InvoiceLineType, InvoiceLineTypeTransfer> {
    
    /** Creates a new instance of InvoiceLineTypeTransferCache */
    public InvoiceLineTypeTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceLineTypeTransfer getInvoiceLineTypeTransfer(InvoiceLineType invoiceLineType) {
        InvoiceLineTypeTransfer invoiceLineTypeTransfer = get(invoiceLineType);
        
        if(invoiceLineTypeTransfer == null) {
            InvoiceLineTypeDetail invoiceLineTypeDetail = invoiceLineType.getLastDetail();
            InvoiceTypeTransfer invoiceType = invoiceControl.getInvoiceTypeTransfer(userVisit, invoiceLineTypeDetail.getInvoiceType());
            String invoiceLineTypeName = invoiceLineTypeDetail.getInvoiceLineTypeName();
            InvoiceLineType parentInvoiceLineType = invoiceLineTypeDetail.getParentInvoiceLineType();
            InvoiceLineTypeTransfer parentInvoiceLineTypeTransfer = parentInvoiceLineType == null? null: getInvoiceLineTypeTransfer(parentInvoiceLineType);
            Boolean isDefault = invoiceLineTypeDetail.getIsDefault();
            Integer sortOrder = invoiceLineTypeDetail.getSortOrder();
            String description = invoiceControl.getBestInvoiceLineTypeDescription(invoiceLineType, getLanguage());
            
            invoiceLineTypeTransfer = new InvoiceLineTypeTransfer(invoiceType, invoiceLineTypeName, parentInvoiceLineTypeTransfer,
                    isDefault, sortOrder, description);
            put(invoiceLineType, invoiceLineTypeTransfer);
        }
        
        return invoiceLineTypeTransfer;
    }
    
}
