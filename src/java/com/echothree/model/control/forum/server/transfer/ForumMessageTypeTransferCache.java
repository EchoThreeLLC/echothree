// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.forum.remote.transfer.ForumMessageTypeTransfer;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessageType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ForumMessageTypeTransferCache
        extends BaseForumTransferCache<ForumMessageType, ForumMessageTypeTransfer> {
    
    /** Creates a new instance of ForumMessageTypeTransferCache */
    public ForumMessageTypeTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
    }
    
    public ForumMessageTypeTransfer getForumMessageTypeTransfer(ForumMessageType forumMessageType) {
        ForumMessageTypeTransfer forumMessageTypeTransfer = get(forumMessageType);
        
        if(forumMessageTypeTransfer == null) {
            String forumMessageTypeName = forumMessageType.getForumMessageTypeName();
            Boolean isDefault = forumMessageType.getIsDefault();
            Integer sortOrder = forumMessageType.getSortOrder();
            String description = forumControl.getBestForumMessageTypeDescription(forumMessageType, getLanguage());
            
            forumMessageTypeTransfer = new ForumMessageTypeTransfer(forumMessageTypeName, isDefault, sortOrder, description);
            put(forumMessageType, forumMessageTypeTransfer);
        }
        
        return forumMessageTypeTransfer;
    }
    
}
