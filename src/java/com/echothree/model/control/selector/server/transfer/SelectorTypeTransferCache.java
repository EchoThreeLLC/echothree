// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.selector.common.transfer.SelectorTypeTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.selector.server.entity.SelectorTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorTypeTransferCache
        extends BaseSelectorTransferCache<SelectorType, SelectorTypeTransfer> {
    
    /** Creates a new instance of SelectorTypeTransferCache */
    public SelectorTypeTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SelectorTypeTransfer getSelectorTypeTransfer(SelectorType selectorType) {
        var selectorTypeTransfer = get(selectorType);
        
        if(selectorTypeTransfer == null) {
            var selectorTypeDetail = selectorType.getLastDetail();
            var selectorKindTransfer = selectorControl.getSelectorKindTransfer(userVisit, selectorTypeDetail.getSelectorKind());
            var selectorTypeName = selectorTypeDetail.getSelectorTypeName();
            var isDefault = selectorTypeDetail.getIsDefault();
            var sortOrder = selectorTypeDetail.getSortOrder();
            var description = selectorControl.getBestSelectorTypeDescription(selectorType, getLanguage());
            
            selectorTypeTransfer = new SelectorTypeTransfer(selectorKindTransfer, selectorTypeName, isDefault, sortOrder, description);
            put(selectorType, selectorTypeTransfer);
        }
        
        return selectorTypeTransfer;
    }
    
}
