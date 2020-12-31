// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.contactlist.common.transfer.ContactListTypeDescriptionTransfer;
import com.echothree.model.control.contactlist.common.transfer.ContactListTypeTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.contactlist.server.entity.ContactListTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContactListTypeDescriptionTransferCache
        extends BaseContactListDescriptionTransferCache<ContactListTypeDescription, ContactListTypeDescriptionTransfer> {
    
    /** Creates a new instance of ContactListTypeDescriptionTransferCache */
    public ContactListTypeDescriptionTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
    }
    
    public ContactListTypeDescriptionTransfer getContactListTypeDescriptionTransfer(ContactListTypeDescription contactListTypeDescription) {
        ContactListTypeDescriptionTransfer contactListTypeDescriptionTransfer = get(contactListTypeDescription);
        
        if(contactListTypeDescriptionTransfer == null) {
            ContactListTypeTransfer contactListTypeTransfer = contactListControl.getContactListTypeTransfer(userVisit, contactListTypeDescription.getContactListType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, contactListTypeDescription.getLanguage());
            
            contactListTypeDescriptionTransfer = new ContactListTypeDescriptionTransfer(languageTransfer, contactListTypeTransfer, contactListTypeDescription.getDescription());
            put(contactListTypeDescription, contactListTypeDescriptionTransfer);
        }
        
        return contactListTypeDescriptionTransfer;
    }
    
}
