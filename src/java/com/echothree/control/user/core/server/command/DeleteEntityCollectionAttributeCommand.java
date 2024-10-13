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

import com.echothree.control.user.core.common.form.DeleteEntityCollectionAttributeForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;

public class DeleteEntityCollectionAttributeCommand
        extends BaseSimpleCommand<DeleteEntityCollectionAttributeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityRefAttribute", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("UuidAttribute", FieldType.UUID, false, null, null)
        );
    }
    
    /** Creates a new instance of DeleteEntityCollectionAttributeCommand */
    public DeleteEntityCollectionAttributeCommand(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

        if(!hasExecutionErrors()) {
            var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttribute(this, entityInstance, form, form,
                    EntityAttributeTypes.COLLECTION);

            if(!hasExecutionErrors()) {
                var entityInstanceAttribute = EntityAttributeLogic.getInstance().getEntityInstanceAttribute(this, form);

                if(!hasExecutionErrors()) {
                    var coreControl = getCoreControl();
                    var entityCollectionAttribute = coreControl.getEntityCollectionAttributeForUpdate(entityAttribute, entityInstance, entityInstanceAttribute);
                    
                    if(entityCollectionAttribute != null) {
                        coreControl.deleteEntityCollectionAttribute(entityCollectionAttribute, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityCollectionAttribute.name(),
                                EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                entityAttribute.getLastDetail().getEntityAttributeName(),
                                EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstanceAttribute));
                    }
                }
            }
        }
        
        return null;
    }
    
}
