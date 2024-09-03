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

package com.echothree.model.control.printer.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterGroupUseTypeDescriptionTransfer;
import com.echothree.model.control.printer.common.transfer.PrinterGroupUseTypeTransfer;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.data.printer.server.entity.PrinterGroupUseTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PrinterGroupUseTypeDescriptionTransferCache
        extends BasePrinterDescriptionTransferCache<PrinterGroupUseTypeDescription, PrinterGroupUseTypeDescriptionTransfer> {
    
    /** Creates a new instance of PrinterGroupUseTypeDescriptionTransferCache */
    public PrinterGroupUseTypeDescriptionTransferCache(UserVisit userVisit, PrinterControl printerControl) {
        super(userVisit, printerControl);
    }
    
    public PrinterGroupUseTypeDescriptionTransfer getPrinterGroupUseTypeDescriptionTransfer(PrinterGroupUseTypeDescription printerGroupUseTypeDescription) {
        var printerGroupUseTypeDescriptionTransfer = get(printerGroupUseTypeDescription);
        
        if(printerGroupUseTypeDescriptionTransfer == null) {
            var printerGroupUseTypeTransfer = printerControl.getPrinterGroupUseTypeTransfer(userVisit, printerGroupUseTypeDescription.getPrinterGroupUseType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, printerGroupUseTypeDescription.getLanguage());
            
            printerGroupUseTypeDescriptionTransfer = new PrinterGroupUseTypeDescriptionTransfer(languageTransfer, printerGroupUseTypeTransfer, printerGroupUseTypeDescription.getDescription());
            put(printerGroupUseTypeDescription, printerGroupUseTypeDescriptionTransfer);
        }
        
        return printerGroupUseTypeDescriptionTransfer;
    }
    
}
