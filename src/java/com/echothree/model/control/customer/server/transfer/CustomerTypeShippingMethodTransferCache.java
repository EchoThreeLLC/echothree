// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.customer.common.transfer.CustomerTypeShippingMethodTransfer;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.customer.server.entity.CustomerTypeShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CustomerTypeShippingMethodTransferCache
        extends BaseCustomerTransferCache<CustomerTypeShippingMethod, CustomerTypeShippingMethodTransfer> {
    
    /** Creates a new instance of CustomerTypeShippingMethodTransferCache */
    public CustomerTypeShippingMethodTransferCache(UserVisit userVisit, CustomerControl customerControl) {
        super(userVisit, customerControl);
    }
    
    public CustomerTypeShippingMethodTransfer getCustomerTypeShippingMethodTransfer(CustomerTypeShippingMethod customerTypeShippingMethod) {
        CustomerTypeShippingMethodTransfer customerTypeShippingMethodTransfer = get(customerTypeShippingMethod);
        
        if(customerTypeShippingMethodTransfer == null) {
            ShippingControl shippingControl = Session.getModelController(ShippingControl.class);
            CustomerTypeTransfer customerType = customerControl.getCustomerTypeTransfer(userVisit, customerTypeShippingMethod.getCustomerType());
            ShippingMethodTransfer shippingMethod = shippingControl.getShippingMethodTransfer(userVisit, customerTypeShippingMethod.getShippingMethod());
            Integer defaultSelectionPriority = customerTypeShippingMethod.getDefaultSelectionPriority();
            Boolean isDefault = customerTypeShippingMethod.getIsDefault();
            Integer sortOrder = customerTypeShippingMethod.getSortOrder();
            
            customerTypeShippingMethodTransfer = new CustomerTypeShippingMethodTransfer(customerType, shippingMethod, defaultSelectionPriority, isDefault,
                    sortOrder);
            put(customerTypeShippingMethod, customerTypeShippingMethodTransfer);
        }
        
        return customerTypeShippingMethodTransfer;
    }
    
}
