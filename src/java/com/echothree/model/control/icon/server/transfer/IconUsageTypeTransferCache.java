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

package com.echothree.model.control.icon.server.transfer;

import com.echothree.model.control.icon.common.transfer.IconUsageTypeTransfer;
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.data.icon.server.entity.IconUsageType;
import com.echothree.model.data.icon.server.entity.IconUsageTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class IconUsageTypeTransferCache
        extends BaseIconTransferCache<IconUsageType, IconUsageTypeTransfer> {
    
    /** Creates a new instance of IconUsageTypeTransferCache */
    public IconUsageTypeTransferCache(UserVisit userVisit, IconControl iconControl) {
        super(userVisit, iconControl);
        
        setIncludeEntityInstance(true);
    }
    
    public IconUsageTypeTransfer getIconUsageTypeTransfer(IconUsageType iconUsageType) {
        IconUsageTypeTransfer iconUsageTypeTransfer = get(iconUsageType);
        
        if(iconUsageTypeTransfer == null) {
            IconUsageTypeDetail iconUsageTypeDetail = iconUsageType.getLastDetail();
            String iconUsageTypeName = iconUsageTypeDetail.getIconUsageTypeName();
            Boolean isDefault = iconUsageTypeDetail.getIsDefault();
            Integer sortOrder = iconUsageTypeDetail.getSortOrder();
            String description = iconControl.getBestIconUsageTypeDescription(iconUsageType, getLanguage());
            
            iconUsageTypeTransfer = new IconUsageTypeTransfer(iconUsageTypeName, isDefault, sortOrder, description);
            put(iconUsageType, iconUsageTypeTransfer);
        }
        return iconUsageTypeTransfer;
    }
    
}
