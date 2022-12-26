// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

public class EntityNameAttributeTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private String nameAttribute;
    private EntityInstanceTransfer entityInstance;
    
    /** Creates a new instance of EntityNameAttributeTransfer */
    public EntityNameAttributeTransfer(EntityAttributeTransfer entityAttribute, String nameAttribute,
            EntityInstanceTransfer entityInstance) {
        this.entityAttribute = entityAttribute;
        this.nameAttribute = nameAttribute;
        this.entityInstance = entityInstance;
    }
    
    public EntityAttributeTransfer getEntityAttribute() {
        return entityAttribute;
    }
    
    public void setEntityAttribute(EntityAttributeTransfer entityAttribute) {
        this.entityAttribute = entityAttribute;
    }
    
    public String getNameAttribute() {
        return nameAttribute;
    }
    
    public void setNameAttribute(String nameAttribute) {
        this.nameAttribute = nameAttribute;
    }
    
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }
    
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }
    
}
