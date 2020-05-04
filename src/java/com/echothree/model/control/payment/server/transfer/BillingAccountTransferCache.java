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

package com.echothree.model.control.payment.server.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.payment.common.PaymentOptions;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.payment.server.entity.BillingAccount;
import com.echothree.model.data.payment.server.entity.BillingAccountDetail;
import com.echothree.model.data.payment.server.entity.BillingAccountStatus;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import java.util.Set;

public class BillingAccountTransferCache
        extends BasePaymentTransferCache<BillingAccount, BillingAccountTransfer> {

    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    boolean includeRoles;

    /** Creates a new instance of BillingAccountTransferCache */
    public BillingAccountTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includeRoles = options.contains(PaymentOptions.BillingAccountIncludeRoles);
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public BillingAccountTransfer getTransfer(BillingAccount billingAccount) {
        BillingAccountTransfer billingAccountTransfer = get(billingAccount);

        if(billingAccountTransfer == null) {
            BillingAccountDetail billingAccountDetail = billingAccount.getLastDetail();
            String billingAccountName = billingAccountDetail.getBillingAccountName();
            Currency currency = billingAccountDetail.getCurrency();
            CurrencyTransfer currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            String reference = billingAccountDetail.getReference();
            String description = billingAccountDetail.getDescription();
            String creditLimit = null;
            String potentialCreditLimit = null;
//            BillingAccountStatus billingAccountStatus = paymentControl.getBillingAccountStatus(billingAccount);
//            Integer rawCreditLimit = billingAccountStatus.getCreditLimit();
//            Integer rawPotentialCreditLimit = billingAccountStatus.getPotentialCreditLimit();
//
//            String partyTypeName = fromParty.getLastDetail().getPartyType().getPartyTypeName();
//            if(PartyTypes.CUSTOMER.name().equals(partyTypeName)) {
//                creditLimit = rawCreditLimit == null? null: AmountUtils.getInstance().formatPriceLine(currency, rawCreditLimit);
//                potentialCreditLimit = rawPotentialCreditLimit == null? null: AmountUtils.getInstance().formatPriceLine(currency, rawPotentialCreditLimit);
//            } else if(PartyTypes.COMPANY.name().equals(partyTypeName)) {
//                creditLimit = rawCreditLimit == null? null: AmountUtils.getInstance().formatCostLine(currency, rawCreditLimit);
//                potentialCreditLimit = rawPotentialCreditLimit == null? null: AmountUtils.getInstance().formatCostLine(currency, rawPotentialCreditLimit);
//            }
            
            billingAccountTransfer = new BillingAccountTransfer(billingAccountName, currencyTransfer, reference, description, creditLimit, potentialCreditLimit);
            put(billingAccount, billingAccountTransfer);
            
            if(includeRoles) {
                List<BillingAccountRoleTransfer> billingAccountRoleTransfers = paymentControl.getBillingAccountRoleTransfersByBillingAccount(userVisit, billingAccount);
                MapWrapper<BillingAccountRoleTransfer> billingAccountRoles = new MapWrapper<>(billingAccountRoleTransfers.size());

                billingAccountRoleTransfers.stream().forEach((billingAccountRoleTransfer) -> {
                    billingAccountRoles.put(billingAccountRoleTransfer.getBillingAccountRoleType().getBillingAccountRoleTypeName(), billingAccountRoleTransfer);
                });

                billingAccountTransfer.setBillingAccountRoles(billingAccountRoles);
            }
        }
        
        return billingAccountTransfer;
    }
    
}