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

package com.echothree.control.user.contactlist.common.result;

import com.echothree.model.control.contactlist.common.transfer.ContactListTransfer;
import com.echothree.model.control.contactlist.common.transfer.PartyTypeContactListTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetPartyTypeContactListsResult
        extends BaseResult {
    
    PartyTypeTransfer getPartyType();
    void setPartyType(PartyTypeTransfer partyType);
    
    ContactListTransfer getContactList();
    void setContactList(ContactListTransfer contactList);
    
    List<PartyTypeContactListTransfer> getPartyTypeContactLists();
    void setPartyTypeContactLists(List<PartyTypeContactListTransfer> partyTypeContactLists);
    
}
