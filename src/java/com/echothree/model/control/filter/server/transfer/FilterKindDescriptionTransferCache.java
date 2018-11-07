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

import com.echothree.model.control.filter.common.transfer.FilterKindDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterKindTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.filter.server.entity.FilterKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FilterKindDescriptionTransferCache
        extends BaseFilterDescriptionTransferCache<FilterKindDescription, FilterKindDescriptionTransfer> {
    
    /** Creates a new instance of FilterKindDescriptionTransferCache */
    public FilterKindDescriptionTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterKindDescriptionTransfer getFilterKindDescriptionTransfer(FilterKindDescription filterKindDescription) {
        FilterKindDescriptionTransfer filterKindDescriptionTransfer = get(filterKindDescription);
        
        if(filterKindDescriptionTransfer == null) {
            FilterKindTransfer filterKindTransfer = filterControl.getFilterKindTransfer(userVisit, filterKindDescription.getFilterKind());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, filterKindDescription.getLanguage());
            
            filterKindDescriptionTransfer = new FilterKindDescriptionTransfer(languageTransfer, filterKindTransfer, filterKindDescription.getDescription());
            put(filterKindDescription, filterKindDescriptionTransfer);
        }
        
        return filterKindDescriptionTransfer;
    }
    
}
