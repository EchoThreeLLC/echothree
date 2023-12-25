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

package com.echothree.model.control.contactlist.server.transfer;

import com.echothree.model.control.contactlist.common.transfer.ContactListDescriptionTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListTransfer;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.contactlist.server.entity.ContactListDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContactListDescriptionTransferCache
        extends BaseContactListDescriptionTransferCache<ContactListDescription, ContactListDescriptionTransfer> {
    
    /** Creates a new instance of ContactListDescriptionTransferCache */
    public ContactListDescriptionTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
    }
    
    public ContactListDescriptionTransfer getContactListDescriptionTransfer(ContactListDescription contactListDescription) {
        ContactListDescriptionTransfer contactListDescriptionTransfer = get(contactListDescription);
        
        if(contactListDescriptionTransfer == null) {
            ContactListTransfer contactListTransfer = contactListControl.getContactListTransfer(userVisit, contactListDescription.getContactList());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, contactListDescription.getLanguage());
            
            contactListDescriptionTransfer = new ContactListDescriptionTransfer(languageTransfer, contactListTransfer, contactListDescription.getDescription());
            put(contactListDescription, contactListDescriptionTransfer);
        }
        
        return contactListDescriptionTransfer;
    }
    
}
