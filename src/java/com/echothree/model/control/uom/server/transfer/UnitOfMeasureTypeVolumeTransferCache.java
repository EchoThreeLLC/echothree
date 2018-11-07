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

package com.echothree.model.control.uom.server.transfer;

import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeVolumeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeVolume;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UnitOfMeasureTypeVolumeTransferCache
        extends BaseUomTransferCache<UnitOfMeasureTypeVolume, UnitOfMeasureTypeVolumeTransfer> {
    
    /** Creates a new instance of UnitOfMeasureTypeVolumeTransferCache */
    public UnitOfMeasureTypeVolumeTransferCache(UserVisit userVisit, UomControl uomControl) {
        super(userVisit, uomControl);
    }
    
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolumeTransfer(UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        UnitOfMeasureTypeVolumeTransfer unitOfMeasureTypeVolumeTransfer = get(unitOfMeasureTypeVolume);
        
        if(unitOfMeasureTypeVolumeTransfer == null) {
            UnitOfMeasureTypeTransfer unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureTypeVolume.getUnitOfMeasureType());
            UnitOfMeasureKind volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
            String height = formatUnitOfMeasure(volumeUnitOfMeasureKind, unitOfMeasureTypeVolume.getHeight());
            String width = formatUnitOfMeasure(volumeUnitOfMeasureKind, unitOfMeasureTypeVolume.getWidth());
            String depth = formatUnitOfMeasure(volumeUnitOfMeasureKind, unitOfMeasureTypeVolume.getDepth());
            
            unitOfMeasureTypeVolumeTransfer = new UnitOfMeasureTypeVolumeTransfer(unitOfMeasureTypeTransfer, height, width, depth);
            put(unitOfMeasureTypeVolume, unitOfMeasureTypeVolumeTransfer);
        }
        
        return unitOfMeasureTypeVolumeTransfer;
    }
    
}
