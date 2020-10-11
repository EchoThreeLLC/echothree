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

package com.echothree.model.control.forum.server.transfer;

import com.echothree.model.control.forum.common.transfer.ForumPartyTypeRoleTransfer;
import com.echothree.model.control.forum.common.transfer.ForumRoleTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.forum.server.entity.ForumPartyTypeRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ForumPartyTypeRoleTransferCache
        extends BaseForumTransferCache<ForumPartyTypeRole, ForumPartyTypeRoleTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of ForumPartyTypeRoleTransferCache */
    public ForumPartyTypeRoleTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
        
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }
    
    public ForumPartyTypeRoleTransfer getForumPartyTypeRoleTransfer(ForumPartyTypeRole forumPartyTypeRole) {
        ForumPartyTypeRoleTransfer forumPartyTypeRoleTransfer = get(forumPartyTypeRole);
        
        if(forumPartyTypeRoleTransfer == null) {
            ForumTransfer forum = forumControl.getForumTransfer(userVisit, forumPartyTypeRole.getForum());
            PartyTypeTransfer partyType = partyControl.getPartyTypeTransfer(userVisit, forumPartyTypeRole.getPartyType());
            ForumRoleTypeTransfer forumRoleType = forumControl.getForumRoleTypeTransfer(userVisit, forumPartyTypeRole.getForumRoleType());
            
            forumPartyTypeRoleTransfer = new ForumPartyTypeRoleTransfer(forum, partyType, forumRoleType);
            put(forumPartyTypeRole, forumPartyTypeRoleTransfer);
        }
        
        return forumPartyTypeRoleTransfer;
    }
    
}
