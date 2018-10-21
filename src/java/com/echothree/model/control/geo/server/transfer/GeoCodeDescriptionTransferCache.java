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

package com.echothree.model.control.geo.server.transfer;

import com.echothree.model.control.geo.remote.transfer.GeoCodeDescriptionTransfer;
import com.echothree.model.control.geo.remote.transfer.GeoCodeTransfer;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.geo.server.entity.GeoCodeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GeoCodeDescriptionTransferCache
        extends BaseGeoDescriptionTransferCache<GeoCodeDescription, GeoCodeDescriptionTransfer> {
    
    /** Creates a new instance of GeoCodeDescriptionTransferCache */
    public GeoCodeDescriptionTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);
    }
    
    public GeoCodeDescriptionTransfer getGeoCodeDescriptionTransfer(GeoCodeDescription geoCodeDescription) {
        GeoCodeDescriptionTransfer geoCodeDescriptionTransfer = get(geoCodeDescription);
        
        if(geoCodeDescriptionTransfer == null) {
            GeoCodeTransfer geoCodeTransfer = geoControl.getGeoCodeTransfer(userVisit, geoCodeDescription.getGeoCode());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, geoCodeDescription.getLanguage());
            
            geoCodeDescriptionTransfer = new GeoCodeDescriptionTransfer(languageTransfer, geoCodeTransfer, geoCodeDescription.getDescription());
            put(geoCodeDescription, geoCodeDescriptionTransfer);
        }
        
        return geoCodeDescriptionTransfer;
    }
    
}
