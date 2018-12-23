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

import com.echothree.control.user.core.common.form.DeleteCommandMessageTranslationForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageTranslation;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteCommandMessageTranslationCommand
        extends BaseSimpleCommand<DeleteCommandMessageTranslationForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("CommandMessageTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("CommandMessageKey", FieldType.COMMAND_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteCommandMessageTranslationCommand */
    public DeleteCommandMessageTranslationCommand(UserVisitPK userVisitPK, DeleteCommandMessageTranslationForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        String commandMessageTypeName = form.getCommandMessageTypeName();
        CommandMessageType commandMessageType = coreControl.getCommandMessageTypeByName(commandMessageTypeName);
        
        if(commandMessageType != null) {
            String commandMessageKey = form.getCommandMessageKey();
            CommandMessage commandMessage = coreControl.getCommandMessageByKey(commandMessageType, commandMessageKey);
            
            if(commandMessage != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    CommandMessageTranslation commandMessageTranslation = coreControl.getCommandMessageTranslationForUpdate(commandMessage, language);
                    
                    if(commandMessageTranslation != null) {
                        coreControl.deleteCommandMessageTranslation(commandMessageTranslation, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommandMessageTranslation.name(), commandMessageTypeName, commandMessage, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCommandMessageKey.name(), commandMessageTypeName, commandMessage);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName);
        }
        
        return null;
    }
    
}
