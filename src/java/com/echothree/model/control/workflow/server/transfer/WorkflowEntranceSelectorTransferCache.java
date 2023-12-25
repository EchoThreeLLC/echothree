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

import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceSelectorTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSelector;
import com.echothree.util.server.persistence.Session;

public class WorkflowEntranceSelectorTransferCache
        extends BaseWorkflowTransferCache<WorkflowEntranceSelector, WorkflowEntranceSelectorTransfer> {
    
    SelectorControl selectorControl;
    
    /** Creates a new instance of WorkflowEntranceSelectorTransferCache */
    public WorkflowEntranceSelectorTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
        
        selectorControl = Session.getModelController(SelectorControl.class);
    }
    
    public WorkflowEntranceSelectorTransfer getWorkflowEntranceSelectorTransfer(WorkflowEntranceSelector workflowEntranceSelector) {
        WorkflowEntranceSelectorTransfer workflowEntranceSelectorTransfer = get(workflowEntranceSelector);
        
        if(workflowEntranceSelectorTransfer == null) {
            WorkflowEntranceTransfer workflowEntrance = workflowControl.getWorkflowEntranceTransfer(userVisit, workflowEntranceSelector.getWorkflowEntrance());
            SelectorTransfer selector = selectorControl.getSelectorTransfer(userVisit, workflowEntranceSelector.getSelector());
            
            workflowEntranceSelectorTransfer = new WorkflowEntranceSelectorTransfer(workflowEntrance, selector);
            put(workflowEntranceSelector, workflowEntranceSelectorTransfer);
        }
        
        return workflowEntranceSelectorTransfer;
    }
    
}
