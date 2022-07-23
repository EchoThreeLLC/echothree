// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("entity name object")
@GraphQLName("EntityName")
public class EntityNameObject
        extends BaseGraphQl {

    private final String key;
    private final String value;

    public EntityNameObject(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    @GraphQLField
    @GraphQLDescription("key")
    @GraphQLNonNull
    public String getKey() {
        return key;
    }

    @GraphQLField
    @GraphQLDescription("value")
    @GraphQLNonNull
    public String getValue() {
        return value;
    }

}
