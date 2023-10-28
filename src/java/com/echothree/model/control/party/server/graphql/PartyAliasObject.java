// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.data.party.server.entity.PartyAlias;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("party alias object")
@GraphQLName("PartyAlias")
public class PartyAliasObject
        implements BaseGraphQl {
    
    private final PartyAlias partyAlias; // Always Present
    
    public PartyAliasObject(PartyAlias partyAlias) {
        this.partyAlias = partyAlias;
    }

    @GraphQLField
    @GraphQLDescription("party")
    public PartyObject getParty(final DataFetchingEnvironment env) {
        var party = partyAlias.getParty();

        return PartySecurityUtils.getInstance().getHasPartyAccess(env, party) ? new PartyObject(party) : null;
    }

    @GraphQLField
    @GraphQLDescription("party alias type")
    public PartyAliasTypeObject getPartyAliasType(final DataFetchingEnvironment env) {
        return PartySecurityUtils.getInstance().getHasPartyAliasTypeAccess(env) ? new PartyAliasTypeObject(partyAlias.getPartyAliasType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("alias")
    @GraphQLNonNull
    public String getAlias() {
        return partyAlias.getAlias();
    }

}
