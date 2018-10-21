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

package com.echothree.model.control.carrier.server.transfer;

import com.echothree.model.control.carrier.remote.transfer.CarrierServiceTransfer;
import com.echothree.model.control.carrier.remote.transfer.CarrierTransfer;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.selector.remote.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.carrier.server.entity.CarrierServiceDetail;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CarrierServiceTransferCache
        extends BaseCarrierTransferCache<CarrierService, CarrierServiceTransfer> {
    
    SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
    
    /** Creates a new instance of CarrierServiceTransferCache */
    public CarrierServiceTransferCache(UserVisit userVisit, CarrierControl carrierControl) {
        super(userVisit, carrierControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CarrierServiceTransfer getCarrierServiceTransfer(CarrierService carrierService) {
        CarrierServiceTransfer carrierServiceTransfer = get(carrierService);
        
        if(carrierServiceTransfer == null) {
            CarrierServiceDetail carrierServiceDetail = carrierService.getLastDetail();
            CarrierTransfer carrier = carrierControl.getCarrierTransfer(userVisit, carrierServiceDetail.getCarrierParty());
            String carrierServiceName = carrierServiceDetail.getCarrierServiceName();
            Selector geoCodeSelector = carrierServiceDetail.getGeoCodeSelector();
            SelectorTransfer geoCodeSelectorTransfer = geoCodeSelector == null? null: selectorControl.getSelectorTransfer(userVisit, geoCodeSelector);
            Selector itemSelector = carrierServiceDetail.getItemSelector();
            SelectorTransfer itemSelectorTransfer = itemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, itemSelector);
            Boolean isDefault = carrierServiceDetail.getIsDefault();
            Integer sortOrder = carrierServiceDetail.getSortOrder();
            String description = carrierControl.getBestCarrierServiceDescription(carrierService, getLanguage());
            
            carrierServiceTransfer = new CarrierServiceTransfer(carrier, carrierServiceName, geoCodeSelectorTransfer, itemSelectorTransfer, isDefault,
                    sortOrder, description);
            put(carrierService, carrierServiceTransfer);
        }
        
        return carrierServiceTransfer;
    }
    
}
