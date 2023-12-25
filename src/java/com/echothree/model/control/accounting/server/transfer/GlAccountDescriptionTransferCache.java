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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.GlAccountDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.accounting.server.entity.GlAccountDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GlAccountDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<GlAccountDescription, GlAccountDescriptionTransfer> {
    
    /** Creates a new instance of GlAccountDescriptionTransferCache */
    public GlAccountDescriptionTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public GlAccountDescriptionTransfer getTransfer(GlAccountDescription glAccountDescription) {
        GlAccountDescriptionTransfer glAccountDescriptionTransfer = get(glAccountDescription);
        
        if(glAccountDescriptionTransfer == null) {
            GlAccountTransferCache glAccountTransferCache = accountingControl.getAccountingTransferCaches(userVisit).getGlAccountTransferCache();
            GlAccountTransfer glAccountTransfer = glAccountTransferCache.getTransfer(glAccountDescription.getGlAccount());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, glAccountDescription.getLanguage());
            
            glAccountDescriptionTransfer = new GlAccountDescriptionTransfer(languageTransfer, glAccountTransfer, glAccountDescription.getDescription());
            put(glAccountDescription, glAccountDescriptionTransfer);
        }
        
        return glAccountDescriptionTransfer;
    }
    
}
