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

package com.echothree.model.control.period.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.period.common.transfer.PeriodTypeDescriptionTransfer;
import com.echothree.model.control.period.common.transfer.PeriodTypeTransfer;
import com.echothree.model.control.period.server.PeriodControl;
import com.echothree.model.data.period.server.entity.PeriodTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PeriodTypeDescriptionTransferCache
        extends BasePeriodDescriptionTransferCache<PeriodTypeDescription, PeriodTypeDescriptionTransfer> {
    
    /** Creates a new instance of PeriodTypeDescriptionTransferCache */
    public PeriodTypeDescriptionTransferCache(UserVisit userVisit, PeriodControl periodControl) {
        super(userVisit, periodControl);
    }
    
    public PeriodTypeDescriptionTransfer getPeriodTypeDescriptionTransfer(PeriodTypeDescription periodTypeDescription) {
        PeriodTypeDescriptionTransfer periodTypeDescriptionTransfer = get(periodTypeDescription);
        
        if(periodTypeDescriptionTransfer == null) {
            PeriodTypeTransfer periodTypeTransfer = periodControl.getPeriodTypeTransfer(userVisit, periodTypeDescription.getPeriodType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, periodTypeDescription.getLanguage());
            
            periodTypeDescriptionTransfer = new PeriodTypeDescriptionTransfer(languageTransfer, periodTypeTransfer, periodTypeDescription.getDescription());
            put(periodTypeDescription, periodTypeDescriptionTransfer);
        }
        
        return periodTypeDescriptionTransfer;
    }
    
}
