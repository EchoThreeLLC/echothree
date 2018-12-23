// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUnitTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnit;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnitDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class HarmonizedTariffScheduleCodeUnitTransferCache
        extends BaseItemTransferCache<HarmonizedTariffScheduleCodeUnit, HarmonizedTariffScheduleCodeUnitTransfer> {
    
    /** Creates a new instance of HarmonizedTariffScheduleCodeUnitTransferCache */
    public HarmonizedTariffScheduleCodeUnitTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public HarmonizedTariffScheduleCodeUnitTransfer getTransfer(HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit) {
        HarmonizedTariffScheduleCodeUnitTransfer harmonizedTariffScheduleCodeUnitTransfer = get(harmonizedTariffScheduleCodeUnit);
        
        if(harmonizedTariffScheduleCodeUnitTransfer == null) {
            HarmonizedTariffScheduleCodeUnitDetail harmonizedTariffScheduleCodeUnitDetail = harmonizedTariffScheduleCodeUnit.getLastDetail();
            String harmonizedTariffScheduleCodeUnitName = harmonizedTariffScheduleCodeUnitDetail.getHarmonizedTariffScheduleCodeUnitName();
            Boolean isDefault = harmonizedTariffScheduleCodeUnitDetail.getIsDefault();
            Integer sortOrder = harmonizedTariffScheduleCodeUnitDetail.getSortOrder();
            String description = itemControl.getBestHarmonizedTariffScheduleCodeUnitDescription(harmonizedTariffScheduleCodeUnit, getLanguage());
            
            harmonizedTariffScheduleCodeUnitTransfer = new HarmonizedTariffScheduleCodeUnitTransfer(harmonizedTariffScheduleCodeUnitName, isDefault, sortOrder, description);
            put(harmonizedTariffScheduleCodeUnit, harmonizedTariffScheduleCodeUnitTransfer);
        }
        
        return harmonizedTariffScheduleCodeUnitTransfer;
    }
    
}
