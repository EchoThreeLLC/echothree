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

package com.echothree.model.control.workflow.server.transfer;

import com.echothree.model.control.workflow.common.transfer.WorkflowStepTypeTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStepType;

public class WorkflowStepTypeTransferCache
        extends BaseWorkflowTransferCache<WorkflowStepType, WorkflowStepTypeTransfer> {
    
    /** Creates a new instance of WorkflowStepTypeTransferCache */
    public WorkflowStepTypeTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
    }
    
    public WorkflowStepTypeTransfer getWorkflowStepTypeTransfer(WorkflowStepType workflowStepType) {
        var workflowStepTypeTransfer = get(workflowStepType);
        
        if(workflowStepTypeTransfer == null) {
            var workflowStepTypeName = workflowStepType.getWorkflowStepTypeName();
            var description = workflowControl.getBestWorkflowStepTypeDescription(workflowStepType, getLanguage());
            
            workflowStepTypeTransfer = new WorkflowStepTypeTransfer(workflowStepTypeName, description);
            put(workflowStepType, workflowStepTypeTransfer);
        }
        
        return workflowStepTypeTransfer;
    }
    
}
