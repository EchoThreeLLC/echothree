// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class ForumGroupTransfer
        extends BaseTransfer {
    
    private String forumGroupName;
    private IconTransfer icon;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<ForumTransfer> forums;
    
    /** Creates a new instance of ForumGroupTransfer */
    public ForumGroupTransfer(String forumGroupName, IconTransfer icon, Integer sortOrder, String description) {
        this.forumGroupName = forumGroupName;
        this.icon = icon;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getForumGroupName() {
        return forumGroupName;
    }
    
    public void setForumGroupName(String forumGroupName) {
        this.forumGroupName = forumGroupName;
    }
    
    public IconTransfer getIcon() {
        return icon;
    }
    
    public void setIcon(IconTransfer icon) {
        this.icon = icon;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ListWrapper<ForumTransfer> getForums() {
        return forums;
    }
    
    public void setForums(ListWrapper<ForumTransfer> forums) {
        this.forums = forums;
    }
    
}
