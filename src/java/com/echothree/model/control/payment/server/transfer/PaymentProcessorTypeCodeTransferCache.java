// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.payment.common.transfer.PaymentProcessorTypeCodeTransfer;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeControl;
import com.echothree.model.control.payment.server.control.PaymentProcessorTypeCodeTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentProcessorTypeCodeTransferCache
        extends BasePaymentTransferCache<PaymentProcessorTypeCode, PaymentProcessorTypeCodeTransfer> {

    PaymentProcessorTypeCodeTypeControl paymentProcessorTypeCodeTypeControl = Session.getModelController(PaymentProcessorTypeCodeTypeControl.class);
    PaymentProcessorTypeCodeControl paymentProcessorTypeCodeControl = Session.getModelController(PaymentProcessorTypeCodeControl.class);

    /** Creates a new instance of PaymentProcessorTypeTransferCache */
    public PaymentProcessorTypeCodeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentProcessorTypeCodeTransfer getTransfer(PaymentProcessorTypeCode paymentProcessorTypeCode) {
        var paymentProcessorTypeCodeTransfer = get(paymentProcessorTypeCode);
        
        if(paymentProcessorTypeCodeTransfer == null) {
            var paymentProcessorTypeCodeDetail = paymentProcessorTypeCode.getLastDetail();
            var paymentProcessorTypeCodeTypeTransfer = paymentProcessorTypeCodeTypeControl.getPaymentProcessorTypeCodeTypeTransfer(userVisit, paymentProcessorTypeCodeDetail.getPaymentProcessorTypeCodeType());
            var paymentProcessorTypeCodeName = paymentProcessorTypeCodeDetail.getPaymentProcessorTypeCodeName();
            var isDefault = paymentProcessorTypeCodeDetail.getIsDefault();
            var sortOrder = paymentProcessorTypeCodeDetail.getSortOrder();
            var description = paymentProcessorTypeCodeControl.getBestPaymentProcessorTypeCodeDescription(paymentProcessorTypeCode, getLanguage());
            
            paymentProcessorTypeCodeTransfer = new PaymentProcessorTypeCodeTransfer(paymentProcessorTypeCodeTypeTransfer,
                    paymentProcessorTypeCodeName, isDefault, sortOrder, description);
            put(paymentProcessorTypeCode, paymentProcessorTypeCodeTransfer);
        }
        
        return paymentProcessorTypeCodeTransfer;
    }
    
}
