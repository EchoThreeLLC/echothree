// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.EntityListItemDefaultTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;


public class EntityListItemDefaultTransferCache
        extends BaseCoreTransferCache<EntityListItemDefault, EntityListItemDefaultTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of EntityListItemDefaultTransferCache */
    public EntityListItemDefaultTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EntityListItemDefaultTransfer getEntityListItemDefaultTransfer(EntityListItemDefault entityListItemDefault) {
        var entityListItemDefaultTransfer = get(entityListItemDefault);
        
        if(entityListItemDefaultTransfer == null) {
            var entityAttribute = coreControl.getEntityAttributeTransfer(userVisit, entityListItemDefault.getEntityAttribute(), null);
            var entityListItem = coreControl.getEntityListItemTransfer(userVisit, entityListItemDefault.getEntityListItem(), null);
            
            entityListItemDefaultTransfer = new EntityListItemDefaultTransfer(entityAttribute, entityListItem);
            put(entityListItemDefault, entityListItemDefaultTransfer);
        }
        
        return entityListItemDefaultTransfer;
    }
    
}
