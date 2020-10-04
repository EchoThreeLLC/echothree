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

package com.echothree.model.control.shipment.server.transfer;

import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.shipment.common.transfer.PartyFreeOnBoardTransfer;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.data.shipment.server.entity.PartyFreeOnBoard;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyFreeOnBoardTransferCache
        extends BaseShipmentTransferCache<PartyFreeOnBoard, PartyFreeOnBoardTransfer> {

    FreeOnBoardControl freeOnBoardControl = (FreeOnBoardControl)Session.getModelController(FreeOnBoardControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

    /** Creates a new instance of FreeOnBoardTransferCache */
    public PartyFreeOnBoardTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    @Override
    public PartyFreeOnBoardTransfer getTransfer(PartyFreeOnBoard partyFreeOnBoard) {
        PartyFreeOnBoardTransfer partyFreeOnBoardTransfer = get(partyFreeOnBoard);
        
        if(partyFreeOnBoardTransfer == null) {
            var partyTransfer = partyControl.getPartyTransfer(userVisit, partyFreeOnBoard.getParty());
            var freeOnBoardTransfer = freeOnBoardControl.getFreeOnBoardTransfer(userVisit, partyFreeOnBoard.getFreeOnBoard());

            partyFreeOnBoardTransfer = new PartyFreeOnBoardTransfer(partyTransfer, freeOnBoardTransfer);
            put(partyFreeOnBoard, partyFreeOnBoardTransfer);
        }
        
        return partyFreeOnBoardTransfer;
    }
    
}
