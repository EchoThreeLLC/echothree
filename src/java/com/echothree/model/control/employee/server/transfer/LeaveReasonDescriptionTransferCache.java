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

package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.employee.remote.transfer.LeaveReasonDescriptionTransfer;
import com.echothree.model.control.employee.remote.transfer.LeaveReasonTransfer;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.employee.server.entity.LeaveReasonDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class LeaveReasonDescriptionTransferCache
        extends BaseEmployeeDescriptionTransferCache<LeaveReasonDescription, LeaveReasonDescriptionTransfer> {
    
    /** Creates a new instance of LeaveReasonDescriptionTransferCache */
    public LeaveReasonDescriptionTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
    }
    
    public LeaveReasonDescriptionTransfer getLeaveReasonDescriptionTransfer(LeaveReasonDescription leaveReasonDescription) {
        LeaveReasonDescriptionTransfer leaveReasonDescriptionTransfer = get(leaveReasonDescription);
        
        if(leaveReasonDescriptionTransfer == null) {
            LeaveReasonTransfer leaveReasonTransfer = employeeControl.getLeaveReasonTransfer(userVisit,
                    leaveReasonDescription.getLeaveReason());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, leaveReasonDescription.getLanguage());
            
            leaveReasonDescriptionTransfer = new LeaveReasonDescriptionTransfer(languageTransfer, leaveReasonTransfer, leaveReasonDescription.getDescription());
            put(leaveReasonDescription, leaveReasonDescriptionTransfer);
        }
        
        return leaveReasonDescriptionTransfer;
    }
    
}
