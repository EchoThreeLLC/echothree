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

import com.echothree.model.control.content.common.transfer.ContentSectionDescriptionTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentSectionDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContentSectionDescriptionTransferCache
        extends BaseContentDescriptionTransferCache<ContentSectionDescription, ContentSectionDescriptionTransfer> {
    
    /** Creates a new instance of ContentSectionDescriptionTransferCache */
    public ContentSectionDescriptionTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
    }
    
    public ContentSectionDescriptionTransfer getContentSectionDescriptionTransfer(ContentSectionDescription contentSectionDescription) {
        var contentSectionDescriptionTransfer = get(contentSectionDescription);
        
        if(contentSectionDescriptionTransfer == null) {
            var contentSectionTransfer = contentControl.getContentSectionTransfer(userVisit, contentSectionDescription.getContentSection());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, contentSectionDescription.getLanguage());
            
            contentSectionDescriptionTransfer = new ContentSectionDescriptionTransfer(languageTransfer, contentSectionTransfer, contentSectionDescription.getDescription());
            put(contentSectionDescription, contentSectionDescriptionTransfer);
        }
        
        return contentSectionDescriptionTransfer;
    }
    
}
