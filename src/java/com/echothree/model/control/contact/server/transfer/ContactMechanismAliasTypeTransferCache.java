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

import com.echothree.model.control.contact.common.transfer.ContactMechanismAliasTypeTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContactMechanismAliasTypeTransferCache
        extends BaseContactTransferCache<ContactMechanismAliasType, ContactMechanismAliasTypeTransfer> {
    
    /** Creates a new instance of ContactMechanismAliasTypeTransferCache */
    public ContactMechanismAliasTypeTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
    }
    
    public ContactMechanismAliasTypeTransfer getContactMechanismAliasTypeTransfer(ContactMechanismAliasType contactMechanismAliasType) {
        var contactMechanismAliasTypeTransfer = get(contactMechanismAliasType);
        
        if(contactMechanismAliasTypeTransfer == null) {
            var contactMechanismAliasTypeDetail = contactMechanismAliasType.getLastDetail();
            var contactMechanismAliasTypeName = contactMechanismAliasTypeDetail.getContactMechanismAliasTypeName();
            var validationPattern = contactMechanismAliasTypeDetail.getValidationPattern();
            var isDefault = contactMechanismAliasTypeDetail.getIsDefault();
            var sortOrder = contactMechanismAliasTypeDetail.getSortOrder();
            var description = contactControl.getBestContactMechanismAliasTypeDescription(contactMechanismAliasType, getLanguage());
            
            contactMechanismAliasTypeTransfer = new ContactMechanismAliasTypeTransfer(contactMechanismAliasTypeName, validationPattern, isDefault, sortOrder,
                    description);
            put(contactMechanismAliasType, contactMechanismAliasTypeTransfer);
        }
        
        return contactMechanismAliasTypeTransfer;
    }
    
}
