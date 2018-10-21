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

import com.echothree.model.control.geo.remote.transfer.BaseGeoCodeTransfer;
import com.echothree.model.control.geo.remote.transfer.GeoCodeAliasTransfer;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.remote.transfer.MapWrapper;
import com.echothree.util.server.persistence.BaseEntity;
import java.util.List;

public abstract class BaseGeoCodeTransferCache<K extends BaseEntity, V extends BaseGeoCodeTransfer>
        extends BaseGeoTransferCache<K, V> {

    /** Creates a new instance of BaseGeoCodeTransferCache */
    public BaseGeoCodeTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);
    }

    public void setupGeoCodeAliasTransfers(GeoCode geoCode, BaseGeoCodeTransfer baseGeoCodeTransfer) {
        List<GeoCodeAliasTransfer> geoCodeAliasTransfers = geoControl.getGeoCodeAliasTransfersByGeoCode(userVisit, geoCode);
        MapWrapper<GeoCodeAliasTransfer> geoCodeAliases = new MapWrapper<>(geoCodeAliasTransfers.size());

        geoCodeAliasTransfers.stream().forEach((geoCodeAliasTransfer) -> {
            geoCodeAliases.put(geoCodeAliasTransfer.getGeoCodeAliasType().getGeoCodeAliasTypeName(), geoCodeAliasTransfer);
        });

        baseGeoCodeTransfer.setGeoCodeAliases(geoCodeAliases);
    }

}
