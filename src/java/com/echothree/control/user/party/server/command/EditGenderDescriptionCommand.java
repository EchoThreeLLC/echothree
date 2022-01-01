// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.GenderDescriptionEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditGenderDescriptionForm;
import com.echothree.control.user.party.common.result.EditGenderDescriptionResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.GenderDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Gender;
import com.echothree.model.data.party.server.entity.GenderDescription;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.value.GenderDescriptionValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditGenderDescriptionCommand
        extends BaseEditCommand<GenderDescriptionSpec, GenderDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("GenderName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditGenderDescriptionCommand */
    public EditGenderDescriptionCommand(UserVisitPK userVisitPK, EditGenderDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        EditGenderDescriptionResult result = PartyResultFactory.getEditGenderDescriptionResult();
        String genderName = spec.getGenderName();
        Gender gender = partyControl.getGenderByName(genderName);
        
        if(gender != null) {
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    GenderDescription genderDescription = partyControl.getGenderDescription(gender, language);
                    
                    if(genderDescription != null) {
                        result.setGenderDescription(partyControl.getGenderDescriptionTransfer(getUserVisit(), genderDescription));
                        
                        if(lockEntity(gender)) {
                            GenderDescriptionEdit edit = PartyEditFactory.getGenderDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(genderDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(gender));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGenderDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    GenderDescriptionValue genderDescriptionValue = partyControl.getGenderDescriptionValueForUpdate(gender, language);
                    
                    if(genderDescriptionValue != null) {
                        if(lockEntityForUpdate(gender)) {
                            try {
                                String description = edit.getDescription();
                                
                                genderDescriptionValue.setDescription(description);
                                
                                partyControl.updateGenderDescriptionFromValue(genderDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(gender);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGenderDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGenderName.name(), genderName);
        }
        
        return result;
    }
    
}
