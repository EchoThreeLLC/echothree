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

package com.echothree.model.control.search.remote.transfer;

import com.echothree.model.control.core.remote.transfer.EntityIntegerRangeTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class UserVisitSearchFacetIntegerTransfer
        extends BaseTransfer {
    
    private EntityIntegerRangeTransfer entityIntegerRange;
    private Integer count;
    
    /** Creates a new instance of UserVisitSearchFacetListItemTransfer */
    public UserVisitSearchFacetIntegerTransfer(EntityIntegerRangeTransfer entityIntegerRange, Integer count) {
        this.entityIntegerRange = entityIntegerRange;
        this.count = count;
    }

    /**
     * @return the entityIntegerRange
     */
    public EntityIntegerRangeTransfer getEntityIntegerRange() {
        return entityIntegerRange;
    }

    /**
     * @param entityIntegerRange the entityIntegerRange to set
     */
    public void setEntityIntegerRange(EntityIntegerRangeTransfer entityIntegerRange) {
        this.entityIntegerRange = entityIntegerRange;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

}
