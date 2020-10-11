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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.transfer.PostalAddressElementTypeTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressLineElementTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressLineTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressLineElement;
import com.echothree.model.data.user.server.entity.UserVisit;

public class PostalAddressLineElementTransferCache
        extends BaseContactTransferCache<PostalAddressLineElement, PostalAddressLineElementTransfer> {
    
    /** Creates a new instance of PostalAddressLineElementTransferCache */
    public PostalAddressLineElementTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
    }
    
    public PostalAddressLineElementTransfer getPostalAddressLineElementTransfer(PostalAddressLineElement postalAddressLineElement) {
        PostalAddressLineElementTransfer postalAddressLineElementTransfer = get(postalAddressLineElement);
        
        if(postalAddressLineElementTransfer == null) {
            PostalAddressLineTransfer postalAddressLine = contactControl.getPostalAddressLineTransfer(userVisit,
                    postalAddressLineElement.getPostalAddressLine());
            Integer postalAddressLineElementSortOrder = postalAddressLineElement.getPostalAddressLineElementSortOrder();
            PostalAddressElementTypeTransfer postalAddressElementType = contactControl.getPostalAddressElementTypeTransfer(userVisit,
                    postalAddressLineElement.getPostalAddressElementType());
            String prefix = postalAddressLineElement.getPrefix();
            Boolean alwaysIncludePrefix = postalAddressLineElement.getAlwaysIncludePrefix();
            String suffix = postalAddressLineElement.getSuffix();
            Boolean alwaysIncludeSuffix = postalAddressLineElement.getAlwaysIncludeSuffix();
            
            postalAddressLineElementTransfer = new PostalAddressLineElementTransfer(postalAddressLine,
                    postalAddressLineElementSortOrder, postalAddressElementType, prefix, alwaysIncludePrefix, suffix,
                    alwaysIncludeSuffix);
            put(postalAddressLineElement, postalAddressLineElementTransfer);
        }
        
        return postalAddressLineElementTransfer;
    }
    
}
