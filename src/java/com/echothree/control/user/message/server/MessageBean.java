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

package com.echothree.control.user.message.server;

import com.echothree.control.user.message.remote.MessageRemote;
import com.echothree.control.user.message.remote.form.*;
import com.echothree.control.user.message.server.command.*;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.remote.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class MessageBean
        extends MessageFormsImpl
        implements MessageRemote, MessageLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "MessageBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Message Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createMessageType(UserVisitPK userVisitPK, CreateMessageTypeForm form) {
        return new CreateMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMessageType(UserVisitPK userVisitPK, GetMessageTypeForm form) {
        return new GetMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMessageTypes(UserVisitPK userVisitPK, GetMessageTypesForm form) {
        return new GetMessageTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editMessageType(UserVisitPK userVisitPK, EditMessageTypeForm form) {
        return new EditMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteMessageType(UserVisitPK userVisitPK, DeleteMessageTypeForm form) {
        return new DeleteMessageTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Message Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createMessageTypeDescription(UserVisitPK userVisitPK, CreateMessageTypeDescriptionForm form) {
        return new CreateMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMessageTypeDescriptions(UserVisitPK userVisitPK, GetMessageTypeDescriptionsForm form) {
        return new GetMessageTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editMessageTypeDescription(UserVisitPK userVisitPK, EditMessageTypeDescriptionForm form) {
        return new EditMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteMessageTypeDescription(UserVisitPK userVisitPK, DeleteMessageTypeDescriptionForm form) {
        return new DeleteMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Messages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createMessage(UserVisitPK userVisitPK, CreateMessageForm form) {
        return new CreateMessageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMessage(UserVisitPK userVisitPK, GetMessageForm form) {
        return new GetMessageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMessages(UserVisitPK userVisitPK, GetMessagesForm form) {
        return new GetMessagesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteMessage(UserVisitPK userVisitPK, DeleteMessageForm form) {
        return new DeleteMessageCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Message Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createMessageDescription(UserVisitPK userVisitPK, CreateMessageDescriptionForm form) {
        return new CreateMessageDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMessageDescriptions(UserVisitPK userVisitPK, GetMessageDescriptionsForm form) {
        return new GetMessageDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editMessageDescription(UserVisitPK userVisitPK, EditMessageDescriptionForm form) {
        return new EditMessageDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteMessageDescription(UserVisitPK userVisitPK, DeleteMessageDescriptionForm form) {
        return new DeleteMessageDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Messages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityMessage(UserVisitPK userVisitPK, CreateEntityMessageForm form) {
        return new CreateEntityMessageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityMessage(UserVisitPK userVisitPK, GetEntityMessageForm form) {
        return new GetEntityMessageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityMessages(UserVisitPK userVisitPK, GetEntityMessagesForm form) {
        return new GetEntityMessagesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityMessage(UserVisitPK userVisitPK, DeleteEntityMessageForm form) {
        return new DeleteEntityMessageCommand(userVisitPK, form).run();
    }
    
}
