// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.content.common.transfer.ContentCollectionTransfer;
import com.echothree.model.control.content.common.transfer.ContentForumTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.content.server.entity.ContentForum;
import com.echothree.model.data.content.server.entity.ContentForumDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ContentForumTransferCache
        extends BaseContentTransferCache<ContentForum, ContentForumTransfer> {

    ForumControl forumControl = Session.getModelController(ForumControl.class);

    /** Creates a new instance of ContentForumTransferCache */
    public ContentForumTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);

        setIncludeEntityInstance(true);
    }

    public ContentForumTransfer getContentForumTransfer(ContentForum contentForum) {
        var contentForumTransfer = get(contentForum);
        
        if(contentForumTransfer == null) {
            var contentForumDetail = contentForum.getLastDetail();
            var contentCollection = contentControl.getContentCollectionTransfer(userVisit, contentForumDetail.getContentCollection());
            var forum = forumControl.getForumTransfer(userVisit, contentForumDetail.getForum());
            var isDefault = contentForumDetail.getIsDefault();
            
            contentForumTransfer = new ContentForumTransfer(contentCollection, forum, isDefault);
            put(contentForum, contentForumTransfer);
        }
        
        return contentForumTransfer;
    }

}
