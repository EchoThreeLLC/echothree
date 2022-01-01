// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.LotAliasTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.LotAliasTypeTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.control.LotAliasControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.inventory.server.entity.LotAliasTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class LotAliasTypeDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<LotAliasTypeDescription, LotAliasTypeDescriptionTransfer> {

    LotAliasControl lotAliasControl = Session.getModelController(LotAliasControl.class);

    /** Creates a new instance of LotAliasTypeDescriptionTransferCache */
    public LotAliasTypeDescriptionTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public LotAliasTypeDescriptionTransfer getTransfer(LotAliasTypeDescription lotAliasTypeDescription) {
        LotAliasTypeDescriptionTransfer lotAliasTypeDescriptionTransfer = get(lotAliasTypeDescription);
        
        if(lotAliasTypeDescriptionTransfer == null) {
            LotAliasTypeTransfer lotAliasTypeTransfer = lotAliasControl.getLotAliasTypeTransfer(userVisit, lotAliasTypeDescription.getLotAliasType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, lotAliasTypeDescription.getLanguage());
            
            lotAliasTypeDescriptionTransfer = new LotAliasTypeDescriptionTransfer(languageTransfer, lotAliasTypeTransfer, lotAliasTypeDescription.getDescription());
            put(lotAliasTypeDescription, lotAliasTypeDescriptionTransfer);
        }
        
        return lotAliasTypeDescriptionTransfer;
    }
    
}
