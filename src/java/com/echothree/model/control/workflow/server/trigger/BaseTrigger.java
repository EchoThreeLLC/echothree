// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.workflow.server.trigger;

import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;

public abstract class BaseTrigger {

    protected EntityInstance getEntityInstance(final WorkflowEntityStatus workflowEntityStatus) {
        return workflowEntityStatus.getEntityInstance();
    }
    
    protected String getWorkflowStepName(final WorkflowEntityStatus workflowEntityStatus) {
        return workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
    }
    
}
