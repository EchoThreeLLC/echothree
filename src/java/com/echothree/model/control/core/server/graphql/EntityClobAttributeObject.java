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

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.graphql.server.graphql.HistoryInterface;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.data.core.common.EntityClobAttributeConstants;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;

@GraphQLDescription("entity clob attribute object")
@GraphQLName("EntityClobAttribute")
public class EntityClobAttributeObject
        extends BaseEntityClobAttributeObject
        implements AttributeInterface, HistoryInterface<EntityClobAttributeHistoryObject> {

    public EntityClobAttributeObject(EntityClobAttribute entityClobAttribute) {
        super(entityClobAttribute);
    }

    @Override
    @GraphQLField
    @GraphQLDescription("history")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityClobAttributeHistoryObject> getHistory(final DataFetchingEnvironment env) {
        if(true) { // TODO: Security Check
            var coreControl = Session.getModelController(CoreControl.class);
            var entityAttribute = entityClobAttribute.getEntityAttribute();
            var entityInstance = entityClobAttribute.getEntityInstance();
            var totalCount = coreControl.countEntityClobAttributeHistory(entityAttribute, entityInstance);

            try(var objectLimiter = new ObjectLimiter(env, EntityClobAttributeConstants.COMPONENT_VENDOR_NAME, EntityClobAttributeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = coreControl.getEntityClobAttributeHistory(entityAttribute, entityInstance);
                var entityObjects = new ArrayList<EntityClobAttributeHistoryObject>(entities.size());

                for(var entity : entities) {
                    var entityObject = new EntityClobAttributeHistoryObject(entity);

                    entityObjects.add(entityObject);
                }

                return new CountedObjects<>(objectLimiter, entityObjects);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
