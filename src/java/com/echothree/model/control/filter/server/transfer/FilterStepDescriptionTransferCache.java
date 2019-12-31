// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.filter.common.transfer.FilterStepDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.filter.server.entity.FilterStepDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FilterStepDescriptionTransferCache
        extends BaseFilterDescriptionTransferCache<FilterStepDescription, FilterStepDescriptionTransfer> {
    
    /** Creates a new instance of FilterStepDescriptionTransferCache */
    public FilterStepDescriptionTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterStepDescriptionTransfer getFilterStepDescriptionTransfer(FilterStepDescription filterStepDescription) {
        FilterStepDescriptionTransfer filterStepDescriptionTransfer = get(filterStepDescription);
        
        if(filterStepDescriptionTransfer == null) {
            FilterStepTransferCache filterStepTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterStepTransferCache();
            FilterStepTransfer filterStepTransfer = filterStepTransferCache.getFilterStepTransfer(filterStepDescription.getFilterStep());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, filterStepDescription.getLanguage());
            
            filterStepDescriptionTransfer = new FilterStepDescriptionTransfer(languageTransfer, filterStepTransfer, filterStepDescription.getDescription());
            put(filterStepDescription, filterStepDescriptionTransfer);
        }
        
        return filterStepDescriptionTransfer;
    }
    
}
