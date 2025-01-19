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

import com.echothree.model.control.core.common.transfer.EntityLongDefaultTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityLongDefault;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityLongDefaultTransferCache
        extends BaseCoreTransferCache<EntityLongDefault, EntityLongDefaultTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of EntityLongDefaultTransferCache */
    public EntityLongDefaultTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EntityLongDefaultTransfer getEntityLongDefaultTransfer(EntityLongDefault entityLongDefault) {
        var entityLongDefaultTransfer = get(entityLongDefault);
        
        if(entityLongDefaultTransfer == null) {
            var entityAttribute = coreControl.getEntityAttributeTransfer(userVisit, entityLongDefault.getEntityAttribute(), null);
            var longAttribute = entityLongDefault.getLongAttribute();
            
            entityLongDefaultTransfer = new EntityLongDefaultTransfer(entityAttribute, longAttribute);
            put(entityLongDefault, entityLongDefaultTransfer);
        }
        
        return entityLongDefaultTransfer;
    }
    
}
