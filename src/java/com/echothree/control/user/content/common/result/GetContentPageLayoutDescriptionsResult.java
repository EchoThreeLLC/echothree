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

package com.echothree.control.user.content.common.result;

import com.echothree.model.control.content.common.transfer.ContentPageLayoutDescriptionTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageLayoutTransfer;
import com.echothree.util.common.command.BaseResult;
import java.util.List;

public interface GetContentPageLayoutDescriptionsResult
        extends BaseResult {
    
    ContentPageLayoutTransfer getContentPageLayout();
    void setContentPageLayout(ContentPageLayoutTransfer contentPageLayout);
    
    List<ContentPageLayoutDescriptionTransfer> getContentPageLayoutDescriptions();
    void setContentPageLayoutDescriptions(List<ContentPageLayoutDescriptionTransfer> contentPageLayoutDescriptions);
    
}
