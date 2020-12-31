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

package com.echothree.model.control.picklist.server.transfer;

import com.echothree.model.control.picklist.common.transfer.PicklistAliasTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistAliasTypeTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTransfer;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.data.picklist.server.entity.PicklistAlias;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PicklistAliasTransferCache
        extends BasePicklistTransferCache<PicklistAlias, PicklistAliasTransfer> {
    
    /** Creates a new instance of PicklistAliasTransferCache */
    public PicklistAliasTransferCache(UserVisit userVisit, PicklistControl picklistControl) {
        super(userVisit, picklistControl);
    }
    
    public PicklistAliasTransfer getPicklistAliasTransfer(PicklistAlias picklistAlias) {
        PicklistAliasTransfer picklistAliasTransfer = get(picklistAlias);
        
        if(picklistAliasTransfer == null) {
            PicklistTransfer picklist = picklistControl.getPicklistTransfer(userVisit, picklistAlias.getPicklist());
            PicklistAliasTypeTransfer picklistAliasType = picklistControl.getPicklistAliasTypeTransfer(userVisit, picklistAlias.getPicklistAliasType());
            String alias = picklistAlias.getAlias();
            
            picklistAliasTransfer = new PicklistAliasTransfer(picklist, picklistAliasType, alias);
            put(picklistAlias, picklistAliasTransfer);
        }
        
        return picklistAliasTransfer;
    }
    
}
