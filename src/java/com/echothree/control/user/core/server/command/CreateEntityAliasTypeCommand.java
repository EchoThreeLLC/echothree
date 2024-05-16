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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CreateEntityAliasTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.logic.EntityAliasTypeLogic;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;

public class CreateEntityAliasTypeCommand
        extends BaseSimpleCommand<CreateEntityAliasTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAliasType.name(), SecurityRoles.Create.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );

    }
    
    /** Creates a new instance of CreateEntityAliasTypeCommand */
    public CreateEntityAliasTypeCommand(UserVisitPK userVisitPK, CreateEntityAliasTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getCreateEntityAliasTypeResult();
        EntityAliasType entityAliasType = null;
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByUniversalSpec(this, form);

        if(!hasExecutionErrors()) {
            var partyPK = getPartyPK();
            var entityAliasTypeName = form.getEntityAliasTypeName();
            var validationPattern = form.getValidationPattern();
            var isDefault = Boolean.valueOf(form.getIsDefault());
            var sortOrder = Integer.valueOf(form.getSortOrder());
            var description = form.getDescription();

            entityAliasType = EntityAliasTypeLogic.getInstance().createEntityAliasType(this, entityType,
                    entityAliasTypeName, validationPattern, isDefault, sortOrder, partyPK,
                    getPreferredLanguage(), description);
        }

        if(entityAliasType != null) {
            var basePK = entityAliasType.getPrimaryKey();

            result.setEntityAliasTypeName(entityAliasType.getLastDetail().getEntityAliasTypeName());
            result.setEntityRef(basePK.getEntityRef());
        }
        
        return result;
    }

}
