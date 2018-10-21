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

package com.echothree.model.control.tag.server.transfer;

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.tag.remote.transfer.TagScopeDescriptionTransfer;
import com.echothree.model.control.tag.remote.transfer.TagScopeTransfer;
import com.echothree.model.control.tag.server.TagControl;
import com.echothree.model.data.tag.server.entity.TagScopeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TagScopeDescriptionTransferCache
        extends BaseTagDescriptionTransferCache<TagScopeDescription, TagScopeDescriptionTransfer> {
    
    /** Creates a new instance of TagScopeDescriptionTransferCache */
    public TagScopeDescriptionTransferCache(UserVisit userVisit, TagControl tagControl) {
        super(userVisit, tagControl);
    }
    
    public TagScopeDescriptionTransfer getTagScopeDescriptionTransfer(TagScopeDescription tagScopeDescription) {
        TagScopeDescriptionTransfer tagScopeDescriptionTransfer = get(tagScopeDescription);
        
        if(tagScopeDescriptionTransfer == null) {
            TagScopeTransfer tagScopeTransfer = tagControl.getTagScopeTransfer(userVisit, tagScopeDescription.getTagScope());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, tagScopeDescription.getLanguage());
            
            tagScopeDescriptionTransfer = new TagScopeDescriptionTransfer(languageTransfer, tagScopeTransfer, tagScopeDescription.getDescription());
            put(tagScopeDescription, tagScopeDescriptionTransfer);
        }
        
        return tagScopeDescriptionTransfer;
    }
    
}
