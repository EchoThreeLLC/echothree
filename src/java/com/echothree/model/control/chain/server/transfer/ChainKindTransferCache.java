// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.model.control.chain.common.transfer.ChainKindTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainKindTransferCache
        extends BaseChainTransferCache<ChainKind, ChainKindTransfer> {
    
    /** Creates a new instance of ChainKindTransferCache */
    public ChainKindTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ChainKindTransfer getChainKindTransfer(ChainKind chainKind) {
        var chainKindTransfer = get(chainKind);
        
        if(chainKindTransfer == null) {
            var chainKindDetail = chainKind.getLastDetail();
            var chainKindName = chainKindDetail.getChainKindName();
            var isDefault = chainKindDetail.getIsDefault();
            var sortOrder = chainKindDetail.getSortOrder();
            var description = chainControl.getBestChainKindDescription(chainKind, getLanguage());
            
            chainKindTransfer = new ChainKindTransfer(chainKindName, isDefault, sortOrder, description);
            put(chainKind, chainKindTransfer);
        }
        
        return chainKindTransfer;
    }
    
}
