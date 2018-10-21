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

package com.echothree.model.control.core.remote.transfer;

import com.echothree.util.remote.transfer.BaseTransfer;

public class CacheEntryDependencyTransfer
        extends BaseTransfer {
    
    private CacheEntryTransfer cacheEntry;
    private EntityInstanceTransfer entityInstance;

    /** Creates a new instance of CacheEntryTransfer */
    public CacheEntryDependencyTransfer(CacheEntryTransfer cacheEntry, EntityInstanceTransfer entityInstance) {
        this.cacheEntry = cacheEntry;
        this.entityInstance = entityInstance;
    }

    /**
     * @return the cacheEntry
     */
    public CacheEntryTransfer getCacheEntry() {
        return cacheEntry;
    }

    /**
     * @param cacheEntry the cacheEntry to set
     */
    public void setCacheEntry(CacheEntryTransfer cacheEntry) {
        this.cacheEntry = cacheEntry;
    }

    /**
     * @return the entityInstance
     */
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }

    /**
     * @param entityInstance the entityInstance to set
     */
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }
    
}
