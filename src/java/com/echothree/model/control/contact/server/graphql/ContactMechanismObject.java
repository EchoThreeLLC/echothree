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

package com.echothree.model.control.contact.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.ContactMechanismDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("contact mechanism object")
@GraphQLName("ContactMechanism")
public class ContactMechanismObject
        extends BaseEntityInstanceObject {
    
    private final ContactMechanism contactMechanism; // Always Present
    
    public ContactMechanismObject(ContactMechanism contactMechanism) {
        super(contactMechanism.getPrimaryKey());
        
        this.contactMechanism = contactMechanism;
    }

    private ContactMechanismDetail contactMechanismDetail; // Optional, use getContactMechanismDetail()
    
    private ContactMechanismDetail getContactMechanismDetail() {
        if(contactMechanismDetail == null) {
            contactMechanismDetail = contactMechanism.getLastDetail();
        }
        
        return contactMechanismDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("contact mechanism name")
    @GraphQLNonNull
    public String getContactMechanismName() {
        return getContactMechanismDetail().getContactMechanismName();
    }

    @GraphQLField
    @GraphQLDescription("contact mechanism type")
    @GraphQLNonNull
    public ContactMechanismTypeObject getContactMechanismType() {
        return new ContactMechanismTypeObject(getContactMechanismDetail().getContactMechanismType());
    }

    @GraphQLField
    @GraphQLDescription("allow solicitation")
    @GraphQLNonNull
    public boolean getAllowSolicitation() {
        return getContactMechanismDetail().getAllowSolicitation();
    }
    
}
