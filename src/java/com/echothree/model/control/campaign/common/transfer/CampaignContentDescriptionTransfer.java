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

package com.echothree.model.control.campaign.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CampaignContentDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private CampaignContentTransfer campaignContent;
    private String description;
    
    /** Creates a new instance of CampaignContentDescriptionTransfer */
    public CampaignContentDescriptionTransfer(LanguageTransfer language, CampaignContentTransfer campaignContent, String description) {
        this.language = language;
        this.campaignContent = campaignContent;
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
     * Returns the campaignContent.
     * @return the campaignContent
     */
    public CampaignContentTransfer getCampaignContent() {
        return campaignContent;
    }

    /**
     * Sets the campaignContent.
     * @param campaignContent the campaignContent to set
     */
    public void setCampaignContent(CampaignContentTransfer campaignContent) {
        this.campaignContent = campaignContent;
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
