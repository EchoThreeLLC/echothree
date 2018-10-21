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

package com.echothree.model.control.shipment.server.transfer;

import com.echothree.model.control.shipment.remote.transfer.ShipmentTimeTransfer;
import com.echothree.model.control.shipment.remote.transfer.ShipmentTimeTypeTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentTime;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ShipmentTimeTransferCache
        extends BaseShipmentTransferCache<ShipmentTime, ShipmentTimeTransfer> {
    
    /** Creates a new instance of ShipmentTimeTransferCache */
    public ShipmentTimeTransferCache(UserVisit userVisit, ShipmentControl shipmentControl) {
        super(userVisit, shipmentControl);
    }
    
    public ShipmentTimeTransfer getShipmentTimeTransfer(ShipmentTime shipmentTime) {
        ShipmentTimeTransfer shipmentTimeTransfer = get(shipmentTime);
        
        if(shipmentTimeTransfer == null) {
            ShipmentTimeTypeTransfer shipmentTimeType = shipmentControl.getShipmentTimeTypeTransfer(userVisit, shipmentTime.getShipmentTimeType());
            Long unformattedTime = shipmentTime.getTime();
            String time = formatTypicalDateTime(unformattedTime);
            
            shipmentTimeTransfer = new ShipmentTimeTransfer(shipmentTimeType, unformattedTime, time);
            put(shipmentTime, shipmentTimeTransfer);
        }
        
        return shipmentTimeTransfer;
    }
    
}
