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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.transfer.PostalAddressElementTypeTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressElementType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PostalAddressElementTypeTransferCache
        extends BaseContactTransferCache<PostalAddressElementType, PostalAddressElementTypeTransfer> {
    
    /** Creates a new instance of PostalAddressElementTypeTransferCache */
    public PostalAddressElementTypeTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
    }
    
    public PostalAddressElementTypeTransfer getPostalAddressElementTypeTransfer(PostalAddressElementType postalAddressElementType) {
        PostalAddressElementTypeTransfer postalAddressElementTypeTransfer = get(postalAddressElementType);
        
        if(postalAddressElementTypeTransfer == null) {
            String postalAddressElementTypeName = postalAddressElementType.getPostalAddressElementTypeName();
            Boolean isDefault = postalAddressElementType.getIsDefault();
            Integer sortOrder = postalAddressElementType.getSortOrder();
            String description = contactControl.getBestPostalAddressElementTypeDescription(postalAddressElementType, getLanguage());
            
            postalAddressElementTypeTransfer = new PostalAddressElementTypeTransfer(postalAddressElementTypeName, isDefault, sortOrder, description);
            put(postalAddressElementType, postalAddressElementTypeTransfer);
        }
        
        return postalAddressElementTypeTransfer;
    }
    
}
