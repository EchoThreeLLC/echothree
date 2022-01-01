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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityListItemDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEntityListItemDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityListItemDescriptionResult;
import com.echothree.control.user.core.common.spec.EntityListItemDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemDescription;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.value.EntityListItemDescriptionValue;
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

public class EditEntityListItemDescriptionCommand
        extends BaseAbstractEditCommand<EntityListItemDescriptionSpec, EntityListItemDescriptionEdit, EditEntityListItemDescriptionResult, EntityListItemDescription, EntityListItem> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityListItem.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityListItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditEntityListItemDescriptionCommand */
    public EditEntityListItemDescriptionCommand(UserVisitPK userVisitPK, EditEntityListItemDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityListItemDescriptionResult getResult() {
        return CoreResultFactory.getEditEntityListItemDescriptionResult();
    }

    @Override
    public EntityListItemDescriptionEdit getEdit() {
        return CoreEditFactory.getEntityListItemDescriptionEdit();
    }

    @Override
    public EntityListItemDescription getEntity(EditEntityListItemDescriptionResult result) {
        var coreControl = getCoreControl();
        EntityListItemDescription entityListItemDescription = null;
        String componentVendorName = spec.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);

        if(componentVendor != null) {
            String entityTypeName = spec.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                String entityAttributeName = spec.getEntityAttributeName();
                EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                if(entityAttribute != null) {
                    String entityListItemName = spec.getEntityListItemName();
                    EntityListItem entityListItem = coreControl.getEntityListItemByName(entityAttribute, entityListItemName);

                    if(entityListItem != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        String languageIsoName = spec.getLanguageIsoName();
                        Language language = partyControl.getLanguageByIsoName(languageIsoName);

                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                entityListItemDescription = coreControl.getEntityListItemDescription(entityListItem, language);
                            } else { // EditMode.UPDATE
                                entityListItemDescription = coreControl.getEntityListItemDescriptionForUpdate(entityListItem, language);
                            }

                            if(entityListItemDescription == null) {
                                addExecutionError(ExecutionErrors.UnknownEntityListItemDescription.name(), componentVendorName, entityTypeName, entityAttributeName,
                                        entityListItemName, languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityListItemName.name(), componentVendorName, entityTypeName, entityAttributeName, entityListItemName);
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

        return entityListItemDescription;
    }

    @Override
    public EntityListItem getLockEntity(EntityListItemDescription entityListItemDescription) {
        return entityListItemDescription.getEntityListItem();
    }

    @Override
    public void fillInResult(EditEntityListItemDescriptionResult result, EntityListItemDescription entityListItemDescription) {
        var coreControl = getCoreControl();

        result.setEntityListItemDescription(coreControl.getEntityListItemDescriptionTransfer(getUserVisit(), entityListItemDescription, null));
    }

    @Override
    public void doLock(EntityListItemDescriptionEdit edit, EntityListItemDescription entityListItemDescription) {
        edit.setDescription(entityListItemDescription.getDescription());
    }

    @Override
    public void doUpdate(EntityListItemDescription entityListItemDescription) {
        var coreControl = getCoreControl();
        EntityListItemDescriptionValue entityListItemDescriptionValue = coreControl.getEntityListItemDescriptionValue(entityListItemDescription);
        
        entityListItemDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateEntityListItemDescriptionFromValue(entityListItemDescriptionValue, getPartyPK());
    }
    
}
