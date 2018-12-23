// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.filter.server.evaluator;

import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.util.server.persistence.Session;

public class FilterCacheFactory {
    
    /**
     * The Singleton instance.
     */
    private static final FilterCacheFactory instance = new FilterCacheFactory();
    
    protected FilterCacheFactory() {
        super();
    }
    
    /**
     * Returns the Singleton instance of FilterCacheFactory.
     */
    public static FilterCacheFactory getInstance() {
        return instance;
    }
    
    public FilterCache getFilterCache(Session session, String filterKindName, String filterTypeName) {
        FilterControl filterControl = (FilterControl)Session.getModelController(FilterControl.class);
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        FilterCache filterCache = null;
        
        if(filterKind != null) {
            FilterType filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                filterCache = new FilterCache(session, filterControl, filterType);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        
        return filterCache;
    }
    
}
