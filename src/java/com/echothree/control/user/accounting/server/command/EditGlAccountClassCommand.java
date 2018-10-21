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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.remote.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.remote.edit.GlAccountClassEdit;
import com.echothree.control.user.accounting.remote.form.EditGlAccountClassForm;
import com.echothree.control.user.accounting.remote.result.AccountingResultFactory;
import com.echothree.control.user.accounting.remote.result.EditGlAccountClassResult;
import com.echothree.control.user.accounting.remote.spec.GlAccountClassSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlAccountClassDescription;
import com.echothree.model.data.accounting.server.entity.GlAccountClassDetail;
import com.echothree.model.data.accounting.server.value.GlAccountClassDescriptionValue;
import com.echothree.model.data.accounting.server.value.GlAccountClassDetailValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
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

public class EditGlAccountClassCommand
        extends BaseEditCommand<GlAccountClassSpec, GlAccountClassEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.GlAccountClass.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGlAccountClassName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditGlAccountClassCommand */
    public EditGlAccountClassCommand(UserVisitPK userVisitPK, EditGlAccountClassForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        EditGlAccountClassResult result = AccountingResultFactory.getEditGlAccountClassResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            String glAccountClassName = spec.getGlAccountClassName();
            GlAccountClass glAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);
            
            if(glAccountClass != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(glAccountClass)) {
                        GlAccountClassDescription glAccountClassDescription = accountingControl.getGlAccountClassDescription(glAccountClass, getPreferredLanguage());
                        GlAccountClassEdit edit = AccountingEditFactory.getGlAccountClassEdit();
                        GlAccountClassDetail glAccountClassDetail = glAccountClass.getLastDetail();
                        GlAccountClass parentGlAccountClass = glAccountClassDetail.getParentGlAccountClass();

                        result.setGlAccountClass(accountingControl.getGlAccountClassTransfer(getUserVisit(), glAccountClass));
                        sendEventUsingNames(glAccountClass.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());

                        result.setEdit(edit);
                        edit.setGlAccountClassName(glAccountClassDetail.getGlAccountClassName());
                        edit.setParentGlAccountClassName(parentGlAccountClass == null? null: parentGlAccountClass.getLastDetail().getGlAccountClassName());
                        edit.setIsDefault(glAccountClassDetail.getIsDefault().toString());
                        edit.setSortOrder(glAccountClassDetail.getSortOrder().toString());

                        if(glAccountClassDescription != null) {
                            edit.setDescription(glAccountClassDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(glAccountClass));
                } else { // EditMode.ABANDON
                    unlockEntity(glAccountClass);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlAccountClassName.name(), glAccountClassName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String glAccountClassName = spec.getGlAccountClassName();
            GlAccountClass glAccountClass = accountingControl.getGlAccountClassByNameForUpdate(glAccountClassName);
            
            if(glAccountClass != null) {
                glAccountClassName = edit.getGlAccountClassName();
                GlAccountClass duplicateGlAccountClass = accountingControl.getGlAccountClassByName(glAccountClassName);
                
                if(duplicateGlAccountClass == null || glAccountClass.equals(duplicateGlAccountClass)) {
                    String parentGlAccountClassName = edit.getParentGlAccountClassName();
                    GlAccountClass parentGlAccountClass = null;
                    
                    if(parentGlAccountClassName != null) {
                        parentGlAccountClass = accountingControl.getGlAccountClassByName(parentGlAccountClassName);
                    }
                    
                    if(parentGlAccountClassName == null || parentGlAccountClass != null) {
                        if(accountingControl.isParentGlAccountClassSafe(glAccountClass, parentGlAccountClass)) {
                            if(lockEntityForUpdate(glAccountClass)) {
                                try {
                                    PartyPK partyPK = getPartyPK();
                                    GlAccountClassDetailValue glAccountClassDetailValue = accountingControl.getGlAccountClassDetailValueForUpdate(glAccountClass);
                                    GlAccountClassDescription glAccountClassDescription = accountingControl.getGlAccountClassDescriptionForUpdate(glAccountClass, getPreferredLanguage());
                                    String description = edit.getDescription();
                                    
                                    glAccountClassDetailValue.setGlAccountClassName(edit.getGlAccountClassName());
                                    glAccountClassDetailValue.setParentGlAccountClassPK(parentGlAccountClass == null? null: parentGlAccountClass.getPrimaryKey());
                                    glAccountClassDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    glAccountClassDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    accountingControl.updateGlAccountClassFromValue(glAccountClassDetailValue, partyPK);
                                    
                                    if(glAccountClassDescription == null && description != null) {
                                        accountingControl.createGlAccountClassDescription(glAccountClass, getPreferredLanguage(), description, partyPK);
                                    } else if(glAccountClassDescription != null && description == null) {
                                        accountingControl.deleteGlAccountClassDescription(glAccountClassDescription, partyPK);
                                    } else if(glAccountClassDescription != null && description != null) {
                                        GlAccountClassDescriptionValue glAccountClassDescriptionValue = accountingControl.getGlAccountClassDescriptionValue(glAccountClassDescription);
                                        
                                        glAccountClassDescriptionValue.setDescription(description);
                                        accountingControl.updateGlAccountClassDescriptionFromValue(glAccountClassDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(glAccountClass);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidParentGlAccountClass.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownParentGlAccountClassName.name(), parentGlAccountClassName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateGlAccountClassName.name(), glAccountClassName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGlAccountClassName.name(), glAccountClassName);
            }
            
            if(hasExecutionErrors()) {
                result.setGlAccountClass(accountingControl.getGlAccountClassTransfer(getUserVisit(), glAccountClass));
                result.setEntityLock(getEntityLockTransfer(glAccountClass));
            }
        }
        
        return result;
    }
    
}
