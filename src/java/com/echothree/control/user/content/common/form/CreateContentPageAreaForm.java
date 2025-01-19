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

package com.echothree.control.user.content.common.form;

import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.persistence.type.ByteArray;

public interface CreateContentPageAreaForm
        extends BaseForm {
    
    String getContentCollectionName();
    void setContentCollectionName(String contentCollectionName);
    
    String getContentSectionName();
    void setContentSectionName(String contentSectionName);
    
    String getContentPageName();
    void setContentPageName(String contentPageName);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
    String getLanguageIsoName();
    void setLanguageIsoName(String languageIsoName);
    
    String getMimeTypeName();
    void setMimeTypeName(String mimeTypeName);
    
    String getDescription();
    void setDescription(String description);
    
    ByteArray getContentPageAreaBlob();
    void setContentPageAreaBlob(ByteArray contentPageAreaBlob);
    
    String getContentPageAreaClob();
    void setContentPageAreaClob(String contentPageAreaClob);
    
    String getContentPageAreaUrl();
    void setContentPageAreaUrl(String contentPageAreaUrl);
    
}
