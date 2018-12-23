// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityAttributeGroupEdit;
import com.echothree.control.user.core.common.form.EditEntityAttributeGroupForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityAttributeGroupResult;
import com.echothree.control.user.core.common.spec.EntityAttributeGroupSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDescription;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDetail;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityAttributeGroupCommand
        extends BaseAbstractEditCommand<EntityAttributeGroupSpec, EntityAttributeGroupEdit, EditEntityAttributeGroupResult, EntityAttributeGroup, EntityAttributeGroup> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttributeGroup.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityAttributeGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityAttributeGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditEntityAttributeGroupCommand */
    public EditEntityAttributeGroupCommand(UserVisitPK userVisitPK, EditEntityAttributeGroupForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityAttributeGroupResult getResult() {
        return CoreResultFactory.getEditEntityAttributeGroupResult();
    }
    
    @Override
    public EntityAttributeGroupEdit getEdit() {
        return CoreEditFactory.getEntityAttributeGroupEdit();
    }
    
    @Override
    public EntityAttributeGroup getEntity(EditEntityAttributeGroupResult result) {
        CoreControl coreControl = getCoreControl();
        EntityAttributeGroup entityAttributeGroup = null;
        String entityAttributeGroupName = spec.getEntityAttributeGroupName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            entityAttributeGroup = coreControl.getEntityAttributeGroupByName(entityAttributeGroupName);

            if(entityAttributeGroup == null) {
                addExecutionError(ExecutionErrors.UnknownEntityAttributeGroupName.name(), entityAttributeGroupName);
            }
        } else { // EditMode.UPDATE
            entityAttributeGroup = coreControl.getEntityAttributeGroupByNameForUpdate(entityAttributeGroupName);
        }

        return entityAttributeGroup;
    }
    
    @Override
    public EntityAttributeGroup getLockEntity(EntityAttributeGroup entityAttributeGroup) {
        return entityAttributeGroup;
    }
    
    @Override
    public void fillInResult(EditEntityAttributeGroupResult result, EntityAttributeGroup entityAttributeGroup) {
        CoreControl coreControl = getCoreControl();
        
        result.setEntityAttributeGroup(coreControl.getEntityAttributeGroupTransfer(getUserVisit(), entityAttributeGroup, null));
    }
    
    @Override
    public void doLock(EntityAttributeGroupEdit edit, EntityAttributeGroup entityAttributeGroup) {
        CoreControl coreControl = getCoreControl();
        EntityAttributeGroupDescription entityAttributeGroupDescription = coreControl.getEntityAttributeGroupDescription(entityAttributeGroup, getPreferredLanguage());
        EntityAttributeGroupDetail entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();

        edit.setEntityAttributeGroupName(entityAttributeGroupDetail.getEntityAttributeGroupName());
        edit.setIsDefault(entityAttributeGroupDetail.getIsDefault().toString());
        edit.setSortOrder(entityAttributeGroupDetail.getSortOrder().toString());

        if(entityAttributeGroupDescription != null) {
            edit.setDescription(entityAttributeGroupDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(EntityAttributeGroup entityAttributeGroup) {
        CoreControl coreControl = getCoreControl();
        String entityAttributeGroupName = edit.getEntityAttributeGroupName();
        EntityAttributeGroup duplicateEntityAttributeGroup = coreControl.getEntityAttributeGroupByName(entityAttributeGroupName);

        if(duplicateEntityAttributeGroup != null && !entityAttributeGroup.equals(duplicateEntityAttributeGroup)) {
            addExecutionError(ExecutionErrors.DuplicateEntityAttributeGroupName.name(), entityAttributeGroupName);
        }
    }
    
    @Override
    public void doUpdate(EntityAttributeGroup entityAttributeGroup) {
        CoreControl coreControl = getCoreControl();
        PartyPK partyPK = getPartyPK();
        EntityAttributeGroupDetailValue entityAttributeGroupDetailValue = coreControl.getEntityAttributeGroupDetailValueForUpdate(entityAttributeGroup);
        EntityAttributeGroupDescription entityAttributeGroupDescription = coreControl.getEntityAttributeGroupDescriptionForUpdate(entityAttributeGroup, getPreferredLanguage());
        String description = edit.getDescription();

        entityAttributeGroupDetailValue.setEntityAttributeGroupName(edit.getEntityAttributeGroupName());
        entityAttributeGroupDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        entityAttributeGroupDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, partyPK);

        if(entityAttributeGroupDescription == null && description != null) {
            coreControl.createEntityAttributeGroupDescription(entityAttributeGroup, getPreferredLanguage(), description, partyPK);
        } else if(entityAttributeGroupDescription != null && description == null) {
            coreControl.deleteEntityAttributeGroupDescription(entityAttributeGroupDescription, partyPK);
        } else if(entityAttributeGroupDescription != null && description != null) {
            EntityAttributeGroupDescriptionValue entityAttributeGroupDescriptionValue = coreControl.getEntityAttributeGroupDescriptionValue(entityAttributeGroupDescription);

            entityAttributeGroupDescriptionValue.setDescription(description);
            coreControl.updateEntityAttributeGroupDescriptionFromValue(entityAttributeGroupDescriptionValue, partyPK);
        }
    }
    
}
