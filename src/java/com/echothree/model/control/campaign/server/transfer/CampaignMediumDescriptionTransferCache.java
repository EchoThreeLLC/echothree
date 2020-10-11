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

package com.echothree.model.control.campaign.server.transfer;

import com.echothree.model.control.campaign.common.transfer.CampaignMediumDescriptionTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignMediumTransfer;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.campaign.server.entity.CampaignMediumDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CampaignMediumDescriptionTransferCache
        extends BaseCampaignDescriptionTransferCache<CampaignMediumDescription, CampaignMediumDescriptionTransfer> {
    
    /** Creates a new instance of CampaignMediumDescriptionTransferCache */
    public CampaignMediumDescriptionTransferCache(UserVisit userVisit, CampaignControl campaignControl) {
        super(userVisit, campaignControl);
    }
    
    public CampaignMediumDescriptionTransfer getCampaignMediumDescriptionTransfer(CampaignMediumDescription campaignMediumDescription) {
        CampaignMediumDescriptionTransfer campaignMediumDescriptionTransfer = get(campaignMediumDescription);
        
        if(campaignMediumDescriptionTransfer == null) {
            CampaignMediumTransfer campaignMediumTransfer = campaignControl.getCampaignMediumTransfer(userVisit, campaignMediumDescription.getCampaignMedium());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, campaignMediumDescription.getLanguage());
            
            campaignMediumDescriptionTransfer = new CampaignMediumDescriptionTransfer(languageTransfer, campaignMediumTransfer, campaignMediumDescription.getDescription());
            put(campaignMediumDescription, campaignMediumDescriptionTransfer);
        }
        return campaignMediumDescriptionTransfer;
    }
    
}
