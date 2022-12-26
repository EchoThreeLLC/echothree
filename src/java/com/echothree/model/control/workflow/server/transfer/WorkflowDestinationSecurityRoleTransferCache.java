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

package com.echothree.model.control.workflow.server.transfer;

import com.echothree.model.control.security.common.transfer.SecurityRoleTransfer;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationPartyTypeTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationSecurityRoleTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSecurityRole;
import com.echothree.util.server.persistence.Session;

public class WorkflowDestinationSecurityRoleTransferCache
        extends BaseWorkflowTransferCache<WorkflowDestinationSecurityRole, WorkflowDestinationSecurityRoleTransfer> {
    
    SecurityControl securityControl;
    
    /** Creates a new instance of WorkflowDestinationSecurityRoleTransferCache */
    public WorkflowDestinationSecurityRoleTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
        
        securityControl = Session.getModelController(SecurityControl.class);
    }
    
    public WorkflowDestinationSecurityRoleTransfer getWorkflowDestinationSecurityRoleTransfer(WorkflowDestinationSecurityRole workflowDestinationSecurityRole) {
        WorkflowDestinationSecurityRoleTransfer workflowDestinationSecurityRoleTransfer = get(workflowDestinationSecurityRole);
        
        if(workflowDestinationSecurityRoleTransfer == null) {
            WorkflowDestinationPartyTypeTransfer workflowDestinationPartyType = workflowControl.getWorkflowDestinationPartyTypeTransfer(userVisit, workflowDestinationSecurityRole.getWorkflowDestinationPartyType());
            SecurityRoleTransfer securityRole = securityControl.getSecurityRoleTransfer(userVisit, workflowDestinationSecurityRole.getSecurityRole());
            
            workflowDestinationSecurityRoleTransfer = new WorkflowDestinationSecurityRoleTransfer(workflowDestinationPartyType, securityRole);
            put(workflowDestinationSecurityRole, workflowDestinationSecurityRoleTransfer);
        }
        
        return workflowDestinationSecurityRoleTransfer;
    }
    
}
