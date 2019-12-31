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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.ApplicationDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ApplicationTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.core.server.entity.ApplicationDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ApplicationDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<ApplicationDescription, ApplicationDescriptionTransfer> {
    
    /** Creates a new instance of ApplicationDescriptionTransferCache */
    public ApplicationDescriptionTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public ApplicationDescriptionTransfer getApplicationDescriptionTransfer(ApplicationDescription applicationDescription) {
        ApplicationDescriptionTransfer applicationDescriptionTransfer = get(applicationDescription);
        
        if(applicationDescriptionTransfer == null) {
            ApplicationTransfer applicationTransfer = coreControl.getApplicationTransfer(userVisit, applicationDescription.getApplication());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, applicationDescription.getLanguage());
            
            applicationDescriptionTransfer = new ApplicationDescriptionTransfer(languageTransfer, applicationTransfer, applicationDescription.getDescription());
            put(applicationDescription, applicationDescriptionTransfer);
        }
        return applicationDescriptionTransfer;
    }
    
}
