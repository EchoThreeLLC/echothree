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

package com.echothree.control.user.contact.common.result;

import com.echothree.control.user.contact.common.edit.PostalAddressFormatDescriptionEdit;
import com.echothree.model.control.contact.common.transfer.PostalAddressFormatDescriptionTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressFormatTransfer;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.command.BaseEditResult;

public interface EditPostalAddressFormatDescriptionResult
        extends BaseEditResult {
    
    PostalAddressFormatTransfer getPostalAddressFormat();
    void setPostalAddressFormat(PostalAddressFormatTransfer postalAddressFormat);
    
    LanguageTransfer getLanguage();
    void setLanguage(LanguageTransfer language);
    
    PostalAddressFormatDescriptionTransfer getPostalAddressFormatDescription();
    void setPostalAddressFormatDescription(PostalAddressFormatDescriptionTransfer postalAddressFormatDescription);
    
    @Override
    PostalAddressFormatDescriptionEdit getEdit();
    void setEdit(PostalAddressFormatDescriptionEdit edit);
    
    @Override
    EntityLockTransfer getEntityLock();
    @Override
    void setEntityLock(EntityLockTransfer entityLock);
    
}
