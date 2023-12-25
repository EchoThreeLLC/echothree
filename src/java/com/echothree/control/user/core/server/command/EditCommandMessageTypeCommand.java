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

import com.echothree.control.user.core.common.edit.CommandMessageTypeEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditCommandMessageTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditCommandMessageTypeResult;
import com.echothree.control.user.core.common.spec.CommandMessageTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.core.server.entity.CommandMessageTypeDescription;
import com.echothree.model.data.core.server.entity.CommandMessageTypeDetail;
import com.echothree.model.data.core.server.value.CommandMessageTypeDescriptionValue;
import com.echothree.model.data.core.server.value.CommandMessageTypeDetailValue;
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

public class EditCommandMessageTypeCommand
        extends BaseAbstractEditCommand<CommandMessageTypeSpec, CommandMessageTypeEdit, EditCommandMessageTypeResult, CommandMessageType, CommandMessageType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CommandMessageType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandMessageTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandMessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCommandMessageTypeCommand */
    public EditCommandMessageTypeCommand(UserVisitPK userVisitPK, EditCommandMessageTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCommandMessageTypeResult getResult() {
        return CoreResultFactory.getEditCommandMessageTypeResult();
    }

    @Override
    public CommandMessageTypeEdit getEdit() {
        return CoreEditFactory.getCommandMessageTypeEdit();
    }

    @Override
    public CommandMessageType getEntity(EditCommandMessageTypeResult result) {
        var coreControl = getCoreControl();
        CommandMessageType commandMessageType = null;
        String commandMessageTypeName = spec.getCommandMessageTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            commandMessageType = coreControl.getCommandMessageTypeByName(commandMessageTypeName);
        } else { // EditMode.UPDATE
            commandMessageType = coreControl.getCommandMessageTypeByNameForUpdate(commandMessageTypeName);
        }

        if(commandMessageType != null) {
            result.setCommandMessageType(coreControl.getCommandMessageTypeTransfer(getUserVisit(), commandMessageType));
        } else {
            addExecutionError(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName);
        }

        return commandMessageType;
    }

    @Override
    public CommandMessageType getLockEntity(CommandMessageType commandMessageType) {
        return commandMessageType;
    }

    @Override
    public void fillInResult(EditCommandMessageTypeResult result, CommandMessageType commandMessageType) {
        var coreControl = getCoreControl();

        result.setCommandMessageType(coreControl.getCommandMessageTypeTransfer(getUserVisit(), commandMessageType));
    }

    @Override
    public void doLock(CommandMessageTypeEdit edit, CommandMessageType commandMessageType) {
        var coreControl = getCoreControl();
        CommandMessageTypeDescription commandMessageTypeDescription = coreControl.getCommandMessageTypeDescription(commandMessageType, getPreferredLanguage());
        CommandMessageTypeDetail commandMessageTypeDetail = commandMessageType.getLastDetail();

        edit.setCommandMessageTypeName(commandMessageTypeDetail.getCommandMessageTypeName());
        edit.setIsDefault(commandMessageTypeDetail.getIsDefault().toString());
        edit.setSortOrder(commandMessageTypeDetail.getSortOrder().toString());

        if(commandMessageTypeDescription != null) {
            edit.setDescription(commandMessageTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CommandMessageType commandMessageType) {
        var coreControl = getCoreControl();
        String commandMessageTypeName = edit.getCommandMessageTypeName();
        CommandMessageType duplicateCommandMessageType = coreControl.getCommandMessageTypeByName(commandMessageTypeName);

        if(duplicateCommandMessageType != null && !commandMessageType.equals(duplicateCommandMessageType)) {
            addExecutionError(ExecutionErrors.DuplicateCommandMessageTypeName.name(), commandMessageTypeName);
        }
    }

    @Override
    public void doUpdate(CommandMessageType commandMessageType) {
        var coreControl = getCoreControl();
        var partyPK = getPartyPK();
        CommandMessageTypeDetailValue commandMessageTypeDetailValue = coreControl.getCommandMessageTypeDetailValueForUpdate(commandMessageType);
        CommandMessageTypeDescription commandMessageTypeDescription = coreControl.getCommandMessageTypeDescriptionForUpdate(commandMessageType, getPreferredLanguage());
        String description = edit.getDescription();

        commandMessageTypeDetailValue.setCommandMessageTypeName(edit.getCommandMessageTypeName());
        commandMessageTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        commandMessageTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        coreControl.updateCommandMessageTypeFromValue(commandMessageTypeDetailValue, partyPK);

        if(commandMessageTypeDescription == null && description != null) {
            coreControl.createCommandMessageTypeDescription(commandMessageType, getPreferredLanguage(), description, partyPK);
        } else {
            if(commandMessageTypeDescription != null && description == null) {
                coreControl.deleteCommandMessageTypeDescription(commandMessageTypeDescription, partyPK);
            } else {
                if(commandMessageTypeDescription != null && description != null) {
                    CommandMessageTypeDescriptionValue commandMessageTypeDescriptionValue = coreControl.getCommandMessageTypeDescriptionValue(commandMessageTypeDescription);

                    commandMessageTypeDescriptionValue.setDescription(description);
                    coreControl.updateCommandMessageTypeDescriptionFromValue(commandMessageTypeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
