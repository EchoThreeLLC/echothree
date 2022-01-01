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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowSecurityUtils;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;
import graphql.schema.DataFetchingEnvironment;

public abstract class BaseObject
        extends BaseGraphQl {

    protected final BasePK basePrimaryKey;

    protected BaseObject(BasePK basePrimaryKey) {
        this.basePrimaryKey = basePrimaryKey;
    }

    protected WorkflowEntityStatusObject getWorkflowEntityStatusObject(final DataFetchingEnvironment env,
            final String workflowName) {
        WorkflowEntityStatusObject result = null;

        if(WorkflowSecurityUtils.getInstance().getHasWorkflowEntityStatusesAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var entityInstance = coreControl.getEntityInstanceByBasePK(basePrimaryKey);
            var workflow = WorkflowLogic.getInstance().getWorkflowByName(null, workflowName);
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstance(workflow, entityInstance);

            result = workflowEntityStatus == null ? null : new WorkflowEntityStatusObject(workflowEntityStatus);
        }

        return result;
    }

}
