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

package com.echothree.control.user.campaign.server.command;

import com.echothree.control.user.campaign.common.edit.CampaignEditFactory;
import com.echothree.control.user.campaign.common.edit.CampaignSourceDescriptionEdit;
import com.echothree.control.user.campaign.common.form.EditCampaignSourceDescriptionForm;
import com.echothree.control.user.campaign.common.result.CampaignResultFactory;
import com.echothree.control.user.campaign.common.result.EditCampaignSourceDescriptionResult;
import com.echothree.control.user.campaign.common.spec.CampaignSourceDescriptionSpec;
import com.echothree.model.control.campaign.server.CampaignControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.campaign.server.entity.CampaignSource;
import com.echothree.model.data.campaign.server.entity.CampaignSourceDescription;
import com.echothree.model.data.campaign.server.value.CampaignSourceDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCampaignSourceDescriptionCommand
        extends BaseAbstractEditCommand<CampaignSourceDescriptionSpec, CampaignSourceDescriptionEdit, EditCampaignSourceDescriptionResult, CampaignSourceDescription, CampaignSource> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CampaignSource.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignSourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCampaignSourceDescriptionCommand */
    public EditCampaignSourceDescriptionCommand(UserVisitPK userVisitPK, EditCampaignSourceDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCampaignSourceDescriptionResult getResult() {
        return CampaignResultFactory.getEditCampaignSourceDescriptionResult();
    }

    @Override
    public CampaignSourceDescriptionEdit getEdit() {
        return CampaignEditFactory.getCampaignSourceDescriptionEdit();
    }

    @Override
    public CampaignSourceDescription getEntity(EditCampaignSourceDescriptionResult result) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignSourceDescription campaignSourceDescription = null;
        String campaignSourceName = spec.getCampaignSourceName();
        CampaignSource campaignSource = campaignControl.getCampaignSourceByName(campaignSourceName);

        if(campaignSource != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    campaignSourceDescription = campaignControl.getCampaignSourceDescription(campaignSource, language);
                } else { // EditMode.UPDATE
                    campaignSourceDescription = campaignControl.getCampaignSourceDescriptionForUpdate(campaignSource, language);
                }

                if(campaignSourceDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownCampaignSourceDescription.name(), campaignSourceName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCampaignSourceName.name(), campaignSourceName);
        }

        return campaignSourceDescription;
    }

    @Override
    public CampaignSource getLockEntity(CampaignSourceDescription campaignSourceDescription) {
        return campaignSourceDescription.getCampaignSource();
    }

    @Override
    public void fillInResult(EditCampaignSourceDescriptionResult result, CampaignSourceDescription campaignSourceDescription) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);

        result.setCampaignSourceDescription(campaignControl.getCampaignSourceDescriptionTransfer(getUserVisit(), campaignSourceDescription));
    }

    @Override
    public void doLock(CampaignSourceDescriptionEdit edit, CampaignSourceDescription campaignSourceDescription) {
        edit.setDescription(campaignSourceDescription.getDescription());
    }

    @Override
    public void doUpdate(CampaignSourceDescription campaignSourceDescription) {
        var campaignControl = (CampaignControl)Session.getModelController(CampaignControl.class);
        CampaignSourceDescriptionValue campaignSourceDescriptionValue = campaignControl.getCampaignSourceDescriptionValue(campaignSourceDescription);
        campaignSourceDescriptionValue.setDescription(edit.getDescription());

        campaignControl.updateCampaignSourceDescriptionFromValue(campaignSourceDescriptionValue, getPartyPK());
    }
    
}
