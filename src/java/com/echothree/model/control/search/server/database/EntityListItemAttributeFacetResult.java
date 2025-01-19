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

package com.echothree.model.control.search.server.database;

import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.util.server.persistence.BaseDatabaseResult;

public class EntityListItemAttributeFacetResult
        implements BaseDatabaseResult {
    
    private EntityListItem entityListItem;
    private Long count;

    /**
     * Returns the entityListItem.
     * @return the entityListItem
     */
    public EntityListItem getEntityListItem() {
        return entityListItem;
    }

    /**
     * Sets the entityListItem.
     * @param entityListItem the entityListItem to set
     */
    public void setEntityListItem(EntityListItem entityListItem) {
        this.entityListItem = entityListItem;
    }

    /**
     * Returns the count.
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * Sets the count.
     * @param count the count to set
     */
    public void setCount(Long count) {
        this.count = count;
    }
    
}
