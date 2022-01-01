// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.warehouse.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationNameElementDescriptionTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationNameElementTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationNameElementDescription;

public class LocationNameElementDescriptionTransferCache
        extends BaseWarehouseDescriptionTransferCache<LocationNameElementDescription, LocationNameElementDescriptionTransfer> {
    
    /** Creates a new instance of LocationNameElementDescriptionTransferCache */
    public LocationNameElementDescriptionTransferCache(UserVisit userVisit, WarehouseControl warehouseControl) {
        super(userVisit, warehouseControl);
    }
    
    public LocationNameElementDescriptionTransfer getLocationNameElementDescriptionTransfer(LocationNameElementDescription locationNameElementDescription) {
        LocationNameElementDescriptionTransfer locationNameElementDescriptionTransfer = get(locationNameElementDescription);
        
        if(locationNameElementDescriptionTransfer == null) {
            LocationNameElementTransferCache locationNameElementTransferCache = warehouseControl.getWarehouseTransferCaches(userVisit).getLocationNameElementTransferCache();
            LocationNameElementTransfer locationNameElementTransfer = locationNameElementTransferCache.getLocationNameElementTransfer(locationNameElementDescription.getLocationNameElement());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, locationNameElementDescription.getLanguage());
            
            locationNameElementDescriptionTransfer = new LocationNameElementDescriptionTransfer(languageTransfer, locationNameElementTransfer, locationNameElementDescription.getDescription());
            put(locationNameElementDescription, locationNameElementDescriptionTransfer);
        }
        
        return locationNameElementDescriptionTransfer;
    }
    
}
