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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity list item attribute object")
@GraphQLName("EntityListItemAttribute")
public class EntityListItemAttributeObject
        implements BaseGraphQl, AttributeInterface {
    
    private final EntityListItemAttribute entityListItemAttribute; // Always Present
    
    public EntityListItemAttributeObject(EntityListItemAttribute entityListItemAttribute) {
        this.entityListItemAttribute = entityListItemAttribute;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityListItemAttribute.getEntityAttribute(), entityListItemAttribute.getEntityInstance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityListItemAttribute.getEntityInstance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity list item")
    public EntityListItemObject getEntityListItem(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityListItemAccess(env) ? new EntityListItemObject(entityListItemAttribute.getEntityListItem()) : null;
    }
    
}
