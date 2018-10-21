// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.workflow.common.WorkflowProperties;
import com.echothree.model.control.workflow.remote.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.remote.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceDetail;
import com.echothree.util.remote.form.TransferProperties;
import java.util.Set;

public class WorkflowEntranceTransferCache
        extends BaseWorkflowTransferCache<WorkflowEntrance, WorkflowEntranceTransfer> {
    
    TransferProperties transferProperties;
    boolean filterWorkflow;
    boolean filterWorkflowEntranceName;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of WorkflowEntranceTransferCache */
    public WorkflowEntranceTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(WorkflowEntranceTransfer.class);
            
            if(properties != null) {
                filterWorkflow = !properties.contains(WorkflowProperties.WORKFLOW);
                filterWorkflowEntranceName = !properties.contains(WorkflowProperties.WORKFLOW_ENTRANCE_NAME);
                filterIsDefault = !properties.contains(WorkflowProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(WorkflowProperties.SORT_ORDER);
                filterDescription = !properties.contains(WorkflowProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(WorkflowProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public WorkflowEntranceTransfer getWorkflowEntranceTransfer(WorkflowEntrance workflowEntrance) {
        WorkflowEntranceTransfer workflowEntranceTransfer = get(workflowEntrance);
        
        if(workflowEntranceTransfer == null) {
            WorkflowEntranceDetail workflowEntranceDetail = workflowEntrance.getLastDetail();
            WorkflowTransfer workflow = filterWorkflow ? null : workflowControl.getWorkflowTransfer(userVisit, workflowEntranceDetail.getWorkflow());
            String workflowEntranceName = filterWorkflowEntranceName ? null : workflowEntranceDetail.getWorkflowEntranceName();
            Boolean isDefault = filterIsDefault ? null : workflowEntranceDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : workflowEntranceDetail.getSortOrder();
            String description = filterDescription ? null : workflowControl.getBestWorkflowEntranceDescription(workflowEntrance, getLanguage());
            
            workflowEntranceTransfer = new WorkflowEntranceTransfer(workflow, workflowEntranceName, isDefault, sortOrder,
                    description);
            put(workflowEntrance, workflowEntranceTransfer);
        }
        
        return workflowEntranceTransfer;
    }
    
}
