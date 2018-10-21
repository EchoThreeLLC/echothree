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

package com.echothree.model.control.period.server.transfer;

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.period.remote.transfer.PeriodDescriptionTransfer;
import com.echothree.model.control.period.remote.transfer.PeriodTransfer;
import com.echothree.model.control.period.server.PeriodControl;
import com.echothree.model.data.period.server.entity.PeriodDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PeriodDescriptionTransferCache
        extends BasePeriodDescriptionTransferCache<PeriodDescription, PeriodDescriptionTransfer> {
    
    /** Creates a new instance of PeriodDescriptionTransferCache */
    public PeriodDescriptionTransferCache(UserVisit userVisit, PeriodControl periodControl) {
        super(userVisit, periodControl);
    }
    
    public PeriodDescriptionTransfer getPeriodDescriptionTransfer(PeriodDescription periodDescription) {
        PeriodDescriptionTransfer periodDescriptionTransfer = get(periodDescription);
        
        if(periodDescriptionTransfer == null) {
            PeriodTransfer periodTransfer = periodControl.getPeriodTransfer(userVisit, periodDescription.getPeriod());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, periodDescription.getLanguage());
            
            periodDescriptionTransfer = new PeriodDescriptionTransfer(languageTransfer, periodTransfer, periodDescription.getDescription());
            put(periodDescription, periodDescriptionTransfer);
        }
        
        return periodDescriptionTransfer;
    }
    
}
