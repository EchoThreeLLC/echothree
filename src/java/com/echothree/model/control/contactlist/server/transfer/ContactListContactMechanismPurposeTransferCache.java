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

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.common.transfer.ContactListContactMechanismPurposeTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactListContactMechanismPurposeTransferCache
        extends BaseContactListTransferCache<ContactListContactMechanismPurpose, ContactListContactMechanismPurposeTransfer> {
    
    final ContactControl contactControl = Session.getModelController(ContactControl.class);
    
    /** Creates a new instance of ContactListContactMechanismPurposeTransferCache */
    public ContactListContactMechanismPurposeTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);
    }
    
    public ContactListContactMechanismPurposeTransfer getContactListContactMechanismPurposeTransfer(final ContactListContactMechanismPurpose contactListContactMechanismPurpose) {
        var contactListContactMechanismPurposeTransfer = get(contactListContactMechanismPurpose);
        
        if(contactListContactMechanismPurposeTransfer == null) {
            var contactListContactMechanismPurposeDetail = contactListContactMechanismPurpose.getLastDetail();
            var contactList = contactListControl.getContactListTransfer(userVisit, contactListContactMechanismPurposeDetail.getContactList());
            var contactMechanismPurpose = contactControl.getContactMechanismPurposeTransfer(userVisit, contactListContactMechanismPurposeDetail.getContactMechanismPurpose());
            var isDefault = contactListContactMechanismPurposeDetail.getIsDefault();
            var sortOrder = contactListContactMechanismPurposeDetail.getSortOrder();
            
            contactListContactMechanismPurposeTransfer = new ContactListContactMechanismPurposeTransfer(contactList, contactMechanismPurpose, isDefault, sortOrder);
            put(contactListContactMechanismPurpose, contactListContactMechanismPurposeTransfer);
        }
        
        return contactListContactMechanismPurposeTransfer;
    }
    
}
