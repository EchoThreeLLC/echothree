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

package com.echothree.control.user.message.server.command;

import com.echothree.control.user.message.common.form.CreateMessageTypeDescriptionForm;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.message.server.entity.MessageType;
import com.echothree.model.data.message.server.entity.MessageTypeDescription;
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

public class CreateMessageTypeDescriptionCommand
        extends BaseSimpleCommand<CreateMessageTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
            new FieldDefinition("MessageTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateMessageTypeDescriptionCommand */
    public CreateMessageTypeDescriptionCommand(UserVisitPK userVisitPK, CreateMessageTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        var componentVendorName = form.getComponentVendorName();
        var componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            var entityTypeName = form.getEntityTypeName();
            var entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var messageControl = Session.getModelController(MessageControl.class);
                var messageTypeName = form.getMessageTypeName();
                var messageType = messageControl.getMessageTypeByName(entityType, messageTypeName);
                
                if(messageType != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = form.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    if(language != null) {
                        var messageTypeDescription = messageControl.getMessageTypeDescription(messageType, language);
                        
                        if(messageTypeDescription == null) {
                            var description = form.getDescription();
                            
                            messageControl.createMessageTypeDescription(messageType, language, description, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateMessageTypeDescription.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownMessageTypeName.name(), messageTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }
        
        return null;
    }
    
}
