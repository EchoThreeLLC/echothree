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

package com.echothree.control.user.workrequirement.common.result;

import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortTypeTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementScopeTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTypeTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetWorkRequirementScopesResult
        extends BaseResult {
    
    WorkEffortTypeTransfer getWorkEffortType();
    void setWorkEffortType(WorkEffortTypeTransfer workEffortType);
    
    WorkRequirementTypeTransfer getWorkRequirementType();
    void setWorkRequirementType(WorkRequirementTypeTransfer workRequirementType);
    
    WorkEffortScopeTransfer getWorkEffortScope();
    void setWorkEffortScope(WorkEffortScopeTransfer workEffortScope);
    
    List<WorkRequirementScopeTransfer> getWorkRequirementScopes();
    void setWorkRequirementScopes(List<WorkRequirementScopeTransfer> workRequirementScopes);
    
}
