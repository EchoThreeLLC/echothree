// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.carrier.server.transfer;



import com.echothree.model.control.carrier.common.transfer.CarrierTransfer;
import com.echothree.model.control.carrier.common.transfer.PartyCarrierTransfer;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.carrier.server.entity.PartyCarrier;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyCarrierTransferCache
        extends BaseCarrierTransferCache<PartyCarrier, PartyCarrierTransfer> {
    
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyCarrierTransferCache */
    public PartyCarrierTransferCache(UserVisit userVisit, CarrierControl carrierControl) {
        super(userVisit, carrierControl);
    }
    
    public PartyCarrierTransfer getPartyCarrierTransfer(PartyCarrier partyCarrier) {
        PartyCarrierTransfer partyCarrierTransfer = get(partyCarrier);
        
        if(partyCarrierTransfer == null) {
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, partyCarrier.getParty());
            CarrierTransfer carrier = carrierControl.getCarrierTransfer(userVisit, partyCarrier.getCarrierParty());
            
            partyCarrierTransfer = new PartyCarrierTransfer(party, carrier);
            put(partyCarrier, partyCarrierTransfer);
        }
        
        return partyCarrierTransfer;
    }
    
}
