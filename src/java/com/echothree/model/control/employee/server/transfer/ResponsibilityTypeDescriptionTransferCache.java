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

package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.employee.common.transfer.ResponsibilityTypeDescriptionTransfer;
import com.echothree.model.control.employee.common.transfer.ResponsibilityTypeTransfer;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.employee.server.entity.ResponsibilityTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ResponsibilityTypeDescriptionTransferCache
        extends BaseEmployeeDescriptionTransferCache<ResponsibilityTypeDescription, ResponsibilityTypeDescriptionTransfer> {
    
    /** Creates a new instance of ResponsibilityTypeDescriptionTransferCache */
    public ResponsibilityTypeDescriptionTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
    }
    
    public ResponsibilityTypeDescriptionTransfer getResponsibilityTypeDescriptionTransfer(ResponsibilityTypeDescription responsibilityTypeDescription) {
        ResponsibilityTypeDescriptionTransfer responsibilityTypeDescriptionTransfer = get(responsibilityTypeDescription);
        
        if(responsibilityTypeDescriptionTransfer == null) {
            ResponsibilityTypeTransfer responsibilityTypeTransfer = employeeControl.getResponsibilityTypeTransfer(userVisit,
                    responsibilityTypeDescription.getResponsibilityType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, responsibilityTypeDescription.getLanguage());
            
            responsibilityTypeDescriptionTransfer = new ResponsibilityTypeDescriptionTransfer(languageTransfer, responsibilityTypeTransfer, responsibilityTypeDescription.getDescription());
            put(responsibilityTypeDescription, responsibilityTypeDescriptionTransfer);
        }
        
        return responsibilityTypeDescriptionTransfer;
    }
    
}
