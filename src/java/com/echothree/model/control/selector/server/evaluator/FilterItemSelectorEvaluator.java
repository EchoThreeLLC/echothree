// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.selector.server.evaluator;

import com.echothree.model.data.item.server.entity.Item;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;

public class FilterItemSelectorEvaluator
        extends BaseItemSelectorEvaluator {
    
    /** Creates a new instance of FilterItemSelectorEvaluator */
    public FilterItemSelectorEvaluator(Session session, BasePK evaluatedBy) {
        super(session, evaluatedBy, FilterItemSelectorEvaluator.class);
    }
    
    public boolean evaluate(CachedSelector cachedSelector, Item item) {
        if(cachedSelector == null)
            throw new IllegalArgumentException("cachedSelector == null");
        if(item == null)
            throw new IllegalArgumentException("item == null");
        
        return isItemSelected(cachedSelector, item);
    }
    
}
