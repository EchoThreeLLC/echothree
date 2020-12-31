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

package com.echothree.model.control.communication.server.transfer;

import com.echothree.model.control.communication.common.transfer.CommunicationSourceTypeTransfer;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationSourceType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CommunicationSourceTypeTransferCache
        extends BaseCommunicationTransferCache<CommunicationSourceType, CommunicationSourceTypeTransfer> {
    
    /** Creates a new instance of CommunicationSourceTypeTransferCache */
    public CommunicationSourceTypeTransferCache(UserVisit userVisit, CommunicationControl communicationControl) {
        super(userVisit, communicationControl);
    }
    
    public CommunicationSourceTypeTransfer getCommunicationSourceTypeTransfer(CommunicationSourceType communicationSourceType) {
        CommunicationSourceTypeTransfer communicationSourceTypeTransfer = get(communicationSourceType);
        
        if(communicationSourceTypeTransfer == null) {
            String communicationSourceTypeName = communicationSourceType.getCommunicationSourceTypeName();
            Boolean isDefault = communicationSourceType.getIsDefault();
            Integer sortOrder = communicationSourceType.getSortOrder();
            String description = communicationControl.getBestCommunicationSourceTypeDescription(communicationSourceType, getLanguage());
            
            communicationSourceTypeTransfer = new CommunicationSourceTypeTransfer(communicationSourceTypeName, isDefault, sortOrder, description);
            put(communicationSourceType, communicationSourceTypeTransfer);
        }
        
        return communicationSourceTypeTransfer;
    }
    
}
