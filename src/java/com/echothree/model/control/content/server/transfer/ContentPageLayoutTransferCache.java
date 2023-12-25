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

import com.echothree.model.control.content.common.transfer.ContentPageLayoutTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ContentPageLayoutTransferCache
        extends BaseContentTransferCache<ContentPageLayout, ContentPageLayoutTransfer> {
    
    /** Creates a new instance of ContentPageLayoutTransferCache */
    public ContentPageLayoutTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ContentPageLayoutTransfer getTransfer(ContentPageLayout contentPageLayout) {
        ContentPageLayoutTransfer contentPageLayoutTransfer = get(contentPageLayout);
        
        if(contentPageLayoutTransfer == null) {
            ContentPageLayoutDetail contentPageLayoutDetail = contentPageLayout.getLastDetail();
            String contentPageLayoutName = contentPageLayoutDetail.getContentPageLayoutName();
            Boolean isDefault = contentPageLayoutDetail.getIsDefault();
            Integer sortOrder = contentPageLayoutDetail.getSortOrder();
            String description = contentControl.getBestContentPageLayoutDescription(contentPageLayout, getLanguage());
            
            contentPageLayoutTransfer = new ContentPageLayoutTransfer(contentPageLayoutName, isDefault, sortOrder, description);
            put(contentPageLayout, contentPageLayoutTransfer);
        }
        
        return contentPageLayoutTransfer;
    }
    
}
