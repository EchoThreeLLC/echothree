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

package com.echothree.model.control.license.server.transfer;

import com.echothree.model.control.license.common.transfer.LicenseTypeDescriptionTransfer;
import com.echothree.model.control.license.common.transfer.LicenseTypeTransfer;
import com.echothree.model.control.license.server.control.LicenseControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.license.server.entity.LicenseTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class LicenseTypeDescriptionTransferCache
        extends BaseLicenseDescriptionTransferCache<LicenseTypeDescription, LicenseTypeDescriptionTransfer> {
    
    /** Creates a new instance of LicenseTypeDescriptionTransferCache */
    public LicenseTypeDescriptionTransferCache(UserVisit userVisit, LicenseControl licenseControl) {
        super(userVisit, licenseControl);
    }
    
    public LicenseTypeDescriptionTransfer getLicenseTypeDescriptionTransfer(LicenseTypeDescription licenseTypeDescription) {
        var licenseTypeDescriptionTransfer = get(licenseTypeDescription);
        
        if(licenseTypeDescriptionTransfer == null) {
            var licenseTypeTransfer = licenseControl.getLicenseTypeTransfer(userVisit, licenseTypeDescription.getLicenseType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, licenseTypeDescription.getLanguage());
            
            licenseTypeDescriptionTransfer = new LicenseTypeDescriptionTransfer(languageTransfer, licenseTypeTransfer, licenseTypeDescription.getDescription());
            put(licenseTypeDescription, licenseTypeDescriptionTransfer);
        }
        return licenseTypeDescriptionTransfer;
    }
    
}
