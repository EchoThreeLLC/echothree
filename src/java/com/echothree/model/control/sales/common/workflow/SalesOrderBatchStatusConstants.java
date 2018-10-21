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

package com.echothree.model.control.sales.common.workflow;

public interface SalesOrderBatchStatusConstants {
    
    String Workflow_SALES_ORDER_BATCH_STATUS = "SALES_ORDER_BATCH_STATUS";
    
    String WorkflowStep_ENTRY    = "ENTRY";
    String WorkflowStep_AUDIT    = "AUDIT";
    String WorkflowStep_COMPLETE = "COMPLETE";
    
    String WorkflowEntrance_ENTRY = "ENTRY";
    
    String WorkflowDestination_ENTRY_TO_AUDIT    = "ENTRY_TO_AUDIT";
    String WorkflowDestination_AUDIT_TO_ENTRY    = "AUDIT_TO_ENTRY";
    String WorkflowDestination_AUDIT_TO_COMPLETE = "AUDIT_TO_COMPLETE";
    
}
