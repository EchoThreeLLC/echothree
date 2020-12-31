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

package com.echothree.model.control.term.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.customer.common.transfer.CustomerTypeTransfer;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.term.common.transfer.CustomerTypeCreditLimitTransfer;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.term.server.entity.CustomerTypeCreditLimit;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class CustomerTypeCreditLimitTransferCache
        extends BaseTermTransferCache<CustomerTypeCreditLimit, CustomerTypeCreditLimitTransfer> {
    
    AccountingControl accountingControl;
    CustomerControl customerControl;
    
    /** Creates a new instance of CustomerTypeCreditLimitTransferCache */
    public CustomerTypeCreditLimitTransferCache(UserVisit userVisit, TermControl termControl) {
        super(userVisit, termControl);
        
        accountingControl = Session.getModelController(AccountingControl.class);
        customerControl = Session.getModelController(CustomerControl.class);
    }
    
    public CustomerTypeCreditLimitTransfer getCustomerTypeCreditLimitTransfer(CustomerTypeCreditLimit customerTypeCreditLimit) {
        CustomerTypeCreditLimitTransfer customerTypeCreditLimitTransfer = get(customerTypeCreditLimit);
        
        if(customerTypeCreditLimitTransfer == null) {
            CustomerTypeTransfer customerTypeTransfer = customerControl.getCustomerTypeTransfer(userVisit,
                    customerTypeCreditLimit.getCustomerType());
            Currency currency = customerTypeCreditLimit.getCurrency();
            CurrencyTransfer currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            String creditLimit = AmountUtils.getInstance().formatAmount(currency, customerTypeCreditLimit.getCreditLimit());
            String potentialCreditLimit = AmountUtils.getInstance().formatAmount(currency, customerTypeCreditLimit.getPotentialCreditLimit());
            
            customerTypeCreditLimitTransfer = new CustomerTypeCreditLimitTransfer(customerTypeTransfer, currencyTransfer,
                    creditLimit, potentialCreditLimit);
            put(customerTypeCreditLimit, customerTypeCreditLimitTransfer);
        }
        
        return customerTypeCreditLimitTransfer;
    }
    
}
