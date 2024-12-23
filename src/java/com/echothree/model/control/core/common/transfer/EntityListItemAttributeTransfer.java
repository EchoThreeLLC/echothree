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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class EntityListItemAttributeTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private EntityInstanceTransfer entityInstance;
    private EntityListItemTransfer entityListItem;
    
    /** Creates a new instance of EntityListItemAttributeTransfer */
    public EntityListItemAttributeTransfer(EntityAttributeTransfer entityAttribute, EntityInstanceTransfer entityInstance,
            EntityListItemTransfer entityListItem) {
        this.entityAttribute = entityAttribute;
        this.entityInstance = entityInstance;
        this.entityListItem = entityListItem;
    }
    
    public EntityAttributeTransfer getEntityAttribute() {
        return entityAttribute;
    }
    
    public void setEntityAttribute(EntityAttributeTransfer entityAttribute) {
        this.entityAttribute = entityAttribute;
    }
    
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }
    
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }
    
    public EntityListItemTransfer getEntityListItem() {
        return entityListItem;
    }
    
    public void setEntityListItem(EntityListItemTransfer entityListItem) {
        this.entityListItem = entityListItem;
    }
    
}
