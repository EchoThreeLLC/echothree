// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.returnpolicy.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnTypeDescriptionTransfer;
import com.echothree.model.control.returnpolicy.common.transfer.ReturnTypeTransfer;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ReturnTypeDescriptionTransferCache
        extends BaseReturnPolicyDescriptionTransferCache<ReturnTypeDescription, ReturnTypeDescriptionTransfer> {
    
    /** Creates a new instance of ReturnTypeDescriptionTransferCache */
    public ReturnTypeDescriptionTransferCache(UserVisit userVisit, ReturnPolicyControl returnPolicyControl) {
        super(userVisit, returnPolicyControl);
    }
    
    public ReturnTypeDescriptionTransfer getReturnTypeDescriptionTransfer(ReturnTypeDescription returnTypeDescription) {
        ReturnTypeDescriptionTransfer returnTypeDescriptionTransfer = get(returnTypeDescription);
        
        if(returnTypeDescriptionTransfer == null) {
            ReturnTypeTransfer returnTypeTransfer = returnPolicyControl.getReturnTypeTransfer(userVisit, returnTypeDescription.getReturnType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, returnTypeDescription.getLanguage());
            
            returnTypeDescriptionTransfer = new ReturnTypeDescriptionTransfer(languageTransfer, returnTypeTransfer, returnTypeDescription.getDescription());
            put(returnTypeDescription, returnTypeDescriptionTransfer);
        }
        
        return returnTypeDescriptionTransfer;
    }
    
}
