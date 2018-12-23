// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.chain.common.transfer.ChainActionTypeTransfer;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainActionType;
import com.echothree.model.data.chain.server.entity.ChainActionTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainActionTypeTransferCache
        extends BaseChainTransferCache<ChainActionType, ChainActionTypeTransfer> {
    
    /** Creates a new instance of ChainActionTypeTransferCache */
    public ChainActionTypeTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ChainActionTypeTransfer getChainActionTypeTransfer(ChainActionType chainActionType) {
        ChainActionTypeTransfer chainActionTypeTransfer = get(chainActionType);
        
        if(chainActionTypeTransfer == null) {
            ChainActionTypeDetail chainActionTypeDetail = chainActionType.getLastDetail();
            String chainActionTypeName = chainActionTypeDetail.getChainActionTypeName();
            Boolean allowMultiple = chainActionTypeDetail.getAllowMultiple();
            Boolean isDefault = chainActionTypeDetail.getIsDefault();
            Integer sortOrder = chainActionTypeDetail.getSortOrder();
            String description = chainControl.getBestChainActionTypeDescription(chainActionType, getLanguage());
            
            chainActionTypeTransfer = new ChainActionTypeTransfer(chainActionTypeName, allowMultiple, isDefault, sortOrder, description);
            put(chainActionType, chainActionTypeTransfer);
        }
        
        return chainActionTypeTransfer;
    }
    
}
