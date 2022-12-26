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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.content.common.transfer.ContentCollectionDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentCollectionTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.content.server.entity.ContentCollectionDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContentCollectionDescriptionTransferCache
        extends BaseContentDescriptionTransferCache<ContentCollectionDescription, ContentCollectionDescriptionTransfer> {
    
    /** Creates a new instance of ContentCollectionDescriptionTransferCache */
    public ContentCollectionDescriptionTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
    }
    
    public ContentCollectionDescriptionTransfer getContentCollectionDescriptionTransfer(ContentCollectionDescription contentCollectionDescription) {
        ContentCollectionDescriptionTransfer contentCollectionDescriptionTransfer = get(contentCollectionDescription);
        
        if(contentCollectionDescriptionTransfer == null) {
            ContentCollectionTransfer contentCollectionTransfer = contentControl.getContentCollectionTransfer(userVisit, contentCollectionDescription.getContentCollection());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, contentCollectionDescription.getLanguage());
            
            contentCollectionDescriptionTransfer = new ContentCollectionDescriptionTransfer(languageTransfer, contentCollectionTransfer, contentCollectionDescription.getDescription());
            put(contentCollectionDescription, contentCollectionDescriptionTransfer);
        }
        
        return contentCollectionDescriptionTransfer;
    }
    
}
