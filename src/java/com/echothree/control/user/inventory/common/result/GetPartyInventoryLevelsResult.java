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

package com.echothree.control.user.inventory.common.result;

import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.common.transfer.PartyInventoryLevelTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.warehouse.common.transfer.WarehouseTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetPartyInventoryLevelsResult
        extends BaseResult {
    
    PartyTransfer getParty();
    void setParty(PartyTransfer party);
    
    WarehouseTransfer getWarehouse();
    void setWarehouse(WarehouseTransfer warehouse);
    
    CompanyTransfer getCompany();
    void setCompany(CompanyTransfer company);
    
    ItemTransfer getItem();
    void setItem(ItemTransfer item);
    
    InventoryConditionTransfer getInventoryCondition();
    void setInventoryCondition(InventoryConditionTransfer inventoryCondition);
    
    List<PartyInventoryLevelTransfer> getPartyInventoryLevels();
    void setPartyInventoryLevels(List<PartyInventoryLevelTransfer> partyInventoryLevels);
    
}
