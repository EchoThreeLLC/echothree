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

package com.echothree.model.control.filter.remote.transfer;

import com.echothree.model.control.selector.remote.transfer.SelectorTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class FilterStepElementTransfer
        extends BaseTransfer {
    
    private FilterStepTransfer filterStep;
    private String filterStepElementName;
    private SelectorTransfer filterItemSelector;
    private FilterAdjustmentTransfer filterAdjustment;
    private String description;
    
    /** Creates a new instance of FilterStepElementTransfer */
    public FilterStepElementTransfer(FilterStepTransfer filterStep, String filterStepElementName, SelectorTransfer filterItemSelector,
    FilterAdjustmentTransfer filterAdjustment, String description) {
        this.filterStep = filterStep;
        this.filterStepElementName = filterStepElementName;
        this.filterItemSelector = filterItemSelector;
        this.filterAdjustment = filterAdjustment;
        this.description = description;
    }
    
    public FilterStepTransfer getFilterStep() {
        return filterStep;
    }
    
    public void setFilterStep(FilterStepTransfer filterStep) {
        this.filterStep = filterStep;
    }
    
    public String getFilterStepElementName() {
        return filterStepElementName;
    }
    
    public void setFilterStepElementName(String filterStepElementName) {
        this.filterStepElementName = filterStepElementName;
    }
    
    public SelectorTransfer getFilterItemSelector() {
        return filterItemSelector;
    }
    
    public void setFilterItemSelector(SelectorTransfer filterItemSelector) {
        this.filterItemSelector = filterItemSelector;
    }
    
    public FilterAdjustmentTransfer getFilterAdjustment() {
        return filterAdjustment;
    }
    
    public void setFilterAdjustment(FilterAdjustmentTransfer filterAdjustment) {
        this.filterAdjustment = filterAdjustment;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
