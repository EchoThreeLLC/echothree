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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.model.control.chain.common.transfer.ChainActionSetDescriptionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionSetTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.chain.server.entity.ChainActionSetDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainActionSetDescriptionTransferCache
        extends BaseChainDescriptionTransferCache<ChainActionSetDescription, ChainActionSetDescriptionTransfer> {
    
    /** Creates a new instance of ChainActionSetDescriptionTransferCache */
    public ChainActionSetDescriptionTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
    }
    
    public ChainActionSetDescriptionTransfer getChainActionSetDescriptionTransfer(ChainActionSetDescription chainActionSetDescription) {
        ChainActionSetDescriptionTransfer chainActionSetDescriptionTransfer = get(chainActionSetDescription);
        
        if(chainActionSetDescriptionTransfer == null) {
            ChainActionSetTransfer chainActionSetTransfer = chainControl.getChainActionSetTransfer(userVisit, chainActionSetDescription.getChainActionSet());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, chainActionSetDescription.getLanguage());
            
            chainActionSetDescriptionTransfer = new ChainActionSetDescriptionTransfer(languageTransfer, chainActionSetTransfer, chainActionSetDescription.getDescription());
            put(chainActionSetDescription, chainActionSetDescriptionTransfer);
        }
        
        return chainActionSetDescriptionTransfer;
    }
    
}
