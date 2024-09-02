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

import com.echothree.model.control.contactlist.common.transfer.ContactListGroupDescriptionTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListGroupTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.contactlist.server.entity.ContactListGroupDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContactListGroupDescriptionTransferCache
        extends BaseContactListDescriptionTransferCache<ContactListGroupDescription, ContactListGroupDescriptionTransfer> {
    
    /** Creates a new instance of ContactListGroupDescriptionTransferCache */
    public ContactListGroupDescriptionTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
    }
    
    public ContactListGroupDescriptionTransfer getContactListGroupDescriptionTransfer(ContactListGroupDescription contactListGroupDescription) {
        ContactListGroupDescriptionTransfer contactListGroupDescriptionTransfer = get(contactListGroupDescription);
        
        if(contactListGroupDescriptionTransfer == null) {
            ContactListGroupTransfer contactListGroupTransfer = contactListControl.getContactListGroupTransfer(userVisit, contactListGroupDescription.getContactListGroup());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, contactListGroupDescription.getLanguage());
            
            contactListGroupDescriptionTransfer = new ContactListGroupDescriptionTransfer(languageTransfer, contactListGroupTransfer, contactListGroupDescription.getDescription());
            put(contactListGroupDescription, contactListGroupDescriptionTransfer);
        }
        
        return contactListGroupDescriptionTransfer;
    }
    
}
