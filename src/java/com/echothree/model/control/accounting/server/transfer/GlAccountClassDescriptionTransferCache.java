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

import com.echothree.model.control.accounting.common.transfer.GlAccountClassDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountClassTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.accounting.server.entity.GlAccountClassDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GlAccountClassDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<GlAccountClassDescription, GlAccountClassDescriptionTransfer> {
    
    /** Creates a new instance of GlAccountClassDescriptionTransferCache */
    public GlAccountClassDescriptionTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public GlAccountClassDescriptionTransfer getTransfer(GlAccountClassDescription glAccountClassDescription) {
        GlAccountClassDescriptionTransfer glAccountClassDescriptionTransfer = get(glAccountClassDescription);
        
        if(glAccountClassDescriptionTransfer == null) {
            GlAccountClassTransferCache glAccountClassTransferCache = accountingControl.getAccountingTransferCaches(userVisit).getGlAccountClassTransferCache();
            GlAccountClassTransfer glAccountClassTransfer = glAccountClassTransferCache.getTransfer(glAccountClassDescription.getGlAccountClass());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, glAccountClassDescription.getLanguage());
            
            glAccountClassDescriptionTransfer = new GlAccountClassDescriptionTransfer(languageTransfer, glAccountClassTransfer, glAccountClassDescription.getDescription());
            put(glAccountClassDescription, glAccountClassDescriptionTransfer);
        }
        
        return glAccountClassDescriptionTransfer;
    }
    
}
