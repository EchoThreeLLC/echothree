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

package com.echothree.model.control.selector.server.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorNodeTypeTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorNodeType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorNodeTypeTransferCache
        extends BaseSelectorTransferCache<SelectorNodeType, SelectorNodeTypeTransfer> {
    
    /** Creates a new instance of SelectorNodeTypeTransferCache */
    public SelectorNodeTypeTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
    }
    
    public SelectorNodeTypeTransfer getSelectorNodeTypeTransfer(SelectorNodeType selectorNodeType) {
        SelectorNodeTypeTransfer selectorNodeTypeTransfer = get(selectorNodeType);
        
        if(selectorNodeTypeTransfer == null) {
            String selectorNodeTypeName = selectorNodeType.getSelectorNodeTypeName();
            String description = selectorControl.getBestSelectorNodeTypeDescription(selectorNodeType, getLanguage());
            
            selectorNodeTypeTransfer = new SelectorNodeTypeTransfer(selectorNodeTypeName, description);
            put(selectorNodeType, selectorNodeTypeTransfer);
        }
        return selectorNodeTypeTransfer;
    }
    
}
