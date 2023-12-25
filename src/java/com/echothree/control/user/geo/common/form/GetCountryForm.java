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

package com.echothree.control.user.geo.common.form;

import com.echothree.control.user.geo.common.spec.GeoCodeSpec;

public interface GetCountryForm
        extends GeoCodeSpec {
    
    String getCountryName();
    void setCountryName(String countryName);
    
    String getIso3Number();
    void setIso3Number(String iso3Number);
    
    String getIso3Letter();
    void setIso3Letter(String iso3Letter);
    
    String getIso2Letter();
    void setIso2Letter(String iso2Letter);
    
    String getAlias();
    void setAlias(String alias);
    
}
