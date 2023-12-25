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

package com.echothree.ui.web.main.action.configuration.geocodealias;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="GeoCodeAliasEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private String geoCodeName;
    private String geoCodeAliasTypeName;
    private String alias;

    /**
     * Returns the geoCodeName.
     * @return the geoCodeName
     */
    public String getGeoCodeName() {
        return geoCodeName;
    }

    /**
     * Sets the geoCodeName.
     * @param geoCodeName the geoCodeName to set
     */
    public void setGeoCodeName(String geoCodeName) {
        this.geoCodeName = geoCodeName;
    }

    /**
     * Returns the geoCodeAliasTypeName.
     * @return the geoCodeAliasTypeName
     */
    public String getGeoCodeAliasTypeName() {
        return geoCodeAliasTypeName;
    }

    /**
     * Sets the geoCodeAliasTypeName.
     * @param geoCodeAliasTypeName the geoCodeAliasTypeName to set
     */
    public void setGeoCodeAliasTypeName(String geoCodeAliasTypeName) {
        this.geoCodeAliasTypeName = geoCodeAliasTypeName;
    }

    /**
     * Returns the alias.
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the alias.
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
