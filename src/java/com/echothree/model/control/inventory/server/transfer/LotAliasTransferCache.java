// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.inventory.common.transfer.LotAliasTransfer;
import com.echothree.model.control.inventory.common.transfer.LotAliasTypeTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.control.LotAliasControl;
import com.echothree.model.control.inventory.server.control.LotControl;
import com.echothree.model.data.inventory.server.entity.LotAlias;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class LotAliasTransferCache
        extends BaseInventoryTransferCache<LotAlias, LotAliasTransfer> {

    LotControl lotControl = Session.getModelController(LotControl.class);
    LotAliasControl lotAliasControl = Session.getModelController(LotAliasControl.class);

    /** Creates a new instance of LotAliasTransferCache */
    public LotAliasTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public LotAliasTransfer getTransfer(LotAlias lotAlias) {
        LotAliasTransfer lotAliasTransfer = get(lotAlias);
        
        if(lotAliasTransfer == null) {
            //LotTransfer lot = lotControl.getLotTransfer(userVisit, lotAlias.getLot());
            LotAliasTypeTransfer lotAliasType = lotAliasControl.getLotAliasTypeTransfer(userVisit, lotAlias.getLotAliasType());
            String alias = lotAlias.getAlias();
            
            lotAliasTransfer = new LotAliasTransfer(/*lot,*/ lotAliasType, alias);
            put(lotAlias, lotAliasTransfer);
        }
        
        return lotAliasTransfer;
    }
    
}
