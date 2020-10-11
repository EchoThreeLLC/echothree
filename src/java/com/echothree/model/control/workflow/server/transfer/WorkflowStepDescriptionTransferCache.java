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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepDescriptionTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowStepTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDescription;

public class WorkflowStepDescriptionTransferCache
        extends BaseWorkflowDescriptionTransferCache<WorkflowStepDescription, WorkflowStepDescriptionTransfer> {
    
    /** Creates a new instance of WorkflowStepDescriptionTransferCache */
    public WorkflowStepDescriptionTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
    }
    
    public WorkflowStepDescriptionTransfer getWorkflowStepDescriptionTransfer(WorkflowStepDescription workflowStepDescription) {
        WorkflowStepDescriptionTransfer workflowStepDescriptionTransfer = get(workflowStepDescription);
        
        if(workflowStepDescriptionTransfer == null) {
            WorkflowStepTransfer workflowStepTransfer = workflowControl.getWorkflowStepTransfer(userVisit, workflowStepDescription.getWorkflowStep());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, workflowStepDescription.getLanguage());
            
            workflowStepDescriptionTransfer = new WorkflowStepDescriptionTransfer(languageTransfer, workflowStepTransfer, workflowStepDescription.getDescription());
            put(workflowStepDescription, workflowStepDescriptionTransfer);
        }
        
        return workflowStepDescriptionTransfer;
    }
    
}
