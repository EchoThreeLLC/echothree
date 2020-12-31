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

package com.echothree.model.control.license.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class LicenseTypeDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private LicenseTypeTransfer licenseType;
    private String description;
    
    /** Creates a new instance of LicenseTypeDescriptionTransfer */
    public LicenseTypeDescriptionTransfer(LanguageTransfer language, LicenseTypeTransfer licenseType, String description) {
        this.language = language;
        this.licenseType = licenseType;
        this.description = description;
    }

    /**
     * Returns the language.
     * @return the language
     */
    public LanguageTransfer getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     * @param language the language to set
     */
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    /**
     * Returns the licenseType.
     * @return the licenseType
     */
    public LicenseTypeTransfer getLicenseType() {
        return licenseType;
    }

    /**
     * Sets the licenseType.
     * @param licenseType the licenseType to set
     */
    public void setLicenseType(LicenseTypeTransfer licenseType) {
        this.licenseType = licenseType;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
