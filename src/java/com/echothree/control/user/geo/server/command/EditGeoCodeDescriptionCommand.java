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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.remote.edit.GeoCodeDescriptionEdit;
import com.echothree.control.user.geo.remote.edit.GeoEditFactory;
import com.echothree.control.user.geo.remote.form.EditGeoCodeDescriptionForm;
import com.echothree.control.user.geo.remote.result.EditGeoCodeDescriptionResult;
import com.echothree.control.user.geo.remote.result.GeoResultFactory;
import com.echothree.control.user.geo.remote.spec.GeoCodeDescriptionSpec;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeDescription;
import com.echothree.model.data.geo.server.value.GeoCodeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditGeoCodeDescriptionCommand
        extends BaseEditCommand<GeoCodeDescriptionSpec, GeoCodeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCode.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditGeoCodeDescriptionCommand */
    public EditGeoCodeDescriptionCommand(UserVisitPK userVisitPK, EditGeoCodeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        EditGeoCodeDescriptionResult result = GeoResultFactory.getEditGeoCodeDescriptionResult();
        String geoCodeName = spec.getGeoCodeName();
        GeoCode geoCode = geoControl.getGeoCodeByName(geoCodeName);
        
        if(geoCode != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    GeoCodeDescription geoCodeDescription = geoControl.getGeoCodeDescription(geoCode, language);
                    
                    if(geoCodeDescription != null) {
                        result.setGeoCodeDescription(geoControl.getGeoCodeDescriptionTransfer(getUserVisit(), geoCodeDescription));
                        
                        if(lockEntity(geoCode)) {
                            GeoCodeDescriptionEdit edit = GeoEditFactory.getGeoCodeDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(geoCodeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(geoCode));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeDescription.name(), geoCodeName, languageIsoName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    GeoCodeDescriptionValue geoCodeDescriptionValue = geoControl.getGeoCodeDescriptionValueForUpdate(geoCode, language);
                    
                    if(geoCodeDescriptionValue != null) {
                        if(lockEntityForUpdate(geoCode)) {
                            try {
                                String description = edit.getDescription();
                                
                                geoCodeDescriptionValue.setDescription(description);
                                
                                geoControl.updateGeoCodeDescriptionFromValue(geoCodeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(geoCode);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeDescription.name(), geoCodeName, languageIsoName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return result;
    }
    
}
