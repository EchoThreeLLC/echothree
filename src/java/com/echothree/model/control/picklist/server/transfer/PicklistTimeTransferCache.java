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

package com.echothree.model.control.picklist.server.transfer;

import com.echothree.model.control.picklist.common.transfer.PicklistTimeTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTimeTypeTransfer;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.data.picklist.server.entity.PicklistTime;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PicklistTimeTransferCache
        extends BasePicklistTransferCache<PicklistTime, PicklistTimeTransfer> {
    
    /** Creates a new instance of PicklistTimeTransferCache */
    public PicklistTimeTransferCache(UserVisit userVisit, PicklistControl picklistControl) {
        super(userVisit, picklistControl);
    }
    
    public PicklistTimeTransfer getPicklistTimeTransfer(PicklistTime picklistTime) {
        PicklistTimeTransfer picklistTimeTransfer = get(picklistTime);
        
        if(picklistTimeTransfer == null) {
            PicklistTimeTypeTransfer picklistTimeType = picklistControl.getPicklistTimeTypeTransfer(userVisit, picklistTime.getPicklistTimeType());
            Long unformattedTime = picklistTime.getTime();
            String time = formatTypicalDateTime(unformattedTime);
            
            picklistTimeTransfer = new PicklistTimeTransfer(picklistTimeType, unformattedTime, time);
            put(picklistTime, picklistTimeTransfer);
        }
        
        return picklistTimeTransfer;
    }
    
}
