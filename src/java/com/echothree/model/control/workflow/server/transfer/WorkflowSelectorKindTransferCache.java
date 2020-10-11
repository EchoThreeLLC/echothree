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

package com.echothree.model.control.workflow.server.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorKindTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowSelectorKindTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowSelectorKind;
import com.echothree.util.server.persistence.Session;

public class WorkflowSelectorKindTransferCache
        extends BaseWorkflowTransferCache<WorkflowSelectorKind, WorkflowSelectorKindTransfer> {
    
    SelectorControl selectorControl;
    
    /** Creates a new instance of WorkflowSelectorKindTransferCache */
    public WorkflowSelectorKindTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
        
        selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
    }
    
    public WorkflowSelectorKindTransfer getWorkflowSelectorKindTransfer(WorkflowSelectorKind workflowSelectorKind) {
        WorkflowSelectorKindTransfer workflowSelectorKindTransfer = get(workflowSelectorKind);
        
        if(workflowSelectorKindTransfer == null) {
            WorkflowTransfer workflow = workflowControl.getWorkflowTransfer(userVisit, workflowSelectorKind.getWorkflow());
            SelectorKindTransfer selectorKind = selectorControl.getSelectorKindTransfer(userVisit, workflowSelectorKind.getSelectorKind());
            
            workflowSelectorKindTransfer = new WorkflowSelectorKindTransfer(workflow, selectorKind);
            put(workflowSelectorKind, workflowSelectorKindTransfer);
        }
        
        return workflowSelectorKindTransfer;
    }
    
}
