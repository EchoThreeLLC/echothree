// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.term.server.transfer;

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.term.common.transfer.PartyTermTransfer;
import com.echothree.model.control.term.common.transfer.TermTransfer;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.term.server.entity.PartyTerm;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyTermTransferCache
        extends BaseTermTransferCache<PartyTerm, PartyTermTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of PartyTermTransferCache */
    public PartyTermTransferCache(UserVisit userVisit, TermControl termControl) {
        super(userVisit, termControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public PartyTermTransfer getPartyTermTransfer(PartyTerm partyTerm) {
        PartyTermTransfer partyTermTransfer = get(partyTerm);
        
        if(partyTermTransfer == null) {
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, partyTerm.getParty());
            TermTransfer term = termControl.getTermTransfer(userVisit, partyTerm.getTerm());
            Boolean taxable = partyTerm.getTaxable();
            
            partyTermTransfer = new PartyTermTransfer(party, term, taxable);
            put(partyTerm, partyTermTransfer);
        }
        
        return partyTermTransfer;
    }
    
}
