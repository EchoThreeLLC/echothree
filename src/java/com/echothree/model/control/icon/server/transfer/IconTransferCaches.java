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

package com.echothree.model.control.icon.server.transfer;

import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class IconTransferCaches
        extends BaseTransferCaches {
    
    protected IconControl iconControl;
    
    protected IconTransferCache iconTransferCache;
    protected IconUsageTypeTransferCache iconUsageTypeTransferCache;
    protected IconUsageTypeDescriptionTransferCache iconUsageTypeDescriptionTransferCache;
    protected IconUsageTransferCache iconUsageTransferCache;
    
    /** Creates a new instance of IconTransferCaches */
    public IconTransferCaches(UserVisit userVisit, IconControl iconControl) {
        super(userVisit);
        
        this.iconControl = iconControl;
    }
    
    public IconTransferCache getIconTransferCache() {
        if(iconTransferCache == null)
            iconTransferCache = new IconTransferCache(userVisit, iconControl);
        
        return iconTransferCache;
    }
    
    public IconUsageTypeTransferCache getIconUsageTypeTransferCache() {
        if(iconUsageTypeTransferCache == null)
            iconUsageTypeTransferCache = new IconUsageTypeTransferCache(userVisit, iconControl);
        
        return iconUsageTypeTransferCache;
    }
    
    public IconUsageTypeDescriptionTransferCache getIconUsageTypeDescriptionTransferCache() {
        if(iconUsageTypeDescriptionTransferCache == null)
            iconUsageTypeDescriptionTransferCache = new IconUsageTypeDescriptionTransferCache(userVisit, iconControl);
        
        return iconUsageTypeDescriptionTransferCache;
    }
    
    public IconUsageTransferCache getIconUsageTransferCache() {
        if(iconUsageTransferCache == null)
            iconUsageTransferCache = new IconUsageTransferCache(userVisit, iconControl);
        
        return iconUsageTransferCache;
    }
    
}
