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

import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.control.content.common.ContentProperties;
import com.echothree.model.control.content.common.transfer.ContentPageAreaTransfer;
import com.echothree.model.control.content.common.transfer.ContentPageTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.transfer.MapWrapperBuilder;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContentPageTransferCache
        extends BaseContentTransferCache<ContentPage, ContentPageTransfer> {

    boolean includeContentPageAreas;

    TransferProperties transferProperties;
    boolean filterContentSection;
    boolean filterContentPageName;
    boolean filterContentPageLayout;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of ContentPageTransferCache */
    public ContentPageTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);

        var options = session.getOptions();
        if(options != null) {
            includeContentPageAreas = options.contains(ContentOptions.ContentPageIncludeContentPageAreas);
            setIncludeUuid(options.contains(ContentOptions.ContentPageIncludeUuid));
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentPageIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentPageIncludeTagScopes));
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(ContentPageTransfer.class);
            
            if(properties != null) {
                filterContentSection = !properties.contains(ContentProperties.CONTENT_SECTION);
                filterContentPageName = !properties.contains(ContentProperties.CONTENT_PAGE_NAME);
                filterContentPageLayout = !properties.contains(ContentProperties.CONTENT_PAGE_LAYOUT);
                filterIsDefault = !properties.contains(ContentProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(ContentProperties.SORT_ORDER);
                filterDescription = !properties.contains(ContentProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(ContentProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }

    public ContentPageTransfer getContentPageTransfer(ContentPage contentPage) {
        var contentPageTransfer = get(contentPage);
        
        if(contentPageTransfer == null) {
            var contentPageDetail = contentPage.getLastDetail();
            var contentSection = filterContentSection ? null : contentControl.getContentSectionTransfer(userVisit, contentPageDetail.getContentSection());
            var contentPageName = filterContentPageName ? null : contentPageDetail.getContentPageName();
            var contentPageLayout = filterContentPageLayout ? null : contentControl.getContentPageLayoutTransfer(userVisit, contentPageDetail.getContentPageLayout());
            var isDefault = filterIsDefault ? null : contentPageDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : contentPageDetail.getSortOrder();
            var description = filterDescription ? null : contentControl.getBestContentPageDescription(contentPage, getLanguage());
            
            contentPageTransfer = new ContentPageTransfer(contentSection, contentPageName, contentPageLayout, isDefault, sortOrder, description);
            put(contentPage, contentPageTransfer);

            if(includeContentPageAreas) {
                var contentPageAreaTransfers = contentControl.getContentPageAreaTransfersByContentPage(userVisit, contentPage, getLanguage());
                Map<String, ContentPageAreaTransfer> contentPageAreas = new LinkedHashMap<>(contentPageAreaTransfers.size());

                contentPageAreaTransfers.forEach((contentPageAreaTransfer) -> {
                    contentPageAreas.put(contentPageAreaTransfer.getContentPageLayoutArea().getSortOrder().toString(), contentPageAreaTransfer);
                });

                contentPageTransfer.setContentPageAreas(MapWrapperBuilder.getInstance().filter(transferProperties, contentPageAreas));
            }
        }
        
        return contentPageTransfer;
    }
    
}
