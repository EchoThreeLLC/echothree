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

package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.employee.common.transfer.TerminationTypeDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.TerminationTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.employee.server.entity.TerminationTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TerminationTypeDescriptionTransferCache
        extends BaseEmployeeDescriptionTransferCache<TerminationTypeDescription, TerminationTypeDescriptionTransfer> {
    
    /** Creates a new instance of TerminationTypeDescriptionTransferCache */
    public TerminationTypeDescriptionTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
    }
    
    public TerminationTypeDescriptionTransfer getTerminationTypeDescriptionTransfer(TerminationTypeDescription terminationTypeDescription) {
        TerminationTypeDescriptionTransfer terminationTypeDescriptionTransfer = get(terminationTypeDescription);
        
        if(terminationTypeDescriptionTransfer == null) {
            TerminationTypeTransfer terminationTypeTransfer = employeeControl.getTerminationTypeTransfer(userVisit,
                    terminationTypeDescription.getTerminationType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, terminationTypeDescription.getLanguage());
            
            terminationTypeDescriptionTransfer = new TerminationTypeDescriptionTransfer(languageTransfer, terminationTypeTransfer, terminationTypeDescription.getDescription());
            put(terminationTypeDescription, terminationTypeDescriptionTransfer);
        }
        
        return terminationTypeDescriptionTransfer;
    }
    
}
