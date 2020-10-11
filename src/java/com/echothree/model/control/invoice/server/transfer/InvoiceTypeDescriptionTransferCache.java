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

package com.echothree.model.control.invoice.server.transfer;

import com.echothree.model.control.invoice.common.transfer.InvoiceTypeDescriptionTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTypeTransfer;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.invoice.server.entity.InvoiceTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InvoiceTypeDescriptionTransferCache
        extends BaseInvoiceDescriptionTransferCache<InvoiceTypeDescription, InvoiceTypeDescriptionTransfer> {
    
    /** Creates a new instance of InvoiceTypeDescriptionTransferCache */
    public InvoiceTypeDescriptionTransferCache(UserVisit userVisit, InvoiceControl invoiceControl) {
        super(userVisit, invoiceControl);
    }
    
    public InvoiceTypeDescriptionTransfer getInvoiceTypeDescriptionTransfer(InvoiceTypeDescription invoiceTypeDescription) {
        InvoiceTypeDescriptionTransfer invoiceTypeDescriptionTransfer = get(invoiceTypeDescription);
        
        if(invoiceTypeDescriptionTransfer == null) {
            InvoiceTypeTransfer invoiceTypeTransfer = invoiceControl.getInvoiceTypeTransfer(userVisit, invoiceTypeDescription.getInvoiceType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, invoiceTypeDescription.getLanguage());
            
            invoiceTypeDescriptionTransfer = new InvoiceTypeDescriptionTransfer(languageTransfer, invoiceTypeTransfer, invoiceTypeDescription.getDescription());
            put(invoiceTypeDescription, invoiceTypeDescriptionTransfer);
        }
        
        return invoiceTypeDescriptionTransfer;
    }
    
}
