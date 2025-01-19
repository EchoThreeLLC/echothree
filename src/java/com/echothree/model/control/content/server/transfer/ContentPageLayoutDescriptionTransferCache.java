// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.content.common.transfer.ContentPageLayoutDescriptionTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContentPageLayoutDescriptionTransferCache
        extends BaseContentDescriptionTransferCache<ContentPageLayoutDescription, ContentPageLayoutDescriptionTransfer> {
    
    /** Creates a new instance of ContentPageLayoutDescriptionTransferCache */
    public ContentPageLayoutDescriptionTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
    }
    
    public ContentPageLayoutDescriptionTransfer getTransfer(ContentPageLayoutDescription contentPageLayoutDescription) {
        var contentPageLayoutDescriptionTransfer = get(contentPageLayoutDescription);
        
        if(contentPageLayoutDescriptionTransfer == null) {
            var contentPageLayoutTransferCache = contentControl.getContentTransferCaches(userVisit).getContentPageLayoutTransferCache();
            var contentPageLayoutTransfer = contentPageLayoutTransferCache.getTransfer(contentPageLayoutDescription.getContentPageLayout());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, contentPageLayoutDescription.getLanguage());
            
            contentPageLayoutDescriptionTransfer = new ContentPageLayoutDescriptionTransfer(languageTransfer, contentPageLayoutTransfer, contentPageLayoutDescription.getDescription());
            put(contentPageLayoutDescription, contentPageLayoutDescriptionTransfer);
        }
        
        return contentPageLayoutDescriptionTransfer;
    }
    
}
