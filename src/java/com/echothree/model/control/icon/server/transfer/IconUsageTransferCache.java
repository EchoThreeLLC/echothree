// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.model.control.icon.common.transfer.IconUsageTransfer;
import com.echothree.model.control.icon.common.transfer.IconUsageTypeTransfer;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.icon.server.entity.IconUsage;
import com.echothree.model.data.user.server.entity.UserVisit;

public class IconUsageTransferCache
        extends BaseIconTransferCache<IconUsage, IconUsageTransfer> {
    
    /** Creates a new instance of IconUsageTransferCache */
    public IconUsageTransferCache(UserVisit userVisit, IconControl iconControl) {
        super(userVisit, iconControl);
    }
    
    public IconUsageTransfer getIconUsageTransfer(IconUsage iconUsage) {
        IconUsageTransfer iconUsageTransfer = get(iconUsage);
        
        if(iconUsageTransfer == null) {
            IconTransfer icon = iconControl.getIconTransfer(userVisit, iconUsage.getIcon());
            IconUsageTypeTransfer iconUsageType = iconControl.getIconUsageTypeTransfer(userVisit, iconUsage.getIconUsageType());
            Boolean isDefault = iconUsageType.getIsDefault();
            Integer sortOrder = iconUsageType.getSortOrder();
            
            iconUsageTransfer = new IconUsageTransfer(icon, iconUsageType, isDefault, sortOrder);
            put(iconUsage, iconUsageTransfer);
        }
        return iconUsageTransfer;
    }
    
}
