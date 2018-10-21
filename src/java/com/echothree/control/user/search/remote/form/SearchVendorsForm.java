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

package com.echothree.control.user.search.remote.form;

import com.echothree.util.remote.form.BaseForm;

public interface SearchVendorsForm
        extends BaseForm {
    
    String getSearchTypeName();
    void setSearchTypeName(String searchTypeName);
    
    String getFirstName();
    void setFirstName(String firstName);
    
    String getFirstNameSoundex();
    void setFirstNameSoundex(String firstNameSoundex);
    
    String getMiddleName();
    void setMiddleName(String middleName);
    
    String getMiddleNameSoundex();
    void setMiddleNameSoundex(String middleNameSoundex);
    
    String getLastName();
    void setLastName(String lastName);
    
    String getLastNameSoundex();
    void setLastNameSoundex(String lastNameSoundex);
    
    String getName();
    void setName(String name);
    
    String getVendorName();
    void setVendorName(String vendorName);
    
    String getCreatedSince();
    void setCreatedSince(String createdSince);

    String getModifiedSince();
    void setModifiedSince(String modifiedSince);
    
    String getFields();
    void setFields(String fields);
    
}
