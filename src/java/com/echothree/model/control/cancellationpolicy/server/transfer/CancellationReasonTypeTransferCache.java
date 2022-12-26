// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationReasonTypeTransfer;
import com.echothree.model.control.cancellationpolicy.common.transfer.CancellationTypeTransfer;
import com.echothree.model.control.cancellationpolicy.server.control.CancellationPolicyControl;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReasonType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CancellationReasonTypeTransferCache
        extends BaseCancellationPolicyTransferCache<CancellationReasonType, CancellationReasonTypeTransfer> {
    
    /** Creates a new instance of CancellationReasonTypeTransferCache */
    public CancellationReasonTypeTransferCache(UserVisit userVisit, CancellationPolicyControl cancellationPolicyControl) {
        super(userVisit, cancellationPolicyControl);
    }
    
    public CancellationReasonTypeTransfer getCancellationReasonTypeTransfer(CancellationReasonType cancellationReasonType) {
        CancellationReasonTypeTransfer cancellationReasonTypeTransfer = get(cancellationReasonType);
        
        if(cancellationReasonTypeTransfer == null) {
            CancellationReasonTransfer cancellationReason = cancellationPolicyControl.getCancellationReasonTransfer(userVisit, cancellationReasonType.getCancellationReason());
            CancellationTypeTransfer cancellationType = cancellationPolicyControl.getCancellationTypeTransfer(userVisit, cancellationReasonType.getCancellationType());
            Boolean isDefault = cancellationReasonType.getIsDefault();
            Integer sortOrder = cancellationReasonType.getSortOrder();
            
            cancellationReasonTypeTransfer = new CancellationReasonTypeTransfer(cancellationReason, cancellationType, isDefault, sortOrder);
            put(cancellationReasonType, cancellationReasonTypeTransfer);
        }
        
        return cancellationReasonTypeTransfer;
    }
    
}
