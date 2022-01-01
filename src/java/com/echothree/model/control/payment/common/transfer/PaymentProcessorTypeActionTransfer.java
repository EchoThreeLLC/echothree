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

package com.echothree.model.control.payment.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PaymentProcessorTypeActionTransfer
        extends BaseTransfer {

    private PaymentProcessorTypeTransfer paymentProcessorTypeTransfer;
    private PaymentProcessorActionTypeTransfer paymentProcessorActionTypeTransfer;
    private Boolean isDefault;
    private Integer sortOrder;

    /** Creates a new instance of PaymentProcessorTypeActionTransfer */
    public PaymentProcessorTypeActionTransfer(PaymentProcessorTypeTransfer paymentProcessorTypeTransfer,
            PaymentProcessorActionTypeTransfer paymentProcessorActionTypeTransfer, Boolean isDefault,
            Integer sortOrder) {
        this.paymentProcessorTypeTransfer = paymentProcessorTypeTransfer;
        this.paymentProcessorActionTypeTransfer = paymentProcessorActionTypeTransfer;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    public PaymentProcessorTypeTransfer getPaymentProcessorTypeTransfer() {
        return paymentProcessorTypeTransfer;
    }

    public void setPaymentProcessorTypeTransfer(PaymentProcessorTypeTransfer paymentProcessorTypeTransfer) {
        this.paymentProcessorTypeTransfer = paymentProcessorTypeTransfer;
    }

    public PaymentProcessorActionTypeTransfer getPaymentProcessorActionTypeTransfer() {
        return paymentProcessorActionTypeTransfer;
    }

    public void setPaymentProcessorActionTypeTransfer(PaymentProcessorActionTypeTransfer paymentProcessorActionTypeTransfer) {
        this.paymentProcessorActionTypeTransfer = paymentProcessorActionTypeTransfer;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}
