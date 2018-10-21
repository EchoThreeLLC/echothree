// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.remote.transfer.ItemAliasTransfer;
import com.echothree.model.control.item.remote.transfer.ItemAliasTypeTransfer;
import com.echothree.model.control.item.remote.transfer.ItemTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.remote.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemAliasTransferCache
        extends BaseItemTransferCache<ItemAlias, ItemAliasTransfer> {
    
    UomControl uomControl;
    
    /** Creates a new instance of ItemAliasTransferCache */
    public ItemAliasTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        uomControl = (UomControl)Session.getModelController(UomControl.class);
    }
    
    @Override
    public ItemAliasTransfer getTransfer(ItemAlias itemAlias) {
        ItemAliasTransfer itemAliasTransfer = get(itemAlias);
        
        if(itemAliasTransfer == null) {
            ItemTransfer item = itemControl.getItemTransfer(userVisit, itemAlias.getItem());
            UnitOfMeasureTypeTransfer unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemAlias.getUnitOfMeasureType());
            ItemAliasTypeTransfer itemAliasType = itemControl.getItemAliasTypeTransfer(userVisit, itemAlias.getItemAliasType());
            String alias = itemAlias.getAlias();
            
            itemAliasTransfer = new ItemAliasTransfer(item, unitOfMeasureType, itemAliasType, alias);
            put(itemAlias, itemAliasTransfer);
        }
        
        return itemAliasTransfer;
    }
    
}
