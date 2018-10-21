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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.remote.transfer.EntityAttributeTypeTransfer;
import com.echothree.model.control.core.remote.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.remote.form.TransferProperties;
import com.echothree.util.remote.transfer.ListWrapper;
import java.util.Set;

public class MimeTypeTransferCache
        extends BaseCoreTransferCache<MimeType, MimeTypeTransfer> {

    boolean includeMimeTypeFileExtensions;
    
    TransferProperties transferProperties;
    boolean filterMimeTypeName;
    boolean filterEntityAttributeType;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of MimeTypeTransferCache */
    public MimeTypeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeMimeTypeFileExtensions = options.contains(CoreOptions.MimeTypeIncludeMimeTypeFileExtensions);
        }

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(MimeTypeTransfer.class);
            
            if(properties != null) {
                filterMimeTypeName = !properties.contains(CoreProperties.MIME_TYPE_NAME);
                filterEntityAttributeType = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE_TYPE);
                filterIsDefault = !properties.contains(CoreProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }

    public MimeTypeTransfer getMimeTypeTransfer(MimeType mimeType) {
        MimeTypeTransfer mimeTypeTransfer = get(mimeType);

        if(mimeTypeTransfer == null) {
            MimeTypeDetail mimeTypeDetail = mimeType.getLastDetail();
            String mimeTypeName = filterMimeTypeName ? null : mimeTypeDetail.getMimeTypeName();
            EntityAttributeTypeTransfer entityAttributeType = filterEntityAttributeType ? null : coreControl.getEntityAttributeTypeTransfer(userVisit, mimeTypeDetail.getEntityAttributeType());
            Boolean isDefault = filterIsDefault ? null : mimeTypeDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : mimeTypeDetail.getSortOrder();
            String description = filterDescription ? null : coreControl.getBestMimeTypeDescription(mimeType, getLanguage());

            mimeTypeTransfer = new MimeTypeTransfer(mimeTypeName, entityAttributeType, isDefault, sortOrder, description);
            put(mimeType, mimeTypeTransfer);
            
            if(includeMimeTypeFileExtensions) {
                mimeTypeTransfer.setMimeTypeFileExtensions(new ListWrapper<>(coreControl.getMimeTypeFileExtensionTransfersByMimeType(userVisit, mimeType)));
            }
        }

        return mimeTypeTransfer;
    }

}
