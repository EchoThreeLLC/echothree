// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.party.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;

public class CompanyTransfer
        extends PartyTransfer {
    
    private String companyName;
    private String isDefault;
    private String sortOrder;
    
    /** Creates a new instance of CompanyTransfer */
    public CompanyTransfer(String partyName, PartyTypeTransfer partyType, LanguageTransfer preferredLanguage, CurrencyTransfer preferredCurrency, TimeZoneTransfer preferredTimeZone,
            DateTimeFormatTransfer preferredDateTimeFormat, PersonTransfer person, PartyGroupTransfer partyGroup, String companyName, String isDefault, String sortOrder) {
        super(partyName, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, person, partyGroup, null);

        this.companyName = companyName;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
