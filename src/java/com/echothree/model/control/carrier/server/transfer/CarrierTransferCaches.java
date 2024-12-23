// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class CarrierTransferCaches
        extends BaseTransferCaches {
    
    protected CarrierControl carrierControl;
    
    CarrierTransferCache carrierTransferCache;
    CarrierTypeTransferCache carrierTypeTransferCache;
    CarrierTypeDescriptionTransferCache carrierTypeDescriptionTransferCache;
    CarrierServiceDescriptionTransferCache carrierServiceDescriptionTransferCache;
    CarrierServiceTransferCache carrierServiceTransferCache;
    CarrierOptionDescriptionTransferCache carrierOptionDescriptionTransferCache;
    CarrierOptionTransferCache carrierOptionTransferCache;
    PartyCarrierTransferCache partyCarrierTransferCache;
    PartyCarrierAccountTransferCache partyCarrierAccountTransferCache;
    CarrierServiceOptionTransferCache carrierServiceOptionTransferCache;
    
    /** Creates a new instance of CarrierTransferCaches */
    public CarrierTransferCaches(UserVisit userVisit, CarrierControl carrierControl) {
        super(userVisit);
        
        this.carrierControl = carrierControl;
    }
    
    public CarrierTransferCache getCarrierTransferCache() {
        if(carrierTransferCache == null)
            carrierTransferCache = new CarrierTransferCache(userVisit, carrierControl);
        
        return carrierTransferCache;
    }
    
    public CarrierTypeTransferCache getCarrierTypeTransferCache() {
        if(carrierTypeTransferCache == null)
            carrierTypeTransferCache = new CarrierTypeTransferCache(userVisit, carrierControl);
        
        return carrierTypeTransferCache;
    }
    
    public CarrierTypeDescriptionTransferCache getCarrierTypeDescriptionTransferCache() {
        if(carrierTypeDescriptionTransferCache == null)
            carrierTypeDescriptionTransferCache = new CarrierTypeDescriptionTransferCache(userVisit, carrierControl);

        return carrierTypeDescriptionTransferCache;
    }

    public CarrierServiceDescriptionTransferCache getCarrierServiceDescriptionTransferCache() {
        if(carrierServiceDescriptionTransferCache == null)
            carrierServiceDescriptionTransferCache = new CarrierServiceDescriptionTransferCache(userVisit, carrierControl);

        return carrierServiceDescriptionTransferCache;
    }

    public CarrierServiceTransferCache getCarrierServiceTransferCache() {
        if(carrierServiceTransferCache == null)
            carrierServiceTransferCache = new CarrierServiceTransferCache(userVisit, carrierControl);
        
        return carrierServiceTransferCache;
    }
    
    public CarrierOptionDescriptionTransferCache getCarrierOptionDescriptionTransferCache() {
        if(carrierOptionDescriptionTransferCache == null)
            carrierOptionDescriptionTransferCache = new CarrierOptionDescriptionTransferCache(userVisit, carrierControl);
        
        return carrierOptionDescriptionTransferCache;
    }
    
    public CarrierOptionTransferCache getCarrierOptionTransferCache() {
        if(carrierOptionTransferCache == null)
            carrierOptionTransferCache = new CarrierOptionTransferCache(userVisit, carrierControl);
        
        return carrierOptionTransferCache;
    }
    
    public PartyCarrierTransferCache getPartyCarrierTransferCache() {
        if(partyCarrierTransferCache == null)
            partyCarrierTransferCache = new PartyCarrierTransferCache(userVisit, carrierControl);

        return partyCarrierTransferCache;
    }

    public PartyCarrierAccountTransferCache getPartyCarrierAccountTransferCache() {
        if(partyCarrierAccountTransferCache == null)
            partyCarrierAccountTransferCache = new PartyCarrierAccountTransferCache(userVisit, carrierControl);

        return partyCarrierAccountTransferCache;
    }

    public CarrierServiceOptionTransferCache getCarrierServiceOptionTransferCache() {
        if(carrierServiceOptionTransferCache == null)
            carrierServiceOptionTransferCache = new CarrierServiceOptionTransferCache(userVisit, carrierControl);
        
        return carrierServiceOptionTransferCache;
    }
    
}
