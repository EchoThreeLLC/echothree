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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.remote.transfer.CurrencyDescriptionTransfer;
import com.echothree.model.control.accounting.remote.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.accounting.server.entity.CurrencyDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CurrencyDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<CurrencyDescription, CurrencyDescriptionTransfer> {
    
    /** Creates a new instance of CurrencyDescriptionTransferCache */
    public CurrencyDescriptionTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public CurrencyDescriptionTransfer getTransfer(CurrencyDescription currencyDescription) {
        CurrencyDescriptionTransfer currencyDescriptionTransfer = get(currencyDescription);
        
        if(currencyDescriptionTransfer == null) {
            CurrencyTransferCache currencyTransferCache = accountingControl.getAccountingTransferCaches(userVisit).getCurrencyTransferCache();
            CurrencyTransfer currencyTransfer = currencyTransferCache.getTransfer(currencyDescription.getCurrency());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, currencyDescription.getLanguage());
            
            currencyDescriptionTransfer = new CurrencyDescriptionTransfer(languageTransfer, currencyTransfer, currencyDescription.getDescription());
            put(currencyDescription, currencyDescriptionTransfer);
        }
        
        return currencyDescriptionTransfer;
    }
    
}
