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
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanismDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("partyContactMechanism object")
@GraphQLName("PartyContactMechanism")
public class PartyContactMechanismObject
        extends BaseEntityInstanceObject {

    private final PartyContactMechanism partyContactMechanism; // Always Present

    public PartyContactMechanismObject(PartyContactMechanism partyContactMechanism) {
        super(partyContactMechanism.getPrimaryKey());
        
        this.partyContactMechanism = partyContactMechanism;
    }

    private PartyContactMechanismDetail partyContactMechanismDetail; // Optional, use getPartyContactMechanismDetail()
    
    private PartyContactMechanismDetail getPartyContactMechanismDetail() {
        if(partyContactMechanismDetail == null) {
            partyContactMechanismDetail = partyContactMechanism.getLastDetail();
        }
        
        return partyContactMechanismDetail;
    }

    @GraphQLField
    @GraphQLDescription("party")
    @GraphQLNonNull
    public PartyObject getParty(final DataFetchingEnvironment env) {
        var party = partyContactMechanismDetail.getParty();

        return PartySecurityUtils.getHasPartyAccess(env, party) ? new PartyObject(party) : null;
    }

    @GraphQLField
    @GraphQLDescription("contact mechanism")
    @GraphQLNonNull
    public ContactMechanismObject getContactMechanism() {
        return new ContactMechanismObject(getPartyContactMechanismDetail().getContactMechanism());
    }

    @GraphQLField
    @GraphQLDescription("description")
    public String getDescription(final DataFetchingEnvironment env) {
        return getPartyContactMechanismDetail().getDescription();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getPartyContactMechanismDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getPartyContactMechanismDetail().getSortOrder();
    }
    
}
