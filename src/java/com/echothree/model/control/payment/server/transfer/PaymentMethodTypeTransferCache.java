// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.payment.common.transfer.PaymentMethodTypeTransfer;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentMethodTypeTransferCache
        extends BasePaymentTransferCache<PaymentMethodType, PaymentMethodTypeTransfer> {

    PaymentMethodTypeControl paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);

    /** Creates a new instance of PaymentMethodTypeTransferCache */
    public PaymentMethodTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public PaymentMethodTypeTransfer getTransfer(PaymentMethodType paymentMethodType) {
        var paymentMethodTypeTransfer = get(paymentMethodType);
        
        if(paymentMethodTypeTransfer == null) {
            var paymentMethodTypeDetail = paymentMethodType.getLastDetail();
            var paymentMethodTypeName = paymentMethodTypeDetail.getPaymentMethodTypeName();
            var isDefault = paymentMethodTypeDetail.getIsDefault();
            var sortOrder = paymentMethodTypeDetail.getSortOrder();
            var description = paymentMethodTypeControl.getBestPaymentMethodTypeDescription(paymentMethodType, getLanguage());
            
            paymentMethodTypeTransfer = new PaymentMethodTypeTransfer(paymentMethodTypeName, isDefault, sortOrder, description);
            put(paymentMethodType, paymentMethodTypeTransfer);
        }
        
        return paymentMethodTypeTransfer;
    }
    
}
