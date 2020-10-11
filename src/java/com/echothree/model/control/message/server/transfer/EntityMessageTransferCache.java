// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.message.common.transfer.EntityMessageTransfer;
import com.echothree.model.control.message.common.transfer.MessageTransfer;
import com.echothree.model.control.message.server.MessageControl;
import com.echothree.model.data.message.server.entity.EntityMessage;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityMessageTransferCache
        extends BaseMessageTransferCache<EntityMessage, EntityMessageTransfer> {
    
    CoreControl coreControl;
    
    /** Creates a new instance of EntityMessageTransferCache */
    public EntityMessageTransferCache(UserVisit userVisit, MessageControl messageControl) {
        super(userVisit, messageControl);
        
        coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    }
    
    public EntityMessageTransfer getEntityMessageTransfer(EntityMessage entityMessage) {
        EntityMessageTransfer entityMessageTransfer = get(entityMessage);
        
        if(entityMessageTransfer == null) {
            EntityInstanceTransfer entityInstance = coreControl.getEntityInstanceTransfer(userVisit, entityMessage, false, false, false, false, false);
            MessageTransfer message = messageControl.getMessageTransfer(userVisit, entityMessage.getMessage());
            
            entityMessageTransfer = new EntityMessageTransfer(entityInstance, message);
            put(entityMessage, entityMessageTransfer);
        }
        
        return entityMessageTransfer;
    }
    
}
