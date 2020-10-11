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

package com.echothree.model.control.tag.server.transfer;

import com.echothree.model.control.tag.common.TagOptions;
import com.echothree.model.control.tag.common.transfer.TagScopeTransfer;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.model.data.tag.server.entity.TagScopeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Set;

public class TagScopeTransferCache
        extends BaseTagTransferCache<TagScope, TagScopeTransfer> {
    
    boolean includeTags;
    
    /** Creates a new instance of TagScopeTransferCache */
    public TagScopeTransferCache(UserVisit userVisit, TagControl tagControl) {
        super(userVisit, tagControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeTags = options.contains(TagOptions.TagScopeIncludeTags);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public TagScopeTransfer getTagScopeTransfer(TagScope tagScope) {
        TagScopeTransfer tagScopeTransfer = get(tagScope);
        
        if(tagScopeTransfer == null) {
            TagScopeDetail tagScopeDetail = tagScope.getLastDetail();
            String tagScopeName = tagScopeDetail.getTagScopeName();
            Boolean isDefault = tagScopeDetail.getIsDefault();
            Integer sortOrder = tagScopeDetail.getSortOrder();
            String description = tagControl.getBestTagScopeDescription(tagScope, getLanguage());
            
            tagScopeTransfer = new TagScopeTransfer(tagScopeName, isDefault, sortOrder, description);
            put(tagScope, tagScopeTransfer);
            
            if(includeTags) {
                tagScopeTransfer.setTags(new ListWrapper<>(tagControl.getTagTransfersByTagScope(userVisit, tagScope)));
            }
        }
        
        return tagScopeTransfer;
    }
    
}
