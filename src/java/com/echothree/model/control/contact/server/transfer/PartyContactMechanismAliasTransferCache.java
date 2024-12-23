// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismAliasTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismAlias;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyContactMechanismAliasTransferCache
        extends BaseContactTransferCache<PartyContactMechanismAlias, PartyContactMechanismAliasTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of PartyContactMechanismAliasTransferCache */
    public PartyContactMechanismAliasTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public PartyContactMechanismAliasTransfer getPartyContactMechanismAliasTransfer(PartyContactMechanismAlias partyContactMechanismAlias) {
        var partyContactMechanismAliasTransfer = get(partyContactMechanismAlias);
        
        if(partyContactMechanismAliasTransfer == null) {
            var party = partyControl.getPartyTransfer(userVisit, partyContactMechanismAlias.getParty());
            var contactMechanism = contactControl.getContactMechanismTransfer(userVisit,
                    partyContactMechanismAlias.getContactMechanism());
            var contactMechanismAliasType = contactControl.getContactMechanismAliasTypeTransfer(userVisit,
                    partyContactMechanismAlias.getContactMechanismAliasType());
            var alias = partyContactMechanismAlias.getAlias();
            
            partyContactMechanismAliasTransfer = new PartyContactMechanismAliasTransfer(party, contactMechanism,
                    contactMechanismAliasType, alias);
            put(partyContactMechanismAlias, partyContactMechanismAliasTransfer);
        }
        
        return partyContactMechanismAliasTransfer;
    }
    
}
