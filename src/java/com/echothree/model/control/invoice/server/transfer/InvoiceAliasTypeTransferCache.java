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

import com.echothree.model.control.invoice.common.transfer.InvoiceAliasTypeTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.data.invoice.server.entity.InvoiceAliasType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceAliasTypeTransferCache
        extends BaseInvoiceTransferCache<InvoiceAliasType, InvoiceAliasTypeTransfer> {
    
    /** Creates a new instance of InvoiceAliasTypeTransferCache */
    public InvoiceAliasTypeTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public InvoiceAliasTypeTransfer getInvoiceAliasTypeTransfer(InvoiceAliasType invoiceAliasType) {
        var invoiceAliasTypeTransfer = get(invoiceAliasType);
        
        if(invoiceAliasTypeTransfer == null) {
            var invoiceAliasTypeDetail = invoiceAliasType.getLastDetail();
            var invoiceType = invoiceControl.getInvoiceTypeTransfer(userVisit, invoiceAliasTypeDetail.getInvoiceType());
            var invoiceAliasTypeName = invoiceAliasTypeDetail.getInvoiceAliasTypeName();
            var validationPattern = invoiceAliasTypeDetail.getValidationPattern();
            var isDefault = invoiceAliasTypeDetail.getIsDefault();
            var sortOrder = invoiceAliasTypeDetail.getSortOrder();
            var description = invoiceControl.getBestInvoiceAliasTypeDescription(invoiceAliasType, getLanguage());
            
            invoiceAliasTypeTransfer = new InvoiceAliasTypeTransfer(invoiceType, invoiceAliasTypeName, validationPattern, isDefault, sortOrder, description);
            put(invoiceAliasType, invoiceAliasTypeTransfer);
        }
        
        return invoiceAliasTypeTransfer;
    }
    
}
