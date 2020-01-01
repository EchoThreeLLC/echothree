// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.employee.common.transfer.TerminationReasonDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.TerminationReasonTransfer;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.employee.server.entity.TerminationReasonDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TerminationReasonDescriptionTransferCache
        extends BaseEmployeeDescriptionTransferCache<TerminationReasonDescription, TerminationReasonDescriptionTransfer> {
    
    /** Creates a new instance of TerminationReasonDescriptionTransferCache */
    public TerminationReasonDescriptionTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
    }
    
    public TerminationReasonDescriptionTransfer getTerminationReasonDescriptionTransfer(TerminationReasonDescription terminationReasonDescription) {
        TerminationReasonDescriptionTransfer terminationReasonDescriptionTransfer = get(terminationReasonDescription);
        
        if(terminationReasonDescriptionTransfer == null) {
            TerminationReasonTransfer terminationReasonTransfer = employeeControl.getTerminationReasonTransfer(userVisit,
                    terminationReasonDescription.getTerminationReason());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, terminationReasonDescription.getLanguage());
            
            terminationReasonDescriptionTransfer = new TerminationReasonDescriptionTransfer(languageTransfer, terminationReasonTransfer, terminationReasonDescription.getDescription());
            put(terminationReasonDescription, terminationReasonDescriptionTransfer);
        }
        
        return terminationReasonDescriptionTransfer;
    }
    
}
