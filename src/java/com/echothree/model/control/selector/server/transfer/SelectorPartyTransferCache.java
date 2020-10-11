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

package com.echothree.model.control.selector.server.transfer;

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.selector.common.transfer.SelectorPartyTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorParty;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class SelectorPartyTransferCache
        extends BaseSelectorTransferCache<SelectorParty, SelectorPartyTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of SelectorPartyTransferCache */
    public SelectorPartyTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
        
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }
    
    public SelectorPartyTransfer getSelectorPartyTransfer(SelectorParty selectorParty) {
        SelectorPartyTransfer selectorPartyTransfer = get(selectorParty);
        
        if(selectorPartyTransfer == null) {
            SelectorTransfer selectorTransfer = selectorControl.getSelectorTransfer(userVisit, selectorParty.getSelector());
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, selectorParty.getParty());
            
            selectorPartyTransfer = new SelectorPartyTransfer(selectorTransfer, partyTransfer);
            put(selectorParty, selectorPartyTransfer);
        }
        
        return selectorPartyTransfer;
    }
    
}
