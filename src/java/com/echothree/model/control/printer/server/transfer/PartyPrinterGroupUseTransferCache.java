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

package com.echothree.model.control.printer.server.transfer;

import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.printer.remote.transfer.PartyPrinterGroupUseTransfer;
import com.echothree.model.control.printer.remote.transfer.PrinterGroupTransfer;
import com.echothree.model.control.printer.remote.transfer.PrinterGroupUseTypeTransfer;
import com.echothree.model.control.printer.server.PrinterControl;
import com.echothree.model.data.printer.server.entity.PartyPrinterGroupUse;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyPrinterGroupUseTransferCache
        extends BasePrinterTransferCache<PartyPrinterGroupUse, PartyPrinterGroupUseTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of PartyPrinterGroupUseTransferCache */
    public PartyPrinterGroupUseTransferCache(UserVisit userVisit, PrinterControl printerControl) {
        super(userVisit, printerControl);
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }
    
    public PartyPrinterGroupUseTransfer getPartyPrinterGroupUseTransfer(PartyPrinterGroupUse partyPrinterGroupUse) {
        PartyPrinterGroupUseTransfer partyPrinterGroupUseTransfer = get(partyPrinterGroupUse);
        
        if(partyPrinterGroupUseTransfer == null) {
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, partyPrinterGroupUse.getParty());
            PrinterGroupUseTypeTransfer printerGroupUseType = printerControl.getPrinterGroupUseTypeTransfer(userVisit,
                    partyPrinterGroupUse.getPrinterGroupUseType());
            PrinterGroupTransfer printerGroup = printerControl.getPrinterGroupTransfer(userVisit,
                    partyPrinterGroupUse.getPrinterGroup());
            
            partyPrinterGroupUseTransfer = new PartyPrinterGroupUseTransfer(party, printerGroupUseType, printerGroup);
            put(partyPrinterGroupUse, partyPrinterGroupUseTransfer);
        }
        
        return partyPrinterGroupUseTransfer;
    }
    
}
