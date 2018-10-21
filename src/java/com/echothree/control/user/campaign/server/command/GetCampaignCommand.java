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

package com.echothree.control.user.campaign.server.command;

import com.echothree.control.user.campaign.remote.form.GetCampaignForm;
import com.echothree.control.user.campaign.remote.result.CampaignResultFactory;
import com.echothree.control.user.campaign.remote.result.GetCampaignResult;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCampaignCommand
        extends BaseSimpleCommand<GetCampaignForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Campaign.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetCampaignCommand */
    public GetCampaignCommand(UserVisitPK userVisitPK, GetCampaignForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetCampaignResult result = CampaignResultFactory.getGetCampaignResult();
        String campaignName = form.getCampaignName();
        int parameterCount = (campaignName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            CampaignControl campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
            Campaign campaign = null;

            if(campaignName == null) {
                EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHOTHREE.name(),
                        EntityTypes.Campaign.name());
                
                if(!hasExecutionErrors()) {
                    campaign = campaignControl.getCampaignByEntityInstance(entityInstance);
                }
            } else {
                campaign = campaignControl.getCampaignByName(campaignName);

                if(campaign == null) {
                    addExecutionError(ExecutionErrors.UnknownCampaignName.name(), campaignName);
                }
            }

            if(!hasExecutionErrors()) {
                result.setCampaign(campaignControl.getCampaignTransfer(getUserVisit(), campaign));
                sendEventUsingNames(campaign.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
