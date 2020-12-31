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

package com.echothree.model.control.job.common.workflow;

public interface JobStatusConstants {
    
    String Workflow_JOB_STATUS = "JOB_STATUS";
    
    String WorkflowStep_ENABLED  = "ENABLED";
    String WorkflowStep_DISABLED = "DISABLED";
    String WorkflowStep_ERROR    = "ERROR";
    
    String WorkflowEntrance_NEW_ENABLED  = "NEW_ENABLED";
    String WorkflowEntrance_NEW_DISABLED = "NEW_DISABLED";
    
    String WorkflowDestination_ENABLED_ENABLED_TO_DISABLED  = "ENABLED_TO_DISABLED";
    String WorkflowDestination_ENABLED_ENABLED_TO_ERROR     = "ENABLED_TO_ERROR";
    String WorkflowDestination_DISABLED_DISABLED_TO_ENABLED = "DISABLED_TO_ENABLED";
    String WorkflowDestination_ERROR_ERROR_TO_ENABLED       = "ERROR_TO_ENABLED";
    
}
