// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDetail;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("workflow entity status object")
@GraphQLName("WorkflowEntityStatus")
public class WorkflowEntityStatusObject {

    private final WorkflowEntityStatus workflowEntityStatus; // Always Present

    public WorkflowEntityStatusObject(WorkflowEntityStatus workflowEntityStatus) {
        this.workflowEntityStatus = workflowEntityStatus;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getInstance().getHasEntityInstanceAccess(env)) {
            return new EntityInstanceObject(workflowEntityStatus.getEntityInstance());
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("workflow step")
    public WorkflowStepObject getWorkflowStep(final DataFetchingEnvironment env) {
        if(WorkflowSecurityUtils.getInstance().getHasWorkflowStepAccess(env)) {
            return new WorkflowStepObject(workflowEntityStatus.getWorkflowStep());
        } else {
            return null;
        }
    }

    // TODO: WorkEffortScope

    @GraphQLField
    @GraphQLDescription("unformatted from time")
    @GraphQLNonNull
    public Long getUnformattedFromTime() {
        return workflowEntityStatus.getFromTime();
    }

    @GraphQLField
    @GraphQLDescription("from time")
    @GraphQLNonNull
    public String getFromTime(final DataFetchingEnvironment env) {
        GraphQlContext context = env.getContext();

        return DateUtils.getInstance().formatTypicalDateTime(context.getUserVisit(), workflowEntityStatus.getFromTime());
    }

    @GraphQLField
    @GraphQLDescription("unformatted thru time")
    @GraphQLNonNull
    public Long getUnformattedThruTime() {
        return workflowEntityStatus.getThruTime();
    }

    @GraphQLField
    @GraphQLDescription("thru time")
    @GraphQLNonNull
    public String getThruTime(final DataFetchingEnvironment env) {
        GraphQlContext context = env.getContext();

        return DateUtils.getInstance().formatTypicalDateTime(context.getUserVisit(), workflowEntityStatus.getThruTime());
    }

}
