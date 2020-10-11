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

package com.echothree.model.control.uom.server.transfer;

import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class UomTransferCaches
        extends BaseTransferCaches {
    
    protected UomControl uomControl;
    
    protected UnitOfMeasureKindTransferCache uomKindTransferCache;
    protected UnitOfMeasureTypeTransferCache uomTypeTransferCache;
    protected UnitOfMeasureKindDescriptionTransferCache uomKindDescriptionTransferCache;
    protected UnitOfMeasureTypeDescriptionTransferCache uomTypeDescriptionTransferCache;
    protected UnitOfMeasureKindUseTransferCache uomKindUseTransferCache;
    protected UnitOfMeasureKindUseTypeTransferCache uomKindUseTypeTransferCache;
    protected UnitOfMeasureEquivalentTransferCache uomEquivalentTransferCache;
    protected UnitOfMeasureTypeVolumeTransferCache uomTypeVolumeTransferCache;
    protected UnitOfMeasureTypeWeightTransferCache uomTypeWeightTransferCache;
    
    /** Creates a new instance of UomTransferCaches */
    public UomTransferCaches(UserVisit userVisit, UomControl uomControl) {
        super(userVisit);
        
        this.uomControl = uomControl;
    }
    
    public UnitOfMeasureKindTransferCache getUnitOfMeasureKindTransferCache() {
        if(uomKindTransferCache == null)
            uomKindTransferCache = new UnitOfMeasureKindTransferCache(userVisit, uomControl);
        
        return uomKindTransferCache;
    }
    
    public UnitOfMeasureTypeTransferCache getUnitOfMeasureTypeTransferCache() {
        if(uomTypeTransferCache == null)
            uomTypeTransferCache = new UnitOfMeasureTypeTransferCache(userVisit, uomControl);
        
        return uomTypeTransferCache;
    }
    
    public UnitOfMeasureKindDescriptionTransferCache getUnitOfMeasureKindDescriptionTransferCache() {
        if(uomKindDescriptionTransferCache == null)
            uomKindDescriptionTransferCache = new UnitOfMeasureKindDescriptionTransferCache(userVisit, uomControl);
        
        return uomKindDescriptionTransferCache;
    }
    
    public UnitOfMeasureTypeDescriptionTransferCache getUnitOfMeasureTypeDescriptionTransferCache() {
        if(uomTypeDescriptionTransferCache == null)
            uomTypeDescriptionTransferCache = new UnitOfMeasureTypeDescriptionTransferCache(userVisit, uomControl);
        
        return uomTypeDescriptionTransferCache;
    }
    
    public UnitOfMeasureKindUseTransferCache getUnitOfMeasureKindUseTransferCache() {
        if(uomKindUseTransferCache == null)
            uomKindUseTransferCache = new UnitOfMeasureKindUseTransferCache(userVisit, uomControl);
        
        return uomKindUseTransferCache;
    }
    
    public UnitOfMeasureKindUseTypeTransferCache getUnitOfMeasureKindUseTypeTransferCache() {
        if(uomKindUseTypeTransferCache == null)
            uomKindUseTypeTransferCache = new UnitOfMeasureKindUseTypeTransferCache(userVisit, uomControl);
        
        return uomKindUseTypeTransferCache;
    }
    
    public UnitOfMeasureEquivalentTransferCache getUnitOfMeasureEquivalentTransferCache() {
        if(uomEquivalentTransferCache == null)
            uomEquivalentTransferCache = new UnitOfMeasureEquivalentTransferCache(userVisit, uomControl);
        
        return uomEquivalentTransferCache;
    }
    
    public UnitOfMeasureTypeVolumeTransferCache getUnitOfMeasureTypeVolumeTransferCache() {
        if(uomTypeVolumeTransferCache == null)
            uomTypeVolumeTransferCache = new UnitOfMeasureTypeVolumeTransferCache(userVisit, uomControl);
        
        return uomTypeVolumeTransferCache;
    }
    
    public UnitOfMeasureTypeWeightTransferCache getUnitOfMeasureTypeWeightTransferCache() {
        if(uomTypeWeightTransferCache == null)
            uomTypeWeightTransferCache = new UnitOfMeasureTypeWeightTransferCache(userVisit, uomControl);
        
        return uomTypeWeightTransferCache;
    }
    
}
