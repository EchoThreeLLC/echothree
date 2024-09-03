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

import com.echothree.model.control.chain.common.transfer.ChainDescriptionTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainDescriptionTransferCache
        extends BaseChainDescriptionTransferCache<ChainDescription, ChainDescriptionTransfer> {
    
    /** Creates a new instance of ChainDescriptionTransferCache */
    public ChainDescriptionTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
    }
    
    public ChainDescriptionTransfer getChainDescriptionTransfer(ChainDescription chainDescription) {
        var chainDescriptionTransfer = get(chainDescription);
        
        if(chainDescriptionTransfer == null) {
            var chainTransfer = chainControl.getChainTransfer(userVisit, chainDescription.getChain());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, chainDescription.getLanguage());
            
            chainDescriptionTransfer = new ChainDescriptionTransfer(languageTransfer, chainTransfer, chainDescription.getDescription());
            put(chainDescription, chainDescriptionTransfer);
        }
        
        return chainDescriptionTransfer;
    }
    
}
