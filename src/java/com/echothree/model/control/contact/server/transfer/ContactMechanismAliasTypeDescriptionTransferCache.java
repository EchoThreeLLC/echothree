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

import com.echothree.model.control.contact.common.transfer.ContactMechanismAliasTypeDescriptionTransfer;
import com.echothree.model.control.contact.common.transfer.ContactMechanismAliasTypeTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContactMechanismAliasTypeDescriptionTransferCache
        extends BaseContactDescriptionTransferCache<ContactMechanismAliasTypeDescription, ContactMechanismAliasTypeDescriptionTransfer> {
    
    /** Creates a new instance of ContactMechanismAliasTypeDescriptionTransferCache */
    public ContactMechanismAliasTypeDescriptionTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
    }
    
    public ContactMechanismAliasTypeDescriptionTransfer getContactMechanismAliasTypeDescriptionTransfer(ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription) {
        ContactMechanismAliasTypeDescriptionTransfer contactMechanismAliasTypeDescriptionTransfer = get(contactMechanismAliasTypeDescription);
        
        if(contactMechanismAliasTypeDescriptionTransfer == null) {
            ContactMechanismAliasTypeTransfer contactMechanismAliasTypeTransfer = contactControl.getContactMechanismAliasTypeTransfer(userVisit, contactMechanismAliasTypeDescription.getContactMechanismAliasType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, contactMechanismAliasTypeDescription.getLanguage());
            
            contactMechanismAliasTypeDescriptionTransfer = new ContactMechanismAliasTypeDescriptionTransfer(languageTransfer, contactMechanismAliasTypeTransfer, contactMechanismAliasTypeDescription.getDescription());
            put(contactMechanismAliasTypeDescription, contactMechanismAliasTypeDescriptionTransfer);
        }
        
        return contactMechanismAliasTypeDescriptionTransfer;
    }
    
}
