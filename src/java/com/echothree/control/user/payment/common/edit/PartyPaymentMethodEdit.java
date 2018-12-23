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

package com.echothree.control.user.payment.common.edit;

import com.echothree.util.common.form.BaseEdit;

public interface PartyPaymentMethodEdit
        extends BaseEdit {
    
    String getDescription();
    void setDescription(String description);
    
    String getDeleteWhenUnused();
    void setDeleteWhenUnused(String deleteWhenUnused);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
    String getPersonalTitleId();
    void setPersonalTitleId(String personalTitleId);
    
    String getFirstName();
    void setFirstName(String firstName);
    
    String getMiddleName();
    void setMiddleName(String middleName);
    
    String getLastName();
    void setLastName(String lastName);
    
    String getNameSuffixId();
    void setNameSuffixId(String nameSuffixId);
    
    String getName();
    void setName(String name);
    
    String getNumber();
    void setNumber(String number);
    
    String getSecurityCode();
    void setSecurityCode(String securityCode);
    
    String getExpirationMonth();
    void setExpirationMonth(String expirationMonth);
    
    String getExpirationYear();
    void setExpirationYear(String expirationYear);
    
    String getBillingContactMechanismName();
    void setBillingContactMechanismName(String billingContactMechanismName);
    
    String getIssuerName();
    void setIssuerName(String issuerName);
    
    String getIssuerContactMechanismName();
    void setIssuerContactMechanismName(String issuerContactMechanismName);
    
}
