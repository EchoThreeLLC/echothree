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

package com.echothree.model.control.icon.server.transfer;

import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.icon.server.entity.IconDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class IconTransferCache
        extends BaseIconTransferCache<Icon, IconTransfer> {
    
    DocumentControl documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
    
    /** Creates a new instance of IconTransferCache */
    public IconTransferCache(UserVisit userVisit, IconControl iconControl) {
        super(userVisit, iconControl);
        
        setIncludeEntityInstance(true);
    }
    
    public IconTransfer getIconTransfer(Icon icon) {
        IconTransfer iconTransfer = get(icon);
        
        if(iconTransfer == null) {
            IconDetail iconDetail = icon.getLastDetail();
            String iconName = iconDetail.getIconName();
            DocumentTransfer document = documentControl.getDocumentTransfer(userVisit, iconDetail.getDocument());
            Boolean isDefault = iconDetail.getIsDefault();
            Integer sortOrder = iconDetail.getSortOrder();
            
            iconTransfer = new IconTransfer(iconName, document, isDefault, sortOrder);
            put(icon, iconTransfer);
        }
        
        return iconTransfer;
    }
    
}
