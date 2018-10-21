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

import com.echothree.model.control.employee.remote.transfer.EmployeeTypeDescriptionTransfer;
import com.echothree.model.control.employee.remote.transfer.EmployeeTypeTransfer;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.employee.server.entity.EmployeeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EmployeeTypeDescriptionTransferCache
        extends BaseEmployeeDescriptionTransferCache<EmployeeTypeDescription, EmployeeTypeDescriptionTransfer> {
    
    /** Creates a new instance of EmployeeTypeDescriptionTransferCache */
    public EmployeeTypeDescriptionTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
    }
    
    public EmployeeTypeDescriptionTransfer getEmployeeTypeDescriptionTransfer(EmployeeTypeDescription employeeTypeDescription) {
        EmployeeTypeDescriptionTransfer employeeTypeDescriptionTransfer = get(employeeTypeDescription);
        
        if(employeeTypeDescriptionTransfer == null) {
            EmployeeTypeTransfer employeeTypeTransfer = employeeControl.getEmployeeTypeTransfer(userVisit, employeeTypeDescription.getEmployeeType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, employeeTypeDescription.getLanguage());
            
            employeeTypeDescriptionTransfer = new EmployeeTypeDescriptionTransfer(languageTransfer, employeeTypeTransfer, employeeTypeDescription.getDescription());
            put(employeeTypeDescription, employeeTypeDescriptionTransfer);
        }
        
        return employeeTypeDescriptionTransfer;
    }
    
}
