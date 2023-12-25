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

package com.echothree.model.control.message.server.transfer;

import com.echothree.model.control.message.common.transfer.MessageTypeDescriptionTransfer;
import com.echothree.model.control.message.common.transfer.MessageTypeTransfer;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.message.server.entity.MessageTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class MessageTypeDescriptionTransferCache
        extends BaseMessageDescriptionTransferCache<MessageTypeDescription, MessageTypeDescriptionTransfer> {
    
    /** Creates a new instance of MessageTypeDescriptionTransferCache */
    public MessageTypeDescriptionTransferCache(UserVisit userVisit, MessageControl messageControl) {
        super(userVisit, messageControl);
    }
    
    public MessageTypeDescriptionTransfer getMessageTypeDescriptionTransfer(MessageTypeDescription messageTypeDescription) {
        MessageTypeDescriptionTransfer messageTypeDescriptionTransfer = get(messageTypeDescription);
        
        if(messageTypeDescriptionTransfer == null) {
            MessageTypeTransferCache messageTypeTransferCache = messageControl.getMessageTransferCaches(userVisit).getMessageTypeTransferCache();
            MessageTypeTransfer messageTypeTransfer = messageTypeTransferCache.getMessageTypeTransfer(messageTypeDescription.getMessageType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, messageTypeDescription.getLanguage());
            
            messageTypeDescriptionTransfer = new MessageTypeDescriptionTransfer(languageTransfer, messageTypeTransfer, messageTypeDescription.getDescription());
            put(messageTypeDescription, messageTypeDescriptionTransfer);
        }
        
        return messageTypeDescriptionTransfer;
    }
    
}
