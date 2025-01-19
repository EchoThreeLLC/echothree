// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.employee.common.transfer.TerminationTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.data.employee.server.entity.TerminationType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TerminationTypeTransferCache
        extends BaseEmployeeTransferCache<TerminationType, TerminationTypeTransfer> {
    
    /** Creates a new instance of TerminationTypeTransferCache */
    public TerminationTypeTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        setIncludeEntityInstance(true);
    }
    
    public TerminationTypeTransfer getTerminationTypeTransfer(TerminationType terminationType) {
        var terminationTypeTransfer = get(terminationType);
        
        if(terminationTypeTransfer == null) {
            var terminationTypeDetail = terminationType.getLastDetail();
            var terminationTypeName = terminationTypeDetail.getTerminationTypeName();
            var isDefault = terminationTypeDetail.getIsDefault();
            var sortOrder = terminationTypeDetail.getSortOrder();
            var description = employeeControl.getBestTerminationTypeDescription(terminationType, getLanguage());
            
            terminationTypeTransfer = new TerminationTypeTransfer(terminationTypeName, isDefault, sortOrder, description);
            put(terminationType, terminationTypeTransfer);
        }
        
        return terminationTypeTransfer;
    }
    
}
