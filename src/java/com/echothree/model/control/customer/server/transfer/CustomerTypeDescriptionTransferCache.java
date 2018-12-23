// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.customer.server.transfer;

import com.echothree.model.control.customer.common.transfer.CustomerTypeDescriptionTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.customer.server.entity.CustomerTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CustomerTypeDescriptionTransferCache
        extends BaseCustomerDescriptionTransferCache<CustomerTypeDescription, CustomerTypeDescriptionTransfer> {
    
    /** Creates a new instance of CustomerTypeDescriptionTransferCache */
    public CustomerTypeDescriptionTransferCache(UserVisit userVisit, CustomerControl customerControl) {
        super(userVisit, customerControl);
    }
    
    public CustomerTypeDescriptionTransfer getCustomerTypeDescriptionTransfer(CustomerTypeDescription customerTypeDescription) {
        CustomerTypeDescriptionTransfer customerTypeDescriptionTransfer = get(customerTypeDescription);
        
        if(customerTypeDescriptionTransfer == null) {
            CustomerTypeTransfer customerTypeTransfer = customerControl.getCustomerTypeTransfer(userVisit, customerTypeDescription.getCustomerType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, customerTypeDescription.getLanguage());
            
            customerTypeDescriptionTransfer = new CustomerTypeDescriptionTransfer(languageTransfer, customerTypeTransfer, customerTypeDescription.getDescription());
            put(customerTypeDescription, customerTypeDescriptionTransfer);
        }
        
        return customerTypeDescriptionTransfer;
    }
    
}
