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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeFileExtensionTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.user.server.entity.UserVisit;

public class MimeTypeFileExtensionTransferCache
        extends BaseCoreTransferCache<MimeTypeFileExtension, MimeTypeFileExtensionTransfer> {
    
    /** Creates a new instance of MimeTypeFileExtensionTransferCache */
    public MimeTypeFileExtensionTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public MimeTypeFileExtensionTransfer getMimeTypeFileExtensionTransfer(MimeTypeFileExtension mimeTypeFileExtension) {
        MimeTypeFileExtensionTransfer mimeTypeFileExtensionTransfer = get(mimeTypeFileExtension);
        
        if(mimeTypeFileExtensionTransfer == null) {
            MimeTypeTransfer mimeType = coreControl.getMimeTypeTransfer(userVisit, mimeTypeFileExtension.getMimeType());
            String fileExtension = mimeTypeFileExtension.getFileExtension();
            Boolean isDefault = mimeTypeFileExtension.getIsDefault();
            
            mimeTypeFileExtensionTransfer = new MimeTypeFileExtensionTransfer(mimeType, fileExtension, isDefault);
            put(mimeTypeFileExtension, mimeTypeFileExtensionTransfer);
        }
        
        return mimeTypeFileExtensionTransfer;
    }
    
}
