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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismPurposeTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismPurpose;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PartyContactMechanismPurposeTransferCache
        extends BaseContactTransferCache<PartyContactMechanismPurpose, PartyContactMechanismPurposeTransfer> {
    
    /** Creates a new instance of PartyContactMechanismPurposeTransferCache */
    public PartyContactMechanismPurposeTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
    }
    
    public PartyContactMechanismPurposeTransfer getPartyContactMechanismPurposeTransfer(PartyContactMechanismPurpose partyContactMechanismPurpose) {
        var partyContactMechanismPurposeTransfer = get(partyContactMechanismPurpose);
        
        if(partyContactMechanismPurposeTransfer == null) {
            var partyContactMechanismPurposeDetail = partyContactMechanismPurpose.getLastDetail();
            var isDefault = partyContactMechanismPurposeDetail.getIsDefault();
            var sortOrder = partyContactMechanismPurposeDetail.getSortOrder();
            
            partyContactMechanismPurposeTransfer = new PartyContactMechanismPurposeTransfer(isDefault, sortOrder);
            put(partyContactMechanismPurpose, partyContactMechanismPurposeTransfer);
            
            partyContactMechanismPurposeTransfer.setPartyContactMechanism(contactControl.getPartyContactMechanismTransfer(userVisit, partyContactMechanismPurposeDetail.getPartyContactMechanism()));
            partyContactMechanismPurposeTransfer.setContactMechanismPurpose(contactControl.getContactMechanismPurposeTransfer(userVisit, partyContactMechanismPurposeDetail.getContactMechanismPurpose()));
        }
        
        return partyContactMechanismPurposeTransfer;
    }
    
}
