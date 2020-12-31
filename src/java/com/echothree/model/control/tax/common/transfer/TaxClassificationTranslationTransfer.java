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

package com.echothree.model.control.tax.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class TaxClassificationTranslationTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private TaxClassificationTransfer taxClassification;
    private String description;
    private MimeTypeTransfer overviewMimeType;
    private String overview;
    
    /** Creates a new instance of TaxClassificationTranslationTransfer */
    public TaxClassificationTranslationTransfer(LanguageTransfer language, TaxClassificationTransfer taxClassification, String description,
            MimeTypeTransfer overviewMimeType, String overview) {
        this.language = language;
        this.taxClassification = taxClassification;
        this.description = description;
        this.overviewMimeType = overviewMimeType;
        this.overview = overview;
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
     * Returns the taxClassification.
     * @return the taxClassification
     */
    public TaxClassificationTransfer getTaxClassification() {
        return taxClassification;
    }

    /**
     * Sets the taxClassification.
     * @param taxClassification the taxClassification to set
     */
    public void setTaxClassification(TaxClassificationTransfer taxClassification) {
        this.taxClassification = taxClassification;
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

    /**
     * Returns the overviewMimeType.
     * @return the overviewMimeType
     */
    public MimeTypeTransfer getOverviewMimeType() {
        return overviewMimeType;
    }

    /**
     * Sets the overviewMimeType.
     * @param overviewMimeType the overviewMimeType to set
     */
    public void setOverviewMimeType(MimeTypeTransfer overviewMimeType) {
        this.overviewMimeType = overviewMimeType;
    }

    /**
     * Returns the overview.
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Sets the overview.
     * @param overview the overview to set
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }
    
}
