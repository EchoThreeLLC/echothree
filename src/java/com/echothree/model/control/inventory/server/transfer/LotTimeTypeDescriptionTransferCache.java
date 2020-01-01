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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.LotTimeTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTimeTypeTransfer;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.inventory.server.entity.LotTimeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class LotTimeTypeDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<LotTimeTypeDescription, LotTimeTypeDescriptionTransfer> {
    
    /** Creates a new instance of LotTimeTypeDescriptionTransferCache */
    public LotTimeTypeDescriptionTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public LotTimeTypeDescriptionTransfer getTransfer(LotTimeTypeDescription lotTimeTypeDescription) {
        LotTimeTypeDescriptionTransfer lotTimeTypeDescriptionTransfer = get(lotTimeTypeDescription);
        
        if(lotTimeTypeDescriptionTransfer == null) {
            LotTimeTypeTransfer lotTimeTypeTransfer = inventoryControl.getLotTimeTypeTransfer(userVisit, lotTimeTypeDescription.getLotTimeType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, lotTimeTypeDescription.getLanguage());
            
            lotTimeTypeDescriptionTransfer = new LotTimeTypeDescriptionTransfer(languageTransfer, lotTimeTypeTransfer, lotTimeTypeDescription.getDescription());
            put(lotTimeTypeDescription, lotTimeTypeDescriptionTransfer);
        }
        
        return lotTimeTypeDescriptionTransfer;
    }
    
}
