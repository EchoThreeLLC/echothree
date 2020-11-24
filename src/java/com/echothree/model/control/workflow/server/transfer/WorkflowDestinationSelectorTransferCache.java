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

import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationSelectorTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSelector;
import com.echothree.util.server.persistence.Session;

public class WorkflowDestinationSelectorTransferCache
        extends BaseWorkflowTransferCache<WorkflowDestinationSelector, WorkflowDestinationSelectorTransfer> {
    
    SelectorControl selectorControl;
    
    /** Creates a new instance of WorkflowDestinationSelectorTransferCache */
    public WorkflowDestinationSelectorTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
        
        selectorControl = Session.getModelController(SelectorControl.class);
    }
    
    public WorkflowDestinationSelectorTransfer getWorkflowDestinationSelectorTransfer(WorkflowDestinationSelector workflowDestinationSelector) {
        WorkflowDestinationSelectorTransfer workflowDestinationSelectorTransfer = get(workflowDestinationSelector);
        
        if(workflowDestinationSelectorTransfer == null) {
            WorkflowDestinationTransfer workflowDestination = workflowControl.getWorkflowDestinationTransfer(userVisit, workflowDestinationSelector.getWorkflowDestination());
            SelectorTransfer selector = selectorControl.getSelectorTransfer(userVisit, workflowDestinationSelector.getSelector());
            
            workflowDestinationSelectorTransfer = new WorkflowDestinationSelectorTransfer(workflowDestination, selector);
            put(workflowDestinationSelector, workflowDestinationSelectorTransfer);
        }
        
        return workflowDestinationSelectorTransfer;
    }
    
}
