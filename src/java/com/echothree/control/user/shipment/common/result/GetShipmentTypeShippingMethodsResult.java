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

package com.echothree.control.user.shipment.common.result;

import com.echothree.model.control.shipment.common.transfer.ShipmentTypeShippingMethodTransfer;
import com.echothree.model.control.shipment.common.transfer.ShipmentTypeTransfer;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetShipmentTypeShippingMethodsResult
        extends BaseResult {
    
    ShipmentTypeTransfer getShipmentType();
    void setShipmentType(ShipmentTypeTransfer shipmentType);
    
    ShippingMethodTransfer getShippingMethod();
    void setShippingMethod(ShippingMethodTransfer shippingMethod);
    
    List<ShipmentTypeShippingMethodTransfer> getShipmentTypeShippingMethods();
    void setShipmentTypeShippingMethods(List<ShipmentTypeShippingMethodTransfer> shipmentTypeShippingMethods);
    
}
