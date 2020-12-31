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

package com.echothree.model.control.workflow.common.transfer;

import com.echothree.model.control.security.common.transfer.SecurityRoleTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class WorkflowDestinationSecurityRoleTransfer
        extends BaseTransfer {
    
    private WorkflowDestinationPartyTypeTransfer workflowDestinationPartyType;
    private SecurityRoleTransfer securityRole;
    
    /** Creates a new instance of WorkflowDestinationSecurityRoleTransfer */
    public WorkflowDestinationSecurityRoleTransfer(WorkflowDestinationPartyTypeTransfer workflowDestinationPartyType, SecurityRoleTransfer securityRole) {
        this.workflowDestinationPartyType = workflowDestinationPartyType;
        this.securityRole = securityRole;
    }
    
    public WorkflowDestinationPartyTypeTransfer getWorkflowDestinationPartyType() {
        return workflowDestinationPartyType;
    }
    
    public void setWorkflowDestinationPartyType(WorkflowDestinationPartyTypeTransfer workflowDestinationPartyType) {
        this.workflowDestinationPartyType = workflowDestinationPartyType;
    }
    
    public SecurityRoleTransfer getSecurityRole() {
        return securityRole;
    }
    
    public void setSecurityRole(SecurityRoleTransfer securityRole) {
        this.securityRole = securityRole;
    }
    
}
