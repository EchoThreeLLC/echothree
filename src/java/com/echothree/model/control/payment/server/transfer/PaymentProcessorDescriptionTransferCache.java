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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorDescriptionTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentProcessorTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorDescriptionTransferCache
        extends BasePaymentDescriptionTransferCache<PaymentProcessorDescription, PaymentProcessorDescriptionTransfer> {

    PaymentProcessorControl paymentProcessorControl = Session.getModelController(com.echothree.model.control.payment.server.control.PaymentProcessorControl.class);

    /** Creates a new instance of PaymentProcessorDescriptionTransferCache */
    public PaymentProcessorDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }

    @Override
    public PaymentProcessorDescriptionTransfer getTransfer(PaymentProcessorDescription paymentProcessorDescription) {
        PaymentProcessorDescriptionTransfer paymentProcessorDescriptionTransfer = get(paymentProcessorDescription);
        
        if(paymentProcessorDescriptionTransfer == null) {
            PaymentProcessorTransfer paymentProcessorTransfer = paymentProcessorControl.getPaymentProcessorTransfer(userVisit, paymentProcessorDescription.getPaymentProcessor());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, paymentProcessorDescription.getLanguage());
            
            paymentProcessorDescriptionTransfer = new PaymentProcessorDescriptionTransfer(languageTransfer, paymentProcessorTransfer, paymentProcessorDescription.getDescription());
            put(paymentProcessorDescription, paymentProcessorDescriptionTransfer);
        }
        
        return paymentProcessorDescriptionTransfer;
    }
    
}
