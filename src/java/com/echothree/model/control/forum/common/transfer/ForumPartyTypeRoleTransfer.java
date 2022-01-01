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

package com.echothree.model.control.forum.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ForumPartyTypeRoleTransfer
        extends BaseTransfer {
    
    private ForumTransfer forum;
    private PartyTypeTransfer partyType;
    private ForumRoleTypeTransfer forumRoleType;
    
    /** Creates a new instance of ForumPartyTypeRoleTransfer */
    public ForumPartyTypeRoleTransfer(ForumTransfer forum, PartyTypeTransfer partyType, ForumRoleTypeTransfer forumRoleType) {
        this.forum = forum;
        this.partyType = partyType;
        this.forumRoleType = forumRoleType;
    }
    
    public ForumTransfer getForum() {
        return forum;
    }
    
    public void setForum(ForumTransfer forum) {
        this.forum = forum;
    }
    
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }
    
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }
    
    public ForumRoleTypeTransfer getForumRoleType() {
        return forumRoleType;
    }
    
    public void setForumRoleType(ForumRoleTypeTransfer forumRoleType) {
        this.forumRoleType = forumRoleType;
    }
    
}
