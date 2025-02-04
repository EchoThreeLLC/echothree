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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.RelatedItemTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.RelatedItemType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class RelatedItemTypeTransferCache
        extends BaseItemTransferCache<RelatedItemType, RelatedItemTypeTransfer> {
    
    /** Creates a new instance of RelatedItemTypeTransferCache */
    public RelatedItemTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public RelatedItemTypeTransfer getTransfer(RelatedItemType relatedItemType) {
        var relatedItemTypeTransfer = get(relatedItemType);
        
        if(relatedItemTypeTransfer == null) {
            var relatedItemTypeDetail = relatedItemType.getLastDetail();
            var relatedItemTypeName = relatedItemTypeDetail.getRelatedItemTypeName();
            var isDefault = relatedItemTypeDetail.getIsDefault();
            var sortOrder = relatedItemTypeDetail.getSortOrder();
            var description = itemControl.getBestRelatedItemTypeDescription(relatedItemType, getLanguage());

            relatedItemTypeTransfer = new RelatedItemTypeTransfer(relatedItemTypeName, isDefault, sortOrder, description);
            put(relatedItemType, relatedItemTypeTransfer);
        }
        
        return relatedItemTypeTransfer;
    }
    
}
