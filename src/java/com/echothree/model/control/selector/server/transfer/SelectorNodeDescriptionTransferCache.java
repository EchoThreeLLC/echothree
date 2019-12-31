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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorNodeDescriptionTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorNodeTransfer;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorNodeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorNodeDescriptionTransferCache
        extends BaseSelectorDescriptionTransferCache<SelectorNodeDescription, SelectorNodeDescriptionTransfer> {
    
    /** Creates a new instance of SelectorNodeDescriptionTransferCache */
    public SelectorNodeDescriptionTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
    }
    
    public SelectorNodeDescriptionTransfer getSelectorNodeDescriptionTransfer(SelectorNodeDescription selectorNodeDescription) {
        SelectorNodeDescriptionTransfer selectorNodeDescriptionTransfer = get(selectorNodeDescription);
        
        if(selectorNodeDescriptionTransfer == null) {
            SelectorNodeTransferCache selectorNodeTransferCache = selectorControl.getSelectorTransferCaches(userVisit).getSelectorNodeTransferCache();
            SelectorNodeTransfer selectorNodeTransfer = selectorNodeTransferCache.getSelectorNodeTransfer(selectorNodeDescription.getSelectorNode());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, selectorNodeDescription.getLanguage());
            
            selectorNodeDescriptionTransfer = new SelectorNodeDescriptionTransfer(languageTransfer, selectorNodeTransfer, selectorNodeDescription.getDescription());
            put(selectorNodeDescription, selectorNodeDescriptionTransfer);
        }
        
        return selectorNodeDescriptionTransfer;
    }
    
}
