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

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("event type object")
@GraphQLName("EventType")
public class EventTypeObject
        extends BaseGraphQl {
    
    private final EventType eventType; // Always Present
    
    public EventTypeObject(EventType eventType) {
        this.eventType = eventType;
    }
    
    @GraphQLField
    @GraphQLDescription("event type name")
    @GraphQLNonNull
    public String getEventTypeName() {
        return eventType.getEventTypeName();
    }

    @GraphQLField
    @GraphQLDescription("update created time")
    @GraphQLNonNull
    public boolean getUpdateCreatedTime() {
        return eventType.getUpdateCreatedTime();
    }

    @GraphQLField
    @GraphQLDescription("update modified time")
    @GraphQLNonNull
    public boolean getUpdateModifiedTime() {
        return eventType.getUpdateModifiedTime();
    }

    @GraphQLField
    @GraphQLDescription("update deleted time")
    @GraphQLNonNull
    public boolean getUpdateDeletedTime() {
        return eventType.getUpdateDeletedTime();
    }

    @GraphQLField
    @GraphQLDescription("update visited time")
    @GraphQLNonNull
    public boolean getUpdateVisitedTime() {
        return eventType.getUpdateVisitedTime();
    }

    @GraphQLField
    @GraphQLDescription("queue to subscribers")
    @GraphQLNonNull
    public boolean getQueueToSubscribers() {
        return eventType.getQueueToSubscribers();
    }

    @GraphQLField
    @GraphQLDescription("keep history")
    @GraphQLNonNull
    public boolean getKeepHistory() {
        return eventType.getKeepHistory();
    }

    @GraphQLField
    @GraphQLDescription("maximum history")
    public Integer getMaximumHistory() {
        return eventType.getMaximumHistory();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var coreControl = Session.getModelController(CoreControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return coreControl.getBestEventTypeDescription(eventType, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }
    
}
