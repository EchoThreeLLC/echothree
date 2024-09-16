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

import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ContactListTransferCaches
        extends BaseTransferCaches {
    
    protected ContactListControl contactListControl;
    
    protected ContactListTypeDescriptionTransferCache contactListTypeDescriptionTransferCache;
    protected ContactListTypeTransferCache contactListTypeTransferCache;
    protected ContactListGroupDescriptionTransferCache contactListGroupDescriptionTransferCache;
    protected ContactListGroupTransferCache contactListGroupTransferCache;
    protected ContactListFrequencyDescriptionTransferCache contactListFrequencyDescriptionTransferCache;
    protected ContactListFrequencyTransferCache contactListFrequencyTransferCache;
    protected ContactListDescriptionTransferCache contactListDescriptionTransferCache;
    protected ContactListTransferCache contactListTransferCache;
    protected PartyContactListTransferCache partyContactListTransferCache;
    protected PartyTypeContactListTransferCache partyTypeContactListTransferCache;
    protected PartyTypeContactListGroupTransferCache partyTypeContactListGroupTransferCache;
    protected CustomerTypeContactListTransferCache customerTypeContactListTransferCache;
    protected CustomerTypeContactListGroupTransferCache customerTypeContactListGroupTransferCache;
    protected ContactListContactMechanismPurposeTransferCache contactListContactMechanismPurposeTransferCache;
    
    /** Creates a new instance of ContactListTransferCaches */
    public ContactListTransferCaches(UserVisit userVisit, ContactListControl contactListControl) {
        super(userVisit);
        
        this.contactListControl = contactListControl;
    }
    
    public ContactListTypeDescriptionTransferCache getContactListTypeDescriptionTransferCache() {
        if(contactListTypeDescriptionTransferCache == null)
            contactListTypeDescriptionTransferCache = new ContactListTypeDescriptionTransferCache(userVisit, contactListControl);
        
        return contactListTypeDescriptionTransferCache;
    }
    
    public ContactListTypeTransferCache getContactListTypeTransferCache() {
        if(contactListTypeTransferCache == null)
            contactListTypeTransferCache = new ContactListTypeTransferCache(userVisit, contactListControl);
        
        return contactListTypeTransferCache;
    }
    
    public ContactListGroupDescriptionTransferCache getContactListGroupDescriptionTransferCache() {
        if(contactListGroupDescriptionTransferCache == null)
            contactListGroupDescriptionTransferCache = new ContactListGroupDescriptionTransferCache(userVisit, contactListControl);

        return contactListGroupDescriptionTransferCache;
    }

    public ContactListGroupTransferCache getContactListGroupTransferCache() {
        if(contactListGroupTransferCache == null)
            contactListGroupTransferCache = new ContactListGroupTransferCache(userVisit, contactListControl);

        return contactListGroupTransferCache;
    }

    public ContactListFrequencyDescriptionTransferCache getContactListFrequencyDescriptionTransferCache() {
        if(contactListFrequencyDescriptionTransferCache == null)
            contactListFrequencyDescriptionTransferCache = new ContactListFrequencyDescriptionTransferCache(userVisit, contactListControl);

        return contactListFrequencyDescriptionTransferCache;
    }

    public ContactListFrequencyTransferCache getContactListFrequencyTransferCache() {
        if(contactListFrequencyTransferCache == null)
            contactListFrequencyTransferCache = new ContactListFrequencyTransferCache(userVisit, contactListControl);

        return contactListFrequencyTransferCache;
    }

    public ContactListDescriptionTransferCache getContactListDescriptionTransferCache() {
        if(contactListDescriptionTransferCache == null)
            contactListDescriptionTransferCache = new ContactListDescriptionTransferCache(userVisit, contactListControl);
        
        return contactListDescriptionTransferCache;
    }
    
    public ContactListTransferCache getContactListTransferCache() {
        if(contactListTransferCache == null)
            contactListTransferCache = new ContactListTransferCache(userVisit, contactListControl);
        
        return contactListTransferCache;
    }
    
    public PartyContactListTransferCache getPartyContactListTransferCache() {
        if(partyContactListTransferCache == null)
            partyContactListTransferCache = new PartyContactListTransferCache(userVisit, contactListControl);
        
        return partyContactListTransferCache;
    }
    
    public PartyTypeContactListTransferCache getPartyTypeContactListTransferCache() {
        if(partyTypeContactListTransferCache == null)
            partyTypeContactListTransferCache = new PartyTypeContactListTransferCache(userVisit, contactListControl);

        return partyTypeContactListTransferCache;
    }

    public PartyTypeContactListGroupTransferCache getPartyTypeContactListGroupTransferCache() {
        if(partyTypeContactListGroupTransferCache == null)
            partyTypeContactListGroupTransferCache = new PartyTypeContactListGroupTransferCache(userVisit, contactListControl);

        return partyTypeContactListGroupTransferCache;
    }

    public CustomerTypeContactListTransferCache getCustomerTypeContactListTransferCache() {
        if(customerTypeContactListTransferCache == null)
            customerTypeContactListTransferCache = new CustomerTypeContactListTransferCache(userVisit, contactListControl);

        return customerTypeContactListTransferCache;
    }

    public CustomerTypeContactListGroupTransferCache getCustomerTypeContactListGroupTransferCache() {
        if(customerTypeContactListGroupTransferCache == null)
            customerTypeContactListGroupTransferCache = new CustomerTypeContactListGroupTransferCache(userVisit, contactListControl);

        return customerTypeContactListGroupTransferCache;
    }

    public ContactListContactMechanismPurposeTransferCache getContactListContactMechanismPurposeTransferCache() {
        if(contactListContactMechanismPurposeTransferCache == null)
            contactListContactMechanismPurposeTransferCache = new ContactListContactMechanismPurposeTransferCache(userVisit, contactListControl);
        
        return contactListContactMechanismPurposeTransferCache;
    }
    
}
