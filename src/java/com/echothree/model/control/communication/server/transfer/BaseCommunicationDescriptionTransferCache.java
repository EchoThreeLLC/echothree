// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;

public abstract class BaseCommunicationDescriptionTransferCache<K extends BaseEntity, V extends BaseTransfer>
        extends BaseCommunicationTransferCache<K, V> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of BaseCommunicationDescriptionTransferCache */
    protected BaseCommunicationDescriptionTransferCache(UserVisit userVisit, CommunicationControl communicationControl) {
        super(userVisit, communicationControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
}
