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

import com.echothree.model.control.chain.common.transfer.ChainTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.contactlist.common.transfer.ContactListTypeTransfer;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.contactlist.server.entity.ContactListType;
import com.echothree.model.data.contactlist.server.entity.ContactListTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContactListTypeTransferCache
        extends BaseContactListTransferCache<ContactListType, ContactListTypeTransfer> {
    
    ChainControl chainControl = Session.getModelController(ChainControl.class);

    /** Creates a new instance of ContactListTypeTransferCache */
    public ContactListTypeTransferCache(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit, contactListControl);

        setIncludeEntityInstance(true);
    }
    
    public ContactListTypeTransfer getContactListTypeTransfer(ContactListType contactListType) {
        ContactListTypeTransfer contactListTypeTransfer = get(contactListType);
        
        if(contactListTypeTransfer == null) {
            ContactListTypeDetail contactListTypeDetail = contactListType.getLastDetail();
            String contactListTypeName = contactListTypeDetail.getContactListTypeName();
            Chain confirmationRequestChain = contactListTypeDetail.getConfirmationRequestChain();
            ChainTransfer confirmationRequestChainTransfer = confirmationRequestChain == null ? null : chainControl.getChainTransfer(userVisit, confirmationRequestChain);
            Chain subscribeChain = contactListTypeDetail.getSubscribeChain();
            ChainTransfer subscribeChainTransfer = subscribeChain == null ? null : chainControl.getChainTransfer(userVisit, subscribeChain);
            Chain unsubscribeChain = contactListTypeDetail.getUnsubscribeChain();
            ChainTransfer unsubscribeChainTransfer = unsubscribeChain == null ? null : chainControl.getChainTransfer(userVisit, unsubscribeChain);
            Boolean usedForSolicitation = contactListTypeDetail.getUsedForSolicitation();
            Boolean isDefault = contactListTypeDetail.getIsDefault();
            Integer sortOrder = contactListTypeDetail.getSortOrder();
            String description = contactListControl.getBestContactListTypeDescription(contactListType, getLanguage());
            
            contactListTypeTransfer = new ContactListTypeTransfer(contactListTypeName, confirmationRequestChainTransfer, subscribeChainTransfer,
                    unsubscribeChainTransfer, usedForSolicitation, isDefault, sortOrder, description);
            put(contactListType, contactListTypeTransfer);
        }
        
        return contactListTypeTransfer;
    }
    
}
