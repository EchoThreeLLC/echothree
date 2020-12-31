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

package com.echothree.control.user.party.common.result;

import com.echothree.util.common.command.BaseResult;

public interface CreateCustomerResult
        extends BaseResult {
    
    /** entityRef may be null if the requested username already exists, and the party
     * that it belongs to is not an customer.
     */
    String getEntityRef();
    void setEntityRef(String entityRef);
    
    /** customerName may be null if the requested username already exists, and the party
     * that it belongs to is not an customer.
     */
    String getCustomerName();
    void setCustomerName(String customerName);
    
    String getPartyName();
    void setPartyName(String partyName);
    
}
