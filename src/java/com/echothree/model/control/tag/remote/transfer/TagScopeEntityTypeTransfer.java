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

package com.echothree.model.control.tag.remote.transfer;

import com.echothree.model.control.core.remote.transfer.EntityTypeTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class TagScopeEntityTypeTransfer
        extends BaseTransfer {
    
    private TagScopeTransfer tagScope;
    private EntityTypeTransfer entityType;
    
    /** Creates a new instance of TagScopeEntityTypeTransfer */
    public TagScopeEntityTypeTransfer(TagScopeTransfer tagScope, EntityTypeTransfer entityType) {
        this.tagScope = tagScope;
        this.entityType = entityType;
    }
    
    public TagScopeTransfer getTagScope() {
        return tagScope;
    }
    
    public void setTagScope(TagScopeTransfer tagScope) {
        this.tagScope = tagScope;
    }
    
    public EntityTypeTransfer getEntityType() {
        return entityType;
    }
    
    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }
    
}
