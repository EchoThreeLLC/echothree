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

import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ContactTransferCaches
        extends BaseTransferCaches {
    
    protected ContactControl contactControl;
    
    protected PostalAddressFormatTransferCache postalAddressFormatTransferCache;
    protected PostalAddressElementTypeTransferCache postalAddressElementTypeTransferCache;
    protected ContactMechanismAliasTypeTransferCache contactMechanismAliasTypeTransferCache;
    protected ContactMechanismAliasTypeDescriptionTransferCache contactMechanismAliasTypeDescriptionTransferCache;
    protected ContactMechanismPurposeTransferCache contactMechanismPurposeTransferCache;
    protected ContactMechanismTypeTransferCache contactMechanismTypeTransferCache;
    protected ContactMechanismTransferCache contactMechanismTransferCache;
    protected ContactEmailAddressTransferCache contactEmailAddressTransferCache;
    protected ContactPostalAddressTransferCache contactPostalAddressTransferCache;
    protected ContactTelephoneTransferCache contactTelephoneTransferCache;
    protected PostalAddressFormatDescriptionTransferCache postalAddressFormatDescriptionTransferCache;
    protected PostalAddressLineTransferCache postalAddressLineTransferCache;
    protected PostalAddressLineElementTransferCache postalAddressLineElementTransferCache;
    protected ContactWebAddressTransferCache contactWebAddressTransferCache;
    protected ContactMechanismAliasTransferCache contactMechanismAliasTransferCache;
    protected PartyContactMechanismAliasTransferCache partyContactMechanismAliasTransferCache;
    protected PartyContactMechanismPurposeTransferCache partyContactMechanismPurposeTransferCache;
    protected PartyContactMechanismRelationshipTransferCache partyContactMechanismRelationshipTransferCache;
    protected PartyContactMechanismTransferCache partyContactMechanismTransferCache;
    protected ContactInet4AddressTransferCache contactInet4AddressTransferCache;
    
    /** Creates a new instance of ContactTransferCaches */
    public ContactTransferCaches(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit);
        
        this.contactControl = contactControl;
    }
    
    public PostalAddressFormatTransferCache getPostalAddressFormatTransferCache() {
        if(postalAddressFormatTransferCache == null)
            postalAddressFormatTransferCache = new PostalAddressFormatTransferCache(userVisit, contactControl);
        
        return postalAddressFormatTransferCache;
    }
    
    public PostalAddressElementTypeTransferCache getPostalAddressElementTypeTransferCache() {
        if(postalAddressElementTypeTransferCache == null)
            postalAddressElementTypeTransferCache = new PostalAddressElementTypeTransferCache(userVisit, contactControl);
        
        return postalAddressElementTypeTransferCache;
    }
    
    public ContactMechanismAliasTypeTransferCache getContactMechanismAliasTypeTransferCache() {
        if(contactMechanismAliasTypeTransferCache == null)
            contactMechanismAliasTypeTransferCache = new ContactMechanismAliasTypeTransferCache(userVisit, contactControl);

        return contactMechanismAliasTypeTransferCache;
    }

    public ContactMechanismAliasTypeDescriptionTransferCache getContactMechanismAliasTypeDescriptionTransferCache() {
        if(contactMechanismAliasTypeDescriptionTransferCache == null)
            contactMechanismAliasTypeDescriptionTransferCache = new ContactMechanismAliasTypeDescriptionTransferCache(userVisit, contactControl);

        return contactMechanismAliasTypeDescriptionTransferCache;
    }

    public ContactMechanismPurposeTransferCache getContactMechanismPurposeTransferCache() {
        if(contactMechanismPurposeTransferCache == null)
            contactMechanismPurposeTransferCache = new ContactMechanismPurposeTransferCache(userVisit, contactControl);
        
        return contactMechanismPurposeTransferCache;
    }
    
    public ContactMechanismTypeTransferCache getContactMechanismTypeTransferCache() {
        if(contactMechanismTypeTransferCache == null)
            contactMechanismTypeTransferCache = new ContactMechanismTypeTransferCache(userVisit, contactControl);
        
        return contactMechanismTypeTransferCache;
    }
    
    public ContactMechanismTransferCache getContactMechanismTransferCache() {
        if(contactMechanismTransferCache == null)
            contactMechanismTransferCache = new ContactMechanismTransferCache(userVisit, contactControl);
        
        return contactMechanismTransferCache;
    }
    
    public ContactEmailAddressTransferCache getContactEmailAddressTransferCache() {
        if(contactEmailAddressTransferCache == null)
            contactEmailAddressTransferCache = new ContactEmailAddressTransferCache(userVisit, contactControl);
        
        return contactEmailAddressTransferCache;
    }
    
    public ContactPostalAddressTransferCache getContactPostalAddressTransferCache() {
        if(contactPostalAddressTransferCache == null)
            contactPostalAddressTransferCache = new ContactPostalAddressTransferCache(userVisit, contactControl);
        
        return contactPostalAddressTransferCache;
    }
    
    public ContactTelephoneTransferCache getContactTelephoneTransferCache() {
        if(contactTelephoneTransferCache == null)
            contactTelephoneTransferCache = new ContactTelephoneTransferCache(userVisit, contactControl);
        
        return contactTelephoneTransferCache;
    }
    
    public PostalAddressFormatDescriptionTransferCache getPostalAddressFormatDescriptionTransferCache() {
        if(postalAddressFormatDescriptionTransferCache == null)
            postalAddressFormatDescriptionTransferCache = new PostalAddressFormatDescriptionTransferCache(userVisit, contactControl);
        
        return postalAddressFormatDescriptionTransferCache;
    }
    
    public PostalAddressLineTransferCache getPostalAddressLineTransferCache() {
        if(postalAddressLineTransferCache == null)
            postalAddressLineTransferCache = new PostalAddressLineTransferCache(userVisit, contactControl);
        
        return postalAddressLineTransferCache;
    }
    
    public PostalAddressLineElementTransferCache getPostalAddressLineElementTransferCache() {
        if(postalAddressLineElementTransferCache == null)
            postalAddressLineElementTransferCache = new PostalAddressLineElementTransferCache(userVisit, contactControl);
        
        return postalAddressLineElementTransferCache;
    }
    
    public ContactWebAddressTransferCache getContactWebAddressTransferCache() {
        if(contactWebAddressTransferCache == null)
            contactWebAddressTransferCache = new ContactWebAddressTransferCache(userVisit, contactControl);
        
        return contactWebAddressTransferCache;
    }
    
    public ContactMechanismAliasTransferCache getContactMechanismAliasTransferCache() {
        if(contactMechanismAliasTransferCache == null)
            contactMechanismAliasTransferCache = new ContactMechanismAliasTransferCache(userVisit, contactControl);
        
        return contactMechanismAliasTransferCache;
    }
    
    public PartyContactMechanismAliasTransferCache getPartyContactMechanismAliasTransferCache() {
        if(partyContactMechanismAliasTransferCache == null)
            partyContactMechanismAliasTransferCache = new PartyContactMechanismAliasTransferCache(userVisit, contactControl);
        
        return partyContactMechanismAliasTransferCache;
    }
    
    public PartyContactMechanismPurposeTransferCache getPartyContactMechanismPurposeTransferCache() {
        if(partyContactMechanismPurposeTransferCache == null)
            partyContactMechanismPurposeTransferCache = new PartyContactMechanismPurposeTransferCache(userVisit, contactControl);
        
        return partyContactMechanismPurposeTransferCache;
    }
    
    public PartyContactMechanismRelationshipTransferCache getPartyContactMechanismRelationshipTransferCache() {
        if(partyContactMechanismRelationshipTransferCache == null)
            partyContactMechanismRelationshipTransferCache = new PartyContactMechanismRelationshipTransferCache(userVisit, contactControl);
        
        return partyContactMechanismRelationshipTransferCache;
    }
    
    public PartyContactMechanismTransferCache getPartyContactMechanismTransferCache() {
        if(partyContactMechanismTransferCache == null)
            partyContactMechanismTransferCache = new PartyContactMechanismTransferCache(userVisit, contactControl);
        
        return partyContactMechanismTransferCache;
    }
    
    public ContactInet4AddressTransferCache getContactInet4AddressTransferCache() {
        if(contactInet4AddressTransferCache == null)
            contactInet4AddressTransferCache = new ContactInet4AddressTransferCache(userVisit, contactControl);
        
        return contactInet4AddressTransferCache;
    }
    
}
