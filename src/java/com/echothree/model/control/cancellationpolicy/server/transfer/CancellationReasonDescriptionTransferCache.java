// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.cancellationpolicy.server.transfer;

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonDescriptionTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonTransfer;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CancellationReasonDescriptionTransferCache
        extends BaseCancellationPolicyDescriptionTransferCache<CancellationReasonDescription, CancellationReasonDescriptionTransfer> {
    
    /** Creates a new instance of CancellationReasonDescriptionTransferCache */
    public CancellationReasonDescriptionTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
    }
    
    public CancellationReasonDescriptionTransfer getCancellationReasonDescriptionTransfer(CancellationReasonDescription cancellationReasonDescription) {
        CancellationReasonDescriptionTransfer cancellationReasonDescriptionTransfer = get(cancellationReasonDescription);
        
        if(cancellationReasonDescriptionTransfer == null) {
            CancellationReasonTransfer cancellationReasonTransfer = cancellationPolicyControl.getCancellationReasonTransfer(userVisit, cancellationReasonDescription.getCancellationReason());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, cancellationReasonDescription.getLanguage());
            
            cancellationReasonDescriptionTransfer = new CancellationReasonDescriptionTransfer(languageTransfer, cancellationReasonTransfer, cancellationReasonDescription.getDescription());
            put(cancellationReasonDescription, cancellationReasonDescriptionTransfer);
        }
        
        return cancellationReasonDescriptionTransfer;
    }
    
}
