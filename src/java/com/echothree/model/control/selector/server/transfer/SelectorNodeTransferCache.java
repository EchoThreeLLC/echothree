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

import com.echothree.model.control.selector.common.transfer.SelectorNodeTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorNodeTransferCache
        extends BaseSelectorTransferCache<SelectorNode, SelectorNodeTransfer> {
    
    /** Creates a new instance of SelectorNodeTransferCache */
    public SelectorNodeTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
    }
    
    public SelectorNodeTransfer getSelectorNodeTransfer(SelectorNode selectorNode) {
        var selectorNodeTransfer = get(selectorNode);
        
        if(selectorNodeTransfer == null) {
            var selectorNodeDetail = selectorNode.getLastDetail();
            var selectorTransferCache = selectorControl.getSelectorTransferCaches(userVisit).getSelectorTransferCache();
            var selector = selectorTransferCache.getSelectorTransfer(selectorNodeDetail.getSelector());
            var selectorNodeName = selectorNodeDetail.getSelectorNodeName();
            var isRootSelectorNode = selectorNodeDetail.getIsRootSelectorNode();
            var selectorNodeTypeTransferCache = selectorControl.getSelectorTransferCaches(userVisit).getSelectorNodeTypeTransferCache();
            var selectorNodeType = selectorNodeTypeTransferCache.getSelectorNodeTypeTransfer(selectorNodeDetail.getSelectorNodeType());
            var negate = selectorNodeDetail.getNegate();
            var description = selectorControl.getBestSelectorNodeDescription(selectorNode, getLanguage());
            
            selectorNodeTransfer = new SelectorNodeTransfer(selector, selectorNodeName, isRootSelectorNode, selectorNodeType,
            negate, description);
            put(selectorNode, selectorNodeTransfer);
        }
        
        return selectorNodeTransfer;
    }
    
}
