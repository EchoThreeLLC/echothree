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

package com.echothree.model.control.filter.server.transfer;

import com.echothree.model.control.filter.common.FilterOptions;
import com.echothree.model.control.filter.common.transfer.FilterStepTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepDetail;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class FilterStepTransferCache
        extends BaseFilterTransferCache<FilterStep, FilterStepTransfer> {
    
    SelectorControl selectorControl;
    boolean includeFilterStepElements;
    boolean includeFilterStepDestinations;
    
    /** Creates a new instance of FilterStepTransferCache */
    public FilterStepTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
        
        selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeFilterStepElements = options.contains(FilterOptions.FilterStepIncludeFilterStepElements);
            includeFilterStepDestinations = options.contains(FilterOptions.FilterStepIncludeFilterStepDestinations);
        }
    }
    
    public FilterStepTransfer getFilterStepTransfer(FilterStep filterStep) {
        FilterStepTransfer filterStepTransfer = get(filterStep);
        
        if(filterStepTransfer == null) {
            FilterStepDetail filterStepDetail = filterStep.getLastDetail();
            FilterTransferCache filterTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterTransferCache();
            FilterTransfer filter = filterTransferCache.getFilterTransfer(filterStepDetail.getFilter());
            String filterStepName = filterStepDetail.getFilterStepName();
            Selector filterItemSelector = filterStepDetail.getFilterItemSelector();
            SelectorTransfer filterItemSelectorTransfer = filterItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, filterItemSelector);
            String description = filterControl.getBestFilterStepDescription(filterStep, getLanguage());
            
            filterStepTransfer = new FilterStepTransfer(filter, filterStepName, filterItemSelectorTransfer, description);
            put(filterStep, filterStepTransfer);
            
            if(includeFilterStepElements) {
                filterStepTransfer.setFilterStepElements(new ListWrapper<>(filterControl.getFilterStepElementTransfersByFilterStep(userVisit, filterStep)));
            }
            
            if(includeFilterStepDestinations) {
                filterStepTransfer.setFilterStepDestinations(new ListWrapper<>(filterControl.getFilterStepDestinationTransfersByFromFilterStep(userVisit, filterStep)));
            }
        }
        
        return filterStepTransfer;
    }
    
}
