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

package com.echothree.model.control.tax.server.transfer;

import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.tax.common.transfer.TaxClassificationTransfer;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.tax.server.entity.TaxClassification;
import com.echothree.model.data.tax.server.entity.TaxClassificationDetail;
import com.echothree.model.data.tax.server.entity.TaxClassificationTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TaxClassificationTransferCache
        extends BaseTaxTransferCache<TaxClassification, TaxClassificationTransfer> {
    
    GeoControl geoControl = Session.getModelController(GeoControl.class);
    
    /** Creates a new instance of TaxClassificationTransferCache */
    public TaxClassificationTransferCache(UserVisit userVisit, TaxControl taxControl) {
        super(userVisit, taxControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TaxClassificationTransfer getTransfer(TaxClassification taxClassification) {
        TaxClassificationTransfer taxClassificationTransfer = get(taxClassification);
        
        if(taxClassificationTransfer == null) {
            TaxClassificationDetail taxClassificationDetail = taxClassification.getLastDetail();
            CountryTransfer countryTransfer = geoControl.getCountryTransfer(userVisit, taxClassificationDetail.getCountryGeoCode());
            String taxClassificationName = taxClassificationDetail.getTaxClassificationName();
            Boolean isDefault = taxClassificationDetail.getIsDefault();
            Integer sortOrder = taxClassificationDetail.getSortOrder();
            TaxClassificationTranslation taxClassificationTranslation = taxControl.getBestTaxClassificationTranslation(taxClassification, getLanguage());
            String description = taxClassificationTranslation == null ? taxClassificationName : taxClassificationTranslation.getDescription();
            
            taxClassificationTransfer = new TaxClassificationTransfer(countryTransfer, taxClassificationName, isDefault, sortOrder, description);
            put(taxClassification, taxClassificationTransfer);
        }
        
        return taxClassificationTransfer;
    }
    
}
