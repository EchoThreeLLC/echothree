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

package com.echothree.control.user.message.remote.edit;

import com.echothree.control.user.core.remote.spec.MimeTypeSpec;
import com.echothree.util.remote.form.BaseEdit;
import com.echothree.util.remote.persistence.type.ByteArray;

public interface MessageEdit
        extends BaseEdit, MimeTypeSpec, MessageDescriptionEdit {
    
    String getMessageName();
    void setMessageName(String messageName);
    
    String getIncludeByDefault();
    void setIncludeByDefault(String includeByDefault);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
    ByteArray getBlobMessage();
    void setBlobMessage(ByteArray blobMessage);
    
    String getClobMessage();
    void setClobMessage(String clobMessage);
    
    String getStringMessage();
    void setStringMessage(String stringMessage);
    
}
