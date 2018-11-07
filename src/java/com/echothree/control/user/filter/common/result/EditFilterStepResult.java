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

package com.echothree.control.user.filter.common.result;

import com.echothree.control.user.filter.common.edit.FilterStepEdit;
import com.echothree.model.control.filter.common.transfer.FilterStepTransfer;
import com.echothree.util.common.command.BaseEditResult;

public interface EditFilterStepResult
        extends BaseEditResult<FilterStepEdit> {
    
    FilterStepTransfer getFilterStep();
    void setFilterStep(FilterStepTransfer FilterStep);
    
}
