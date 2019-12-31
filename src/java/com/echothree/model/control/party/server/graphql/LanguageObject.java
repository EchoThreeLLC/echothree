// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("language object")
@GraphQLName("Language")
public class LanguageObject
        extends BaseEntityInstanceObject {
    
    private final Language language; // Always Present
    
    public LanguageObject(Language language) {
        super(language.getPrimaryKey());
        
        this.language = language;
    }
    
    @GraphQLField
    @GraphQLDescription("language iso name")
    @GraphQLNonNull
    public String getLanguageIsoName() {
        return language.getLanguageIsoName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public Boolean getIsDefault() {
        return language.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public Integer getSortOrder() {
        return language.getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return partyControl.getBestLanguageDescription(language, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }

}
