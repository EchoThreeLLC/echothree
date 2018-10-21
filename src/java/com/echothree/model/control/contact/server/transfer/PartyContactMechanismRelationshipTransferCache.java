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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.remote.transfer.PartyContactMechanismRelationshipTransfer;
import com.echothree.model.control.contact.remote.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismRelationship;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartyContactMechanismRelationshipTransferCache
        extends BaseContactTransferCache<PartyContactMechanismRelationship, PartyContactMechanismRelationshipTransfer> {
    
    /** Creates a new instance of PartyContactMechanismRelationshipTransferCache */
    public PartyContactMechanismRelationshipTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
    }
    
    public PartyContactMechanismRelationshipTransfer getPartyContactMechanismRelationshipTransfer(PartyContactMechanismRelationship partyContactMechanismRelationship) {
        PartyContactMechanismRelationshipTransfer partyContactMechanismRelationshipTransfer = get(partyContactMechanismRelationship);
        
        if(partyContactMechanismRelationshipTransfer == null) {
            PartyContactMechanismTransfer fromPartyContactMechanism = contactControl.getPartyContactMechanismTransfer(userVisit, partyContactMechanismRelationship.getFromPartyContactMechanism());
            PartyContactMechanismTransfer toPartyContactMechanism = contactControl.getPartyContactMechanismTransfer(userVisit, partyContactMechanismRelationship.getToPartyContactMechanism());
            
            partyContactMechanismRelationshipTransfer = new PartyContactMechanismRelationshipTransfer(fromPartyContactMechanism, toPartyContactMechanism);
            put(partyContactMechanismRelationship, partyContactMechanismRelationshipTransfer);
        }
        
        return partyContactMechanismRelationshipTransfer;
    }
    
}
