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

package com.echothree.model.control.workflow.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class WorkflowTypeTransfer
        extends BaseTransfer {
    
    private String workflowTypeName;
    private String description;
    
    /** Creates a new instance of WorkflowTypeTransfer */
    public WorkflowTypeTransfer(String workflowTypeName, String description) {
        this.workflowTypeName = workflowTypeName;
        this.description = description;
    }
    
    public String getWorkflowTypeName() {
        return workflowTypeName;
    }
    
    public void setWorkflowTypeName(String workflowTypeName) {
        this.workflowTypeName = workflowTypeName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
