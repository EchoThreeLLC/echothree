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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache
        extends BaseItemDescriptionTransferCache<HarmonizedTariffScheduleCodeUseTypeDescription, HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer> {
    
    /** Creates a new instance of HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache */
    public HarmonizedTariffScheduleCodeUseTypeDescriptionTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer getTransfer(HarmonizedTariffScheduleCodeUseTypeDescription harmonizedTariffScheduleCodeUseTypeDescription) {
        HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer harmonizedTariffScheduleCodeUseTypeDescriptionTransfer = get(harmonizedTariffScheduleCodeUseTypeDescription);
        
        if(harmonizedTariffScheduleCodeUseTypeDescriptionTransfer == null) {
            HarmonizedTariffScheduleCodeUseTypeTransfer harmonizedTariffScheduleCodeUseTypeTransfer = itemControl.getHarmonizedTariffScheduleCodeUseTypeTransfer(userVisit, harmonizedTariffScheduleCodeUseTypeDescription.getHarmonizedTariffScheduleCodeUseType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, harmonizedTariffScheduleCodeUseTypeDescription.getLanguage());
            
            harmonizedTariffScheduleCodeUseTypeDescriptionTransfer = new HarmonizedTariffScheduleCodeUseTypeDescriptionTransfer(languageTransfer, harmonizedTariffScheduleCodeUseTypeTransfer, harmonizedTariffScheduleCodeUseTypeDescription.getDescription());
            put(harmonizedTariffScheduleCodeUseTypeDescription, harmonizedTariffScheduleCodeUseTypeDescriptionTransfer);
        }
        
        return harmonizedTariffScheduleCodeUseTypeDescriptionTransfer;
    }
    
}
