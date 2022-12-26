// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.filter.common.transfer.FilterAdjustmentDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class FilterAdjustmentDescriptionTransferCache
        extends BaseFilterDescriptionTransferCache<FilterAdjustmentDescription, FilterAdjustmentDescriptionTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    /** Creates a new instance of FilterAdjustmentDescriptionTransferCache */
    public FilterAdjustmentDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }

    @Override
    public FilterAdjustmentDescriptionTransfer getTransfer(FilterAdjustmentDescription filterAdjustmentDescription) {
        FilterAdjustmentDescriptionTransfer filterAdjustmentDescriptionTransfer = get(filterAdjustmentDescription);
        
        if(filterAdjustmentDescriptionTransfer == null) {
            FilterAdjustmentTransfer filterAdjustmentTransfer = filterControl.getFilterAdjustmentTransfer(userVisit, filterAdjustmentDescription.getFilterAdjustment());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, filterAdjustmentDescription.getLanguage());
            
            filterAdjustmentDescriptionTransfer = new FilterAdjustmentDescriptionTransfer(languageTransfer, filterAdjustmentTransfer, filterAdjustmentDescription.getDescription());
            put(filterAdjustmentDescription, filterAdjustmentDescriptionTransfer);
        }
        
        return filterAdjustmentDescriptionTransfer;
    }
    
}
