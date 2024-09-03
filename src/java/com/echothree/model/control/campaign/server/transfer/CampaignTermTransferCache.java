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

package com.echothree.model.control.campaign.server.transfer;

import com.echothree.model.control.campaign.common.CampaignOptions;
import com.echothree.model.control.campaign.common.transfer.CampaignTermTransfer;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.campaign.common.workflow.CampaignTermStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CampaignTermTransferCache
        extends BaseCampaignTransferCache<CampaignTerm, CampaignTermTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of CampaignTermTransferCache */
    public CampaignTermTransferCache(UserVisit userVisit, CampaignControl campaignControl) {
        super(userVisit, campaignControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(CampaignOptions.CampaignTermIncludeKey));
            setIncludeGuid(options.contains(CampaignOptions.CampaignTermIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public CampaignTermTransfer getCampaignTermTransfer(CampaignTerm campaignTerm) {
        var campaignTermTransfer = get(campaignTerm);

        if(campaignTermTransfer == null) {
            var campaignTermDetail = campaignTerm.getLastDetail();
            var campaignTermName = campaignTermDetail.getCampaignTermName();
            var valueSha1Hash = campaignTermDetail.getValueSha1Hash();
            var value = campaignTermDetail.getValue();
            var isDefault = campaignTermDetail.getIsDefault();
            var sortOrder = campaignTermDetail.getSortOrder();
            var description = campaignControl.getBestCampaignTermDescription(campaignTerm, getLanguage());

            var entityInstance = coreControl.getEntityInstanceByBasePK(campaignTerm.getPrimaryKey());
            var campaignTermStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS, entityInstance);
            
            campaignTermTransfer = new CampaignTermTransfer(campaignTermName, valueSha1Hash, value, isDefault, sortOrder, description,
                    campaignTermStatusTransfer);
            put(campaignTerm, campaignTermTransfer);
        }

        return campaignTermTransfer;
    }

}
