// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.geo.common.transfer.PostalCodeTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PostalCodeTransferCache
        extends BaseGeoCodeTransferCache<GeoCode, PostalCodeTransfer> {
    
    boolean includeAliases;
    
    /** Creates a new instance of PostalCodeTransferCache */
    public PostalCodeTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeAliases = options.contains(GeoOptions.PostalCodeIncludeAliases);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PostalCodeTransfer getPostalCodeTransfer(GeoCode geoCode) {
        var postalCodeTransfer = get(geoCode);
        
        if(postalCodeTransfer == null) {
            var geoCodeDetail = geoCode.getLastDetail();
            var geoCodeName = geoCodeDetail.getGeoCodeName();
            var geoCodeType = geoControl.getGeoCodeTypeTransfer(userVisit, geoCodeDetail.getGeoCodeType());
            var geoCodeScope = geoControl.getGeoCodeScopeTransfer(userVisit, geoCodeDetail.getGeoCodeScope());
            var isDefault = geoCodeDetail.getIsDefault();
            var sortOrder = geoCodeDetail.getSortOrder();
            var description = geoControl.getBestGeoCodeDescription(geoCode, getLanguage());

            var countryGeoCodeType = geoControl.getGeoCodeTypeByName(GeoConstants.GeoCodeType_COUNTRY);
            var geoCodeRelationships = geoControl.getGeoCodeRelationshipsByFromGeoCodeAndGeoCodeType(geoCode, countryGeoCodeType);
            if(geoCodeRelationships.size() != 1) {
                getLog().error("non-1 geoCodeRelationships.size()");
            }
            var country = geoControl.getCountryTransfer(userVisit, geoCodeRelationships.iterator().next().getToGeoCode());
            
            postalCodeTransfer = new PostalCodeTransfer(country, geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder,
                    description);
            put(geoCode, postalCodeTransfer);
            
            if(includeAliases) {
                setupGeoCodeAliasTransfers(geoCode, postalCodeTransfer);
            }
        }
        
        return postalCodeTransfer;
    }
    
}
