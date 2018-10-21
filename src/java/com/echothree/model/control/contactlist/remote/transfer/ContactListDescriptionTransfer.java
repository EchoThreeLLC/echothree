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

package com.echothree.model.control.contactlist.remote.transfer;

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class ContactListDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private ContactListTransfer contactList;
    private String description;
    
    /** Creates a new instance of ContactListDescriptionTransfer */
    public ContactListDescriptionTransfer(LanguageTransfer language, ContactListTransfer contactList, String description) {
        this.language = language;
        this.contactList = contactList;
        this.description = description;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public ContactListTransfer getContactList() {
        return contactList;
    }
    
    public void setContactList(ContactListTransfer contactList) {
        this.contactList = contactList;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
