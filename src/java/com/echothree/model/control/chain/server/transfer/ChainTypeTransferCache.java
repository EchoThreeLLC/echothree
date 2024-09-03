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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.model.control.chain.common.transfer.ChainTypeTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainTypeTransferCache
        extends BaseChainTransferCache<ChainType, ChainTypeTransfer> {
    
    /** Creates a new instance of ChainTypeTransferCache */
    public ChainTypeTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ChainTypeTransfer getChainTypeTransfer(ChainType chainType) {
        var chainTypeTransfer = get(chainType);
        
        if(chainTypeTransfer == null) {
            var chainTypeDetail = chainType.getLastDetail();
            var chainKindTransfer = chainControl.getChainKindTransfer(userVisit, chainTypeDetail.getChainKind());
            var chainTypeName = chainTypeDetail.getChainTypeName();
            var isDefault = chainTypeDetail.getIsDefault();
            var sortOrder = chainTypeDetail.getSortOrder();
            var description = chainControl.getBestChainTypeDescription(chainType, getLanguage());
            
            chainTypeTransfer = new ChainTypeTransfer(chainKindTransfer, chainTypeName, isDefault, sortOrder, description);
            put(chainType, chainTypeTransfer);
        }
        
        return chainTypeTransfer;
    }
    
}
