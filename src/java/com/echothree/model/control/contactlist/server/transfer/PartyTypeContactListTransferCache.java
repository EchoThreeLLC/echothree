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

package com.echothree.model.control.contactlist.server.transfer;

import com.echothree.model.control.contactlist.common.transfer.ContactListTransfer;
import com.echothree.model.control.contactlist.common.transfer.PartyTypeContactListTransfer;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactList;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyTypeContactListTransferCache
        extends BaseContactListTransferCache<PartyTypeContactList, PartyTypeContactListTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of PartyTypeContactListTransferCache */
    public PartyTypeContactListTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
    }
    
    public PartyTypeContactListTransfer getPartyTypeContactListTransfer(PartyTypeContactList partyTypeContactList) {
        PartyTypeContactListTransfer partyTypeContactListTransfer = get(partyTypeContactList);
        
        if(partyTypeContactListTransfer == null) {
            PartyTypeTransfer partyTypeTransfer = partyControl.getPartyTypeTransfer(userVisit, partyTypeContactList.getPartyType());
            ContactListTransfer contactListTransfer = contactListControl.getContactListTransfer(userVisit, partyTypeContactList.getContactList());
            Boolean addWhenCreated = partyTypeContactList.getAddWhenCreated();
            
            partyTypeContactListTransfer = new PartyTypeContactListTransfer(partyTypeTransfer, contactListTransfer, addWhenCreated);
            put(partyTypeContactList, partyTypeContactListTransfer);
        }
        
        return partyTypeContactListTransfer;
    }
    
}
