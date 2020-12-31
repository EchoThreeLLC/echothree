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

package com.echothree.control.user.tax.common.edit;

import com.echothree.control.user.tax.common.spec.TaxSpec;

public interface TaxEdit
        extends TaxSpec, TaxDescriptionEdit {
    
    String getContactMechanismPurposeName();
    void setContactMechanismPurposeName(String contactMechanismPurposeName);
    
    String getGlAccountName();
    void setGlAccountName(String glAccountName);
    
    String getIncludeShippingCharge();
    void setIncludeShippingCharge(String includeShippingCharge);
    
    String getIncludeProcessingCharge();
    void setIncludeProcessingCharge(String includeProcessingCharge);
    
    String getIncludeInsuranceCharge();
    void setIncludeInsuranceCharge(String includeInsuranceCharge);
    
    String getPercent();
    void setPercent(String percent);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
