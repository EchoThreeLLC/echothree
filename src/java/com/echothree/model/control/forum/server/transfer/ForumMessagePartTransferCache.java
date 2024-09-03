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

package com.echothree.model.control.forum.server.transfer;

import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.forum.server.entity.ForumBlobMessagePart;
import com.echothree.model.data.forum.server.entity.ForumClobMessagePart;
import com.echothree.model.data.forum.server.entity.ForumMessagePart;
import com.echothree.model.data.forum.server.entity.ForumMessagePartDetail;
import com.echothree.model.data.forum.server.entity.ForumStringMessagePart;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class ForumMessagePartTransferCache
        extends BaseForumTransferCache<ForumMessagePart, ForumMessagePartTransfer> {
    
    CoreControl coreControl;
    PartyControl partyControl;
    boolean includeBlob;
    boolean includeClob;
    boolean includeString;
    
    /** Creates a new instance of ForumMessagePartTransferCache */
    public ForumMessagePartTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
        
        coreControl = Session.getModelController(CoreControl.class);
        partyControl = Session.getModelController(PartyControl.class);
        
        var options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(ForumOptions.ForumMessagePartIncludeBlob);
            includeClob = options.contains(ForumOptions.ForumMessagePartIncludeClob);
            includeString = options.contains(ForumOptions.ForumMessagePartIncludeString);
        }
    }
    
    public ForumMessagePartTransfer getForumMessagePartTransfer(ForumMessagePart forumMessagePart) {
        var forumMessagePartTransfer = get(forumMessagePart);
        
        if(forumMessagePartTransfer == null) {
            var forumMessagePartDetail = forumMessagePart.getLastDetail();
            var forumMessageTransfer = forumControl.getForumMessageTransfer(userVisit, forumMessagePartDetail.getForumMessage());
            var forumMessagePartTypeTransfer = forumControl.getForumMessagePartTypeTransfer(userVisit, forumMessagePartDetail.getForumMessagePartType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, forumMessagePartDetail.getLanguage());
            var mimeType = forumMessagePartDetail.getMimeType();
            ByteArray blobMessagePart = null;
            String clobMessagePart = null;
            String stringMessagePart = null;
            
            if(includeBlob) {
                var forumBlobMessagePart = forumControl.getForumBlobMessagePart(forumMessagePart);
                
                if(forumBlobMessagePart != null) {
                    blobMessagePart = forumBlobMessagePart.getBlob();
                }
            }
            
            if(includeClob) {
                var forumClobMessagePart = forumControl.getForumClobMessagePart(forumMessagePart);
                
                if(forumClobMessagePart != null) {
                    var preferredClobMimeType = session.getPreferredClobMimeType();
                    
                    clobMessagePart = forumClobMessagePart.getClob();
                    
                    if(preferredClobMimeType != null) {
                        var preferredClobMimeTypeName = preferredClobMimeType.getLastDetail().getMimeTypeName();
                        
                        if(preferredClobMimeTypeName.contains(MimeTypes.TEXT_HTML.mimeTypeName())) {
                            clobMessagePart = StringUtils.getInstance().convertToHtml(clobMessagePart, mimeType.getLastDetail().getMimeTypeName());
                            mimeType = preferredClobMimeType;
                        }
                    }
                }
            }
            
            if(includeString) {
                var forumStringMessagePart = forumControl.getForumStringMessagePart(forumMessagePart);
                
                if(forumStringMessagePart != null) {
                    stringMessagePart = forumStringMessagePart.getString();
                }
            }

            var mimeTypeTransfer = mimeType == null ? null : coreControl.getMimeTypeTransfer(userVisit, mimeType);
            
            forumMessagePartTransfer = new ForumMessagePartTransfer(forumMessageTransfer, forumMessagePartTypeTransfer,
                    languageTransfer, mimeTypeTransfer, blobMessagePart, clobMessagePart, stringMessagePart);
            put(forumMessagePart, forumMessagePartTransfer);
        }
        
        return forumMessagePartTransfer;
    }
    
}
