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

package com.echothree.model.control.contact.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ContactMechanismAliasTransfer
        extends BaseTransfer {
    
    private ContactMechanismTransfer contactMechanism;
    private ContactMechanismAliasTypeTransfer contactMechanismAliasType;
    private String alias;
    
    /** Creates a new instance of ContactMechanismAliasTransfer */
    public ContactMechanismAliasTransfer(ContactMechanismTransfer contactMechanism,
            ContactMechanismAliasTypeTransfer contactMechanismAliasType, String alias) {
        this.contactMechanism = contactMechanism;
        this.contactMechanismAliasType = contactMechanismAliasType;
        this.alias = alias;
    }
    
    public ContactMechanismTransfer getContactMechanism() {
        return contactMechanism;
    }
    
    public void setContactMechanism(ContactMechanismTransfer contactMechanism) {
        this.contactMechanism = contactMechanism;
    }
    
    public ContactMechanismAliasTypeTransfer getContactMechanismAliasType() {
        return contactMechanismAliasType;
    }
    
    public void setContactMechanismAliasType(ContactMechanismAliasTypeTransfer contactMechanismAliasType) {
        this.contactMechanismAliasType = contactMechanismAliasType;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
