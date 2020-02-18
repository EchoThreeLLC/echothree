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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.GlResourceTypeEdit;
import com.echothree.control.user.accounting.common.form.EditGlResourceTypeForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditGlResourceTypeResult;
import com.echothree.control.user.accounting.common.spec.GlResourceTypeSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
import com.echothree.model.data.accounting.server.entity.GlResourceTypeDescription;
import com.echothree.model.data.accounting.server.entity.GlResourceTypeDetail;
import com.echothree.model.data.accounting.server.value.GlResourceTypeDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlResourceTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditGlResourceTypeCommand
        extends BaseEditCommand<GlResourceTypeSpec, GlResourceTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.GlResourceType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlResourceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditGlResourceTypeCommand */
    public EditGlResourceTypeCommand(UserVisitPK userVisitPK, EditGlResourceTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        EditGlResourceTypeResult result = AccountingResultFactory.getEditGlResourceTypeResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            String glResourceTypeName = spec.getGlResourceTypeName();
            GlResourceType glResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);
            
            if(glResourceType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(glResourceType)) {
                        GlResourceTypeDescription glResourceTypeDescription = accountingControl.getGlResourceTypeDescription(glResourceType, getPreferredLanguage());
                        GlResourceTypeEdit edit = AccountingEditFactory.getGlResourceTypeEdit();
                        GlResourceTypeDetail glResourceTypeDetail = glResourceType.getLastDetail();

                        result.setGlResourceType(accountingControl.getGlResourceTypeTransfer(getUserVisit(), glResourceType));
                        sendEventUsingNames(glResourceType.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());

                        result.setEdit(edit);
                        edit.setGlResourceTypeName(glResourceTypeDetail.getGlResourceTypeName());
                        edit.setIsDefault(glResourceTypeDetail.getIsDefault().toString());
                        edit.setSortOrder(glResourceTypeDetail.getSortOrder().toString());

                        if(glResourceTypeDescription != null) {
                            edit.setDescription(glResourceTypeDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(glResourceType));
                } else { // EditMode.ABANDON
                    unlockEntity(glResourceType);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlResourceTypeName.name(), glResourceTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String glResourceTypeName = spec.getGlResourceTypeName();
            GlResourceType glResourceType = accountingControl.getGlResourceTypeByNameForUpdate(glResourceTypeName);
            
            if(glResourceType != null) {
                glResourceTypeName = edit.getGlResourceTypeName();
                GlResourceType duplicateGlResourceType = accountingControl.getGlResourceTypeByName(glResourceTypeName);
                
                if(duplicateGlResourceType == null || glResourceType.equals(duplicateGlResourceType)) {
                    if(lockEntityForUpdate(glResourceType)) {
                        try {
                            PartyPK partyPK = getPartyPK();
                            GlResourceTypeDetailValue glResourceTypeDetailValue = accountingControl.getGlResourceTypeDetailValueForUpdate(glResourceType);
                            GlResourceTypeDescription glResourceTypeDescription = accountingControl.getGlResourceTypeDescriptionForUpdate(glResourceType, getPreferredLanguage());
                            String description = edit.getDescription();
                            
                            glResourceTypeDetailValue.setGlResourceTypeName(edit.getGlResourceTypeName());
                            glResourceTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            glResourceTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            accountingControl.updateGlResourceTypeFromValue(glResourceTypeDetailValue, partyPK);
                            
                            if(glResourceTypeDescription == null && description != null) {
                                accountingControl.createGlResourceTypeDescription(glResourceType, getPreferredLanguage(), description, partyPK);
                            } else if(glResourceTypeDescription != null && description == null) {
                                accountingControl.deleteGlResourceTypeDescription(glResourceTypeDescription, partyPK);
                            } else if(glResourceTypeDescription != null && description != null) {
                                GlResourceTypeDescriptionValue glResourceTypeDescriptionValue = accountingControl.getGlResourceTypeDescriptionValue(glResourceTypeDescription);
                                
                                glResourceTypeDescriptionValue.setDescription(description);
                                accountingControl.updateGlResourceTypeDescriptionFromValue(glResourceTypeDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(glResourceType);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateGlResourceTypeName.name(), glResourceTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlResourceTypeName.name(), glResourceTypeName);
            }
            
            if(hasExecutionErrors()) {
                result.setGlResourceType(accountingControl.getGlResourceTypeTransfer(getUserVisit(), glResourceType));
                result.setEntityLock(getEntityLockTransfer(glResourceType));
            }
        }
        
        return result;
    }
    
}
