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

package com.echothree.model.control.cancellationpolicy.server.transfer;

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationKindDescriptionTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationKindTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CancellationKindDescriptionTransferCache
        extends BaseCancellationPolicyDescriptionTransferCache<CancellationKindDescription, CancellationKindDescriptionTransfer> {
    
    /** Creates a new instance of CancellationKindDescriptionTransferCache */
    public CancellationKindDescriptionTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
    }
    
    public CancellationKindDescriptionTransfer getCancellationKindDescriptionTransfer(CancellationKindDescription cancellationKindDescription) {
        CancellationKindDescriptionTransfer cancellationKindDescriptionTransfer = get(cancellationKindDescription);
        
        if(cancellationKindDescriptionTransfer == null) {
            CancellationKindTransfer cancellationKindTransfer = cancellationPolicyControl.getCancellationKindTransfer(userVisit, cancellationKindDescription.getCancellationKind());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, cancellationKindDescription.getLanguage());
            
            cancellationKindDescriptionTransfer = new CancellationKindDescriptionTransfer(languageTransfer, cancellationKindTransfer, cancellationKindDescription.getDescription());
            put(cancellationKindDescription, cancellationKindDescriptionTransfer);
        }
        
        return cancellationKindDescriptionTransfer;
    }
    
}
