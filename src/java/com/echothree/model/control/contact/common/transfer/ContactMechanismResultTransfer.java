// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.contact.common.transfer.ContactMechanismTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ContactMechanismResultTransfer
        extends BaseTransfer {
    
    private String contactMechanismName;
    private ContactMechanismTransfer contactMechanism;
    
    /** Creates a new instance of ContactMechanismResultTransfer */
    public ContactMechanismResultTransfer(String contactMechanismName, ContactMechanismTransfer contactMechanism) {
        this.contactMechanismName = contactMechanismName;
        this.contactMechanism = contactMechanism;
    }

    /**
     * Returns the contactMechanismName.
     * @return the contactMechanismName
     */
    public String getContactMechanismName() {
        return contactMechanismName;
    }

    /**
     * Sets the contactMechanismName.
     * @param contactMechanismName the contactMechanismName to set
     */
    public void setContactMechanismName(String contactMechanismName) {
        this.contactMechanismName = contactMechanismName;
    }

    /**
     * Returns the contactMechanism.
     * @return the contactMechanism
     */
    public ContactMechanismTransfer getContactMechanism() {
        return contactMechanism;
    }

    /**
     * Sets the contactMechanism.
     * @param contactMechanism the contactMechanism to set
     */
    public void setContactMechanism(ContactMechanismTransfer contactMechanism) {
        this.contactMechanism = contactMechanism;
    }

 }
