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

package com.echothree.model.control.party.server.graphql;

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.PartyRelationshipType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("party relationship type object")
@GraphQLName("PartyRelationshipType")
public class PartyRelationshipTypeObject
        implements BaseGraphQl {

    private final PartyRelationshipType partyRelationshipType; // Always Present
    
    public PartyRelationshipTypeObject(PartyRelationshipType partyRelationshipType) {
        this.partyRelationshipType = partyRelationshipType;
    }
    
    @GraphQLField
    @GraphQLDescription("party relationship type name")
    @GraphQLNonNull
    public String getPartyRelationshipTypeName() {
        return partyRelationshipType.getPartyRelationshipTypeName();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return partyControl.getBestPartyRelationshipTypeDescription(partyRelationshipType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
