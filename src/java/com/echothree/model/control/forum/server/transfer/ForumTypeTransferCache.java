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

import com.echothree.model.control.forum.common.transfer.ForumTypeTransfer;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ForumTypeTransferCache
        extends BaseForumTransferCache<ForumType, ForumTypeTransfer> {
    
    /** Creates a new instance of ForumTypeTransferCache */
    public ForumTypeTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
    }
    
    public ForumTypeTransfer getForumTypeTransfer(ForumType forumType) {
        ForumTypeTransfer forumTypeTransfer = get(forumType);
        
        if(forumTypeTransfer == null) {
            String forumTypeName = forumType.getForumTypeName();
            Boolean isDefault = forumType.getIsDefault();
            Integer sortOrder = forumType.getSortOrder();
            String description = forumControl.getBestForumTypeDescription(forumType, getLanguage());
            
            forumTypeTransfer = new ForumTypeTransfer(forumTypeName, isDefault, sortOrder, description);
            put(forumType, forumTypeTransfer);
        }
        
        return forumTypeTransfer;
    }
    
}
