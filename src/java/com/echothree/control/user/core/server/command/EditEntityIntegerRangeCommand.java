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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.remote.edit.CoreEditFactory;
import com.echothree.control.user.core.remote.edit.EntityIntegerRangeEdit;
import com.echothree.control.user.core.remote.form.EditEntityIntegerRangeForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.EditEntityIntegerRangeResult;
import com.echothree.control.user.core.remote.spec.EntityIntegerRangeSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDescription;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDetail;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.value.EntityIntegerRangeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityIntegerRangeDetailValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditEntityIntegerRangeCommand
        extends BaseAbstractEditCommand<EntityIntegerRangeSpec, EntityIntegerRangeEdit, EditEntityIntegerRangeResult, EntityIntegerRange, EntityIntegerRange> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityIntegerRange.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityIntegerRangeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityIntegerRangeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MinimumIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("MaximumIntegerValue", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditEntityIntegerRangeCommand */
    public EditEntityIntegerRangeCommand(UserVisitPK userVisitPK, EditEntityIntegerRangeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
   public EditEntityIntegerRangeResult getResult() {
        return CoreResultFactory.getEditEntityIntegerRangeResult();
    }

    @Override
    public EntityIntegerRangeEdit getEdit() {
        return CoreEditFactory.getEntityIntegerRangeEdit();
    }

    EntityAttribute entityAttribute = null;
    
    @Override
    public EntityIntegerRange getEntity(EditEntityIntegerRangeResult result) {
        CoreControl coreControl = getCoreControl();
        EntityIntegerRange entityIntegerRange = null;
        String componentVendorName = spec.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            String entityTypeName = spec.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                String entityAttributeName = spec.getEntityAttributeName();
                
                entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    String entityIntegerRangeName = spec.getEntityIntegerRangeName();

                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        entityIntegerRange = coreControl.getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName);
                    } else { // EditMode.UPDATE
                        entityIntegerRange = coreControl.getEntityIntegerRangeByNameForUpdate(entityAttribute, entityIntegerRangeName);
                    }

                    if(entityIntegerRange == null) {
                        addExecutionError(ExecutionErrors.UnknownEntityIntegerRangeName.name(), componentVendorName, entityTypeName, entityAttributeName, entityIntegerRangeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), componentVendorName, entityTypeName, entityAttributeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return entityIntegerRange;
    }

    @Override
    public EntityIntegerRange getLockEntity(EntityIntegerRange entityIntegerRange) {
        return entityIntegerRange;
    }

    @Override
    public void fillInResult(EditEntityIntegerRangeResult result, EntityIntegerRange entityIntegerRange) {
        CoreControl coreControl = getCoreControl();

        result.setEntityIntegerRange(coreControl.getEntityIntegerRangeTransfer(getUserVisit(), entityIntegerRange, null));
    }

    @Override
    public void doLock(EntityIntegerRangeEdit edit, EntityIntegerRange entityIntegerRange) {
        CoreControl coreControl = getCoreControl();
        EntityIntegerRangeDescription entityIntegerRangeDescription = coreControl.getEntityIntegerRangeDescription(entityIntegerRange, getPreferredLanguage());
        EntityIntegerRangeDetail entityIntegerRangeDetail = entityIntegerRange.getLastDetail();
        Integer minimumIntegerValue = entityIntegerRangeDetail.getMinimumIntegerValue();
        Integer maximumIntegerValue = entityIntegerRangeDetail.getMaximumIntegerValue();

        edit.setEntityIntegerRangeName(entityIntegerRangeDetail.getEntityIntegerRangeName());
        edit.setMinimumIntegerValue(minimumIntegerValue == null ? null : minimumIntegerValue.toString());
        edit.setMaximumIntegerValue(maximumIntegerValue == null ? null : maximumIntegerValue.toString());
        edit.setIsDefault(entityIntegerRangeDetail.getIsDefault().toString());
        edit.setSortOrder(entityIntegerRangeDetail.getSortOrder().toString());

        if(entityIntegerRangeDescription != null) {
            edit.setDescription(entityIntegerRangeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(EntityIntegerRange entityIntegerRange) {
        String strMinimumIntegerValue = edit.getMinimumIntegerValue();
        Integer minimumIntegerValue = strMinimumIntegerValue == null ? null : Integer.valueOf(strMinimumIntegerValue);
        String strMaximumIntegerValue = edit.getMaximumIntegerValue();
        Integer maximumIntegerValue = strMaximumIntegerValue == null ? null : Integer.valueOf(strMaximumIntegerValue);

        if(minimumIntegerValue == null || maximumIntegerValue == null || maximumIntegerValue >= minimumIntegerValue) {
            CoreControl coreControl = getCoreControl();
            String entityIntegerRangeName = edit.getEntityIntegerRangeName();
            EntityIntegerRange duplicateEntityIntegerRange = coreControl.getEntityIntegerRangeByName(entityAttribute, entityIntegerRangeName);

            if(duplicateEntityIntegerRange != null && !entityIntegerRange.equals(duplicateEntityIntegerRange)) {
                addExecutionError(ExecutionErrors.DuplicateEntityIntegerRangeName.name(), entityIntegerRangeName);
            }
        } else {
            addExecutionError(ExecutionErrors.MinimumValueGreaterThanMaximumValue.name(), strMinimumIntegerValue, strMaximumIntegerValue);
        }
    }

    @Override
    public void doUpdate(EntityIntegerRange entityIntegerRange) {
        CoreControl coreControl = getCoreControl();
        PartyPK partyPK = getPartyPK();
        EntityIntegerRangeDetailValue entityIntegerRangeDetailValue = coreControl.getEntityIntegerRangeDetailValueForUpdate(entityIntegerRange);
        EntityIntegerRangeDescription entityIntegerRangeDescription = coreControl.getEntityIntegerRangeDescriptionForUpdate(entityIntegerRange, getPreferredLanguage());
        String strMinimumIntegerValue = edit.getMinimumIntegerValue();
        String strMaximumIntegerValue = edit.getMaximumIntegerValue();
        String description = edit.getDescription();

        entityIntegerRangeDetailValue.setEntityIntegerRangeName(edit.getEntityIntegerRangeName());
        entityIntegerRangeDetailValue.setMinimumIntegerValue(strMinimumIntegerValue == null ? null : Integer.valueOf(strMinimumIntegerValue));
        entityIntegerRangeDetailValue.setMaximumIntegerValue(strMaximumIntegerValue == null ? null : Integer.valueOf(strMaximumIntegerValue));
        entityIntegerRangeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        entityIntegerRangeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateEntityIntegerRangeFromValue(entityIntegerRangeDetailValue, partyPK);

        if(entityIntegerRangeDescription == null && description != null) {
            coreControl.createEntityIntegerRangeDescription(entityIntegerRange, getPreferredLanguage(), description, partyPK);
        } else {
            if(entityIntegerRangeDescription != null && description == null) {
                coreControl.deleteEntityIntegerRangeDescription(entityIntegerRangeDescription, partyPK);
            } else {
                if(entityIntegerRangeDescription != null && description != null) {
                    EntityIntegerRangeDescriptionValue entityIntegerRangeDescriptionValue = coreControl.getEntityIntegerRangeDescriptionValue(entityIntegerRangeDescription);

                    entityIntegerRangeDescriptionValue.setDescription(description);
                    coreControl.updateEntityIntegerRangeDescriptionFromValue(entityIntegerRangeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
