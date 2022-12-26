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

package com.echothree.model.control.selector.server.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorKindTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorKindDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorKindTransferCache
        extends BaseSelectorTransferCache<SelectorKind, SelectorKindTransfer> {
    
    /** Creates a new instance of SelectorKindTransferCache */
    public SelectorKindTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SelectorKindTransfer getSelectorKindTransfer(SelectorKind selectorKind) {
        SelectorKindTransfer selectorKindTransfer = get(selectorKind);
        
        if(selectorKindTransfer == null) {
            SelectorKindDetail selectorKindDetail = selectorKind.getLastDetail();
            String selectorKindName = selectorKindDetail.getSelectorKindName();
            Boolean isDefault = selectorKindDetail.getIsDefault();
            Integer sortOrder = selectorKindDetail.getSortOrder();
            String description = selectorControl.getBestSelectorKindDescription(selectorKind, getLanguage());
            
            selectorKindTransfer = new SelectorKindTransfer(selectorKindName, isDefault, sortOrder, description);
            put(selectorKind, selectorKindTransfer);
        }
        
        return selectorKindTransfer;
    }
    
}
