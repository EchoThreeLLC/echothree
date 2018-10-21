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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.remote.edit.PicklistEditFactory;
import com.echothree.control.user.picklist.remote.edit.PicklistTypeDescriptionEdit;
import com.echothree.control.user.picklist.remote.form.EditPicklistTypeDescriptionForm;
import com.echothree.control.user.picklist.remote.result.EditPicklistTypeDescriptionResult;
import com.echothree.control.user.picklist.remote.result.PicklistResultFactory;
import com.echothree.control.user.picklist.remote.spec.PicklistTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.picklist.server.PicklistControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.picklist.server.entity.PicklistTypeDescription;
import com.echothree.model.data.picklist.server.value.PicklistTypeDescriptionValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPicklistTypeDescriptionCommand
        extends BaseAbstractEditCommand<PicklistTypeDescriptionSpec, PicklistTypeDescriptionEdit, EditPicklistTypeDescriptionResult, PicklistTypeDescription, PicklistType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PicklistType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditPicklistTypeDescriptionCommand */
    public EditPicklistTypeDescriptionCommand(UserVisitPK userVisitPK, EditPicklistTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPicklistTypeDescriptionResult getResult() {
        return PicklistResultFactory.getEditPicklistTypeDescriptionResult();
    }

    @Override
    public PicklistTypeDescriptionEdit getEdit() {
        return PicklistEditFactory.getPicklistTypeDescriptionEdit();
    }

    @Override
    public PicklistTypeDescription getEntity(EditPicklistTypeDescriptionResult result) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        PicklistTypeDescription picklistTypeDescription = null;
        String picklistTypeName = spec.getPicklistTypeName();
        PicklistType picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    picklistTypeDescription = picklistControl.getPicklistTypeDescription(picklistType, language);
                } else { // EditMode.UPDATE
                    picklistTypeDescription = picklistControl.getPicklistTypeDescriptionForUpdate(picklistType, language);
                }

                if(picklistTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownPicklistTypeDescription.name(), picklistTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return picklistTypeDescription;
    }

    @Override
    public PicklistType getLockEntity(PicklistTypeDescription picklistTypeDescription) {
        return picklistTypeDescription.getPicklistType();
    }

    @Override
    public void fillInResult(EditPicklistTypeDescriptionResult result, PicklistTypeDescription picklistTypeDescription) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);

        result.setPicklistTypeDescription(picklistControl.getPicklistTypeDescriptionTransfer(getUserVisit(), picklistTypeDescription));
    }

    @Override
    public void doLock(PicklistTypeDescriptionEdit edit, PicklistTypeDescription picklistTypeDescription) {
        edit.setDescription(picklistTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(PicklistTypeDescription picklistTypeDescription) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        PicklistTypeDescriptionValue picklistTypeDescriptionValue = picklistControl.getPicklistTypeDescriptionValue(picklistTypeDescription);
        picklistTypeDescriptionValue.setDescription(edit.getDescription());

        picklistControl.updatePicklistTypeDescriptionFromValue(picklistTypeDescriptionValue, getPartyPK());
    }
    
}
