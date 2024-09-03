// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.employee.common.transfer.TerminationReasonTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.TerminationReason;
import com.echothree.model.data.employee.server.entity.TerminationReasonDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TerminationReasonTransferCache
        extends BaseEmployeeTransferCache<TerminationReason, TerminationReasonTransfer> {
    
    /** Creates a new instance of TerminationReasonTransferCache */
    public TerminationReasonTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public TerminationReasonTransfer getTerminationReasonTransfer(TerminationReason terminationReason) {
        var terminationReasonTransfer = get(terminationReason);
        
        if(terminationReasonTransfer == null) {
            var terminationReasonDetail = terminationReason.getLastDetail();
            var terminationReasonName = terminationReasonDetail.getTerminationReasonName();
            var isDefault = terminationReasonDetail.getIsDefault();
            var sortOrder = terminationReasonDetail.getSortOrder();
            var description = employeeControl.getBestTerminationReasonDescription(terminationReason, getLanguage());
            
            terminationReasonTransfer = new TerminationReasonTransfer(terminationReasonName, isDefault, sortOrder, description);
            put(terminationReason, terminationReasonTransfer);
        }
        
        return terminationReasonTransfer;
    }
    
}
