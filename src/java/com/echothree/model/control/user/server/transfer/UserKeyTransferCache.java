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

package com.echothree.model.control.user.server.transfer;


import com.echothree.model.control.party.common.transfer.PartyRelationshipTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.common.transfer.UserKeyTransfer;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.user.server.entity.UserKey;
import com.echothree.model.data.user.server.entity.UserKeyDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UserKeyTransferCache
        extends BaseUserTransferCache<UserKey, UserKeyTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of UserKeyTransferCache */
    public UserKeyTransferCache(UserVisit userVisit, UserControl userControl) {
        super(userVisit, userControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public UserKeyTransfer getUserKeyTransfer(UserKey userKey) {
        UserKeyTransfer userKeyTransfer = get(userKey);
        
        if(userKeyTransfer == null) {
            UserKeyDetail userKeyDetail = userKey.getLastDetail();
            String userKeyName = userKeyDetail.getUserKeyName();
            Party party = userKeyDetail.getParty();
            PartyTransfer partyTransfer = party == null? null: partyControl.getPartyTransfer(userVisit, party);
            PartyRelationship partyRelationship = userKeyDetail.getPartyRelationship();
            PartyRelationshipTransfer partyRelationshipTransfer = partyRelationship == null? null: partyControl.getPartyRelationshipTransfer(userVisit, partyRelationship);
            
            userKeyTransfer = new UserKeyTransfer(userKeyName, partyTransfer, partyRelationshipTransfer);
            put(userKey, userKeyTransfer);
        }
        
        return userKeyTransfer;
    }
    
}
