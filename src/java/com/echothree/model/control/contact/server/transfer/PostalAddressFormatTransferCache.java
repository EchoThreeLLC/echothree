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

package com.echothree.model.control.contact.server.transfer;

import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.contact.remote.transfer.PostalAddressFormatTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.remote.transfer.ListWrapper;
import java.util.Set;

public class PostalAddressFormatTransferCache
        extends BaseContactTransferCache<PostalAddressFormat, PostalAddressFormatTransfer> {
    
    boolean includeLines;
    
    /** Creates a new instance of PostalAddressFormatTransferCache */
    public PostalAddressFormatTransferCache(UserVisit userVisit, ContactControl contactControl) {
        super(userVisit, contactControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeLines = options.contains(ContactOptions.PostalAddressFormatIncludeLines);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public PostalAddressFormatTransfer getPostalAddressFormatTransfer(PostalAddressFormat postalAddressFormat) {
        PostalAddressFormatTransfer postalAddressFormatTransfer = get(postalAddressFormat);
        
        if(postalAddressFormatTransfer == null) {
            PostalAddressFormatDetail postalAddressFormatDetail = postalAddressFormat.getLastDetail();
            String postalAddressFormatName = postalAddressFormatDetail.getPostalAddressFormatName();
            Boolean isDefault = postalAddressFormatDetail.getIsDefault();
            Integer sortOrder = postalAddressFormatDetail.getSortOrder();
            String description = contactControl.getBestPostalAddressFormatDescription(postalAddressFormat, getLanguage());
            
            postalAddressFormatTransfer = new PostalAddressFormatTransfer(postalAddressFormatName, isDefault, sortOrder, description);
            put(postalAddressFormat, postalAddressFormatTransfer);
            
            if(includeLines) {
                postalAddressFormatTransfer.setPostalAddressLines(new ListWrapper<>(contactControl.getPostalAddressLineTransfersByPostalAddressFormat(userVisit, postalAddressFormat)));
            }
        }
        
        return postalAddressFormatTransfer;
    }
    
}
