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

package com.echothree.model.control.carrier.server.transfer;

import com.echothree.model.control.carrier.common.transfer.CarrierOptionTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierServiceOptionTransfer;
import com.echothree.model.control.carrier.common.transfer.CarrierServiceTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.data.carrier.server.entity.CarrierServiceOption;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CarrierServiceOptionTransferCache
        extends BaseCarrierTransferCache<CarrierServiceOption, CarrierServiceOptionTransfer> {
    
    /** Creates a new instance of CarrierServiceOptionTransferCache */
    public CarrierServiceOptionTransferCache(UserVisit userVisit, CarrierControl carrierControl) {
        super(userVisit, carrierControl);
    }
    
    public CarrierServiceOptionTransfer getCarrierServiceOptionTransfer(CarrierServiceOption carrierServiceOption) {
        CarrierServiceOptionTransfer carrierServiceOptionTransfer = get(carrierServiceOption);
        
        if(carrierServiceOptionTransfer == null) {
            CarrierServiceTransfer carrierService = carrierControl.getCarrierServiceTransfer(userVisit, carrierServiceOption.getCarrierService());
            CarrierOptionTransfer carrierOption = carrierControl.getCarrierOptionTransfer(userVisit, carrierServiceOption.getCarrierOption());
            
            carrierServiceOptionTransfer = new CarrierServiceOptionTransfer(carrierService, carrierOption);
            put(carrierServiceOption, carrierServiceOptionTransfer);
        }
        
        return carrierServiceOptionTransfer;
    }
    
}
