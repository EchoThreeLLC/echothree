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

package com.echothree.control.user.letter.common.edit;

import com.echothree.control.user.letter.common.spec.LetterSourceSpec;

public interface LetterSourceEdit
        extends LetterSourceSpec, LetterSourceDescriptionEdit {
    
    String getEmailAddressContactMechanismName();
    void setEmailAddressContactMechanismName(String emailAddressContactMechanismName);
    
    String getEmailAddressContactMechanismAliasTypeName();
    void setEmailAddressContactMechanismAliasTypeName(String emailAddressContactMechanismAliasTypeName);
    
    String getEmailAddressContactMechanismAlias();
    void setEmailAddressContactMechanismAlias(String emailAddressContactMechanismAlias);
    
    String getPostalAddressContactMechanismName();
    void setPostalAddressContactMechanismName(String postalAddressContactMechanismName);
    
    String getPostalAddressContactMechanismAliasTypeName();
    void setPostalAddressContactMechanismAliasTypeName(String postalAddressContactMechanismAliasTypeName);
    
    String getPostalAddressContactMechanismAlias();
    void setPostalAddressContactMechanismAlias(String postalAddressContactMechanismAlias);
    
    String getLetterSourceContactMechanismName();
    void setLetterSourceContactMechanismName(String letterSourceContactMechanismName);
    
    String getLetterSourceContactMechanismAliasTypeName();
    void setLetterSourceContactMechanismAliasTypeName(String letterSourceContactMechanismAliasTypeName);
    
    String getLetterSourceContactMechanismAlias();
    void setLetterSourceContactMechanismAlias(String letterSourceContactMechanismAlias);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
