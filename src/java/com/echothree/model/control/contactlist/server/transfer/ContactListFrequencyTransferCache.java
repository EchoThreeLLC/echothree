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

import com.echothree.model.control.contactlist.common.transfer.ContactListFrequencyTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContactListFrequencyTransferCache
        extends BaseContactListTransferCache<ContactListFrequency, ContactListFrequencyTransfer> {
    
    /** Creates a new instance of ContactListFrequencyTransferCache */
    public ContactListFrequencyTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ContactListFrequencyTransfer getContactListFrequencyTransfer(ContactListFrequency contactListFrequency) {
        var contactListFrequencyTransfer = get(contactListFrequency);
        
        if(contactListFrequencyTransfer == null) {
            var contactListFrequencyDetail = contactListFrequency.getLastDetail();
            var contactListFrequencyName = contactListFrequencyDetail.getContactListFrequencyName();
            var isDefault = contactListFrequencyDetail.getIsDefault();
            var sortOrder = contactListFrequencyDetail.getSortOrder();
            var description = contactListControl.getBestContactListFrequencyDescription(contactListFrequency, getLanguage());
            
            contactListFrequencyTransfer = new ContactListFrequencyTransfer(contactListFrequencyName, isDefault, sortOrder, description);
            put(contactListFrequency, contactListFrequencyTransfer);
        }
        
        return contactListFrequencyTransfer;
    }
    
}
