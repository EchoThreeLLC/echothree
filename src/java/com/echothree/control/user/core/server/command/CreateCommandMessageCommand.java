// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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


import com.echothree.control.user.core.common.form.CreateCommandMessageForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCommandMessageCommand
        extends BaseSimpleCommand<CreateCommandMessageForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CommandMessage.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandMessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommandMessageKey", FieldType.KEY, true, null, null),
                new FieldDefinition("Translation", FieldType.STRING, false, 1L, 512L)
                ));
    }
    
    /** Creates a new instance of CreateCommandMessageCommand */
    public CreateCommandMessageCommand(UserVisitPK userVisitPK, CreateCommandMessageForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        String commandMessageTypeName = form.getCommandMessageTypeName();
        CommandMessageType commandMessageType = coreControl.getCommandMessageTypeByName(commandMessageTypeName);
        
        if(commandMessageType != null) {
            String commandMessageKey = form.getCommandMessageKey();
            CommandMessage commandMessage = coreControl.getCommandMessageByKey(commandMessageType, commandMessageKey);
            
            if(commandMessage == null) {
                var partyPK = getPartyPK();
                String translation = form.getTranslation();

                commandMessage = coreControl.createCommandMessage(commandMessageType, commandMessageKey, partyPK);

                if(translation != null) {
                    coreControl.createCommandMessageTranslation(commandMessage, getPreferredLanguage(), translation, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateCommandMessageKey.name(), commandMessageTypeName, commandMessageKey);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName);
        }
        
        return null;
    }
    
}
