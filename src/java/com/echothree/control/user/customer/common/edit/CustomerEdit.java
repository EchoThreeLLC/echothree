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

package com.echothree.control.user.customer.common.edit;

import com.echothree.control.user.customer.common.spec.CustomerTypeSpec;
import com.echothree.control.user.party.common.edit.PartyEdit;
import com.echothree.control.user.party.common.edit.PartyGroupEdit;

public interface CustomerEdit
        extends CustomerTypeSpec, PartyEdit, PartyGroupEdit {
    
    String getCancellationPolicyName();
    void setCancellationPolicyName(String cancellationPolicyName);

    String getReturnPolicyName();
    void setReturnPolicyName(String returnPolicyName);

    String getArGlAccountName();
    void setArGlAccountName(String arGlAccountName);
    
    String getHoldUntilComplete();
    void setHoldUntilComplete(String holdUntilComplete);

    String getAllowBackorders();
    void setAllowBackorders(String allowBackorders);

    String getAllowSubstitutions();
    void setAllowSubstitutions(String allowSubstitutions);

    String getAllowCombiningShipments();
    void setAllowCombiningShipments(String allowCombiningShipments);

    String getRequireReference();
    void setRequireReference(String requireReference);
    
    String getAllowReferenceDuplicates();
    void setAllowReferenceDuplicates(String allowReferenceDuplicates);
    
    String getReferenceValidationPattern();
    void setReferenceValidationPattern(String referenceValidationPattern);
    
}
