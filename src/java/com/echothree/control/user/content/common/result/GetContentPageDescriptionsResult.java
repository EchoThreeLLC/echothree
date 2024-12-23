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

package com.echothree.control.user.content.common.result;

import com.echothree.model.control.content.common.transfer.ContentCollectionTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageTransfer;
import com.echothree.model.control.content.common.transfer.ContentSectionTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetContentPageDescriptionsResult
        extends BaseResult {
    
    ContentCollectionTransfer getContentCollection();
    void setContentCollection(ContentCollectionTransfer contentCollection);
    
    ContentSectionTransfer getContentSection();
    void setContentSection(ContentSectionTransfer contentSection);
    
    ContentPageTransfer getContentPage();
    void setContentPage(ContentPageTransfer contentPage);
    
    List<ContentPageDescriptionTransfer> getContentPageDescriptions();
    void setContentPageDescriptions(List<ContentPageDescriptionTransfer> contentPageDescriptions);
    
}
