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

package com.echothree.model.control.forum.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ForumMessagePartTypeTransfer
        extends BaseTransfer {
    
    private String forumMessagePartTypeName;
    private MimeTypeUsageTypeTransfer mimeTypeUsageType;
    private Integer sortOrder;
    
    /** Creates a new instance of ForumMessagePartTypeTransfer */
    public ForumMessagePartTypeTransfer(String forumMessagePartTypeName, MimeTypeUsageTypeTransfer mimeTypeUsageType,
            Integer sortOrder) {
        this.forumMessagePartTypeName = forumMessagePartTypeName;
        this.mimeTypeUsageType = mimeTypeUsageType;
        this.sortOrder = sortOrder;
    }
    
    public String getForumMessagePartTypeName() {
        return forumMessagePartTypeName;
    }
    
    public void setForumMessagePartTypeName(String forumMessagePartTypeName) {
        this.forumMessagePartTypeName = forumMessagePartTypeName;
    }
    
    public MimeTypeUsageTypeTransfer getMimeTypeUsageType() {
        return mimeTypeUsageType;
    }
    
    public void setMimeTypeUsageType(MimeTypeUsageTypeTransfer mimeTypeUsageType) {
        this.mimeTypeUsageType = mimeTypeUsageType;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
