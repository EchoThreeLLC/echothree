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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.edit.CancellationPolicyEditFactory;
import com.echothree.control.user.cancellationpolicy.common.edit.CancellationTypeDescriptionEdit;
import com.echothree.control.user.cancellationpolicy.common.form.EditCancellationTypeDescriptionForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.EditCancellationTypeDescriptionResult;
import com.echothree.control.user.cancellationpolicy.common.spec.CancellationTypeDescriptionSpec;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationTypeDescription;
import com.echothree.model.data.cancellationpolicy.server.value.CancellationTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCancellationTypeDescriptionCommand
        extends BaseEditCommand<CancellationTypeDescriptionSpec, CancellationTypeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCancellationTypeDescriptionCommand */
    public EditCancellationTypeDescriptionCommand(UserVisitPK userVisitPK, EditCancellationTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var cancellationPolicyControl = Session.getModelController(CancellationPolicyControl.class);
        EditCancellationTypeDescriptionResult result = CancellationPolicyResultFactory.getEditCancellationTypeDescriptionResult();
        String cancellationKindName = spec.getCancellationKindName();
        CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);
        
        if(cancellationKind != null) {
            String cancellationTypeName = spec.getCancellationTypeName();
            CancellationType cancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);
            
            if(cancellationType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        CancellationTypeDescription cancellationTypeDescription = cancellationPolicyControl.getCancellationTypeDescription(cancellationType, language);
                        
                        if(cancellationTypeDescription != null) {
                            result.setCancellationTypeDescription(cancellationPolicyControl.getCancellationTypeDescriptionTransfer(getUserVisit(), cancellationTypeDescription));
                            
                            if(lockEntity(cancellationType)) {
                                CancellationTypeDescriptionEdit edit = CancellationPolicyEditFactory.getCancellationTypeDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(cancellationTypeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(cancellationType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationTypeDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        CancellationTypeDescriptionValue cancellationTypeDescriptionValue = cancellationPolicyControl.getCancellationTypeDescriptionValueForUpdate(cancellationType, language);
                        
                        if(cancellationTypeDescriptionValue != null) {
                            if(lockEntityForUpdate(cancellationType)) {
                                try {
                                    String description = edit.getDescription();
                                    
                                    cancellationTypeDescriptionValue.setDescription(description);
                                    
                                    cancellationPolicyControl.updateCancellationTypeDescriptionFromValue(cancellationTypeDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(cancellationType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCancellationTypeDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
        }
        
        return result;
    }
    
}
