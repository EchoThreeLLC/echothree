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

package com.echothree.model.control.workflow.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.party.server.graphql.PartyTypeObject;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow entrance party type object")
@GraphQLName("WorkflowEntrancePartyType")
public class WorkflowEntrancePartyTypeObject
        extends BaseObject {
    
    private final WorkflowEntrancePartyType workflowEntrancePartyType; // Always Present
    
    public WorkflowEntrancePartyTypeObject(WorkflowEntrancePartyType workflowEntrancePartyType) {
        super(workflowEntrancePartyType.getPrimaryKey());
        
        this.workflowEntrancePartyType = workflowEntrancePartyType;
    }

    @GraphQLField
    @GraphQLDescription("workflow entrance")
    public WorkflowEntranceObject getWorkflowEntrance(final DataFetchingEnvironment env) {
        return WorkflowSecurityUtils.getInstance().getHasWorkflowEntranceAccess(env) ? new WorkflowEntranceObject(workflowEntrancePartyType.getWorkflowEntrance()) : null;
    }

    @GraphQLField
    @GraphQLDescription("party type")
    @GraphQLNonNull
    public PartyTypeObject getPartyType() {
        return new PartyTypeObject(workflowEntrancePartyType.getPartyType());
    }

}
