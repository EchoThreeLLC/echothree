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

package com.echothree.ui.web.main.action.configuration.geocode;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="GeoCodeDescriptionDelete")
public class DescriptionDeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String geoCodeName;
    private String languageIsoName;

    /**
     * Returns the geoCodeName.
     * @return the geoCodeName
     */
    public String getGeoCodeName() {
        return geoCodeName;
    }

    /**
     * @param geoCodeName the geoCodeName to set
     */
    public void setGeoCodeName(String geoCodeName) {
        this.geoCodeName = geoCodeName;
    }

    /**
     * Returns the languageIsoName.
     * @return the languageIsoName
     */
    public String getLanguageIsoName() {
        return languageIsoName;
    }

    /**
     * @param languageIsoName the languageIsoName to set
     */
    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }
    
}
