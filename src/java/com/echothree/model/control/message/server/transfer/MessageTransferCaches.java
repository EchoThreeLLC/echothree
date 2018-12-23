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

package com.echothree.model.control.message.server.transfer;

import com.echothree.model.control.message.server.MessageControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class MessageTransferCaches
        extends BaseTransferCaches {
    
    protected MessageControl messageControl;
    
    protected MessageTypeTransferCache messageTypeTransferCache;
    protected MessageTypeDescriptionTransferCache messageTypeDescriptionTransferCache;
    protected MessageTransferCache messageTransferCache;
    protected MessageStringTransferCache messageStringTransferCache;
    protected MessageBlobTransferCache messageBlobTransferCache;
    protected MessageClobTransferCache messageClobTransferCache;
    protected MessageDescriptionTransferCache messageDescriptionTransferCache;
    protected EntityMessageTransferCache entityMessageTransferCache;
    
    /** Creates a new instance of MessageTransferCaches */
    public MessageTransferCaches(UserVisit userVisit, MessageControl messageControl) {
        super(userVisit);
        
        this.messageControl = messageControl;
    }
    
    public MessageTypeTransferCache getMessageTypeTransferCache() {
        if(messageTypeTransferCache == null)
            messageTypeTransferCache = new MessageTypeTransferCache(userVisit, messageControl);
        
        return messageTypeTransferCache;
    }
    
    public MessageTypeDescriptionTransferCache getMessageTypeDescriptionTransferCache() {
        if(messageTypeDescriptionTransferCache == null)
            messageTypeDescriptionTransferCache = new MessageTypeDescriptionTransferCache(userVisit, messageControl);
        
        return messageTypeDescriptionTransferCache;
    }
    
    public MessageTransferCache getMessageTransferCache() {
        if(messageTransferCache == null)
            messageTransferCache = new MessageTransferCache(userVisit, messageControl);
        
        return messageTransferCache;
    }
    
    public MessageStringTransferCache getMessageStringTransferCache() {
        if(messageStringTransferCache == null)
            messageStringTransferCache = new MessageStringTransferCache(userVisit, messageControl);
        
        return messageStringTransferCache;
    }
    
    public MessageBlobTransferCache getMessageBlobTransferCache() {
        if(messageBlobTransferCache == null)
            messageBlobTransferCache = new MessageBlobTransferCache(userVisit, messageControl);
        
        return messageBlobTransferCache;
    }
    
    public MessageClobTransferCache getMessageClobTransferCache() {
        if(messageClobTransferCache == null)
            messageClobTransferCache = new MessageClobTransferCache(userVisit, messageControl);
        
        return messageClobTransferCache;
    }
    
    public MessageDescriptionTransferCache getMessageDescriptionTransferCache() {
        if(messageDescriptionTransferCache == null)
            messageDescriptionTransferCache = new MessageDescriptionTransferCache(userVisit, messageControl);
        
        return messageDescriptionTransferCache;
    }
    
    public EntityMessageTransferCache getEntityMessageTransferCache() {
        if(entityMessageTransferCache == null)
            entityMessageTransferCache = new EntityMessageTransferCache(userVisit, messageControl);
        
        return entityMessageTransferCache;
    }
    
}
