// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.content.common.transfer.ContentCollectionTransfer;
import com.echothree.model.control.content.common.transfer.ContentSectionTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentSectionDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.transfer.ListWrapperBuilder;
import java.util.Set;

public class ContentSectionTransferCache
        extends BaseContentTransferCache<ContentSection, ContentSectionTransfer> {
    
    boolean includeContentPages;
    
    TransferProperties transferProperties;
    boolean filterContentCollection;
    boolean filterContentSectionName;
    boolean filterParentContentSection;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of ContentSectionTransferCache */
    public ContentSectionTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);

        var options = session.getOptions();
        if(options != null) {
            includeContentPages = options.contains(ContentOptions.ContentSectionIncludeContentPages);
            setIncludeKey(options.contains(ContentOptions.ContentSectionIncludeKey));
            setIncludeGuid(options.contains(ContentOptions.ContentSectionIncludeGuid));
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentSectionIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentSectionIncludeTagScopes));
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(ContentSectionTransfer.class);
            
            if(properties != null) {
                filterContentCollection = !properties.contains(ContentProperties.CONTENT_COLLECTION);
                filterContentSectionName = !properties.contains(ContentProperties.CONTENT_SECTION_NAME);
                filterParentContentSection = !properties.contains(ContentProperties.PARENT_CONTENT_SECTION);
                filterIsDefault = !properties.contains(ContentProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(ContentProperties.SORT_ORDER);
                filterDescription = !properties.contains(ContentProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(ContentProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public ContentSectionTransfer getContentSectionTransfer(ContentSection contentSection) {
        ContentSectionTransfer contentSectionTransfer = get(contentSection);
        
        if(contentSectionTransfer == null) {
            ContentSectionDetail contentSectionDetail = contentSection.getLastDetail();
            ContentCollectionTransfer contentCollectionTransfer = filterContentCollection ? null : contentControl.getContentCollectionTransfer(userVisit, contentSectionDetail.getContentCollection());
            String contentSectionName = filterContentSectionName ? null : contentSectionDetail.getContentSectionName();
            ContentSection parentContentSection = filterParentContentSection ? null : contentSectionDetail.getParentContentSection();
            ContentSectionTransfer parentContentSectionTransfer = parentContentSection == null ? null : contentControl.getContentSectionTransfer(userVisit, parentContentSection);
            Boolean isDefault = filterIsDefault ? null : contentSectionDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : contentSectionDetail.getSortOrder();
            String description = filterDescription ? null : contentControl.getBestContentSectionDescription(contentSection, getLanguage());
            
            contentSectionTransfer = new ContentSectionTransfer(contentCollectionTransfer, contentSectionName, parentContentSectionTransfer, isDefault,
                    sortOrder, description);
            put(contentSection, contentSectionTransfer);
            
            if(includeContentPages) {
                contentSectionTransfer.setContentPages(ListWrapperBuilder.getInstance().filter(transferProperties, contentControl.getContentPageTransfers(userVisit, contentSectionDetail.getContentSection())));
            }
        }
        
        return contentSectionTransfer;
    }
    
}
