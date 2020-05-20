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

package com.echothree.model.control.payment.server.control;

import com.echothree.model.control.payment.server.transfer.PaymentTransferCaches;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.Session;

public abstract class BasePaymentControl
        extends BaseModelControl {

    /** Creates a new instance of BasePaymentControl */
    protected BasePaymentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Payment Transfer Caches
    // --------------------------------------------------------------------------------
    
    private PaymentTransferCaches paymentTransferCaches = null;
    
    public PaymentTransferCaches getPaymentTransferCaches(UserVisit userVisit) {
        if(paymentTransferCaches == null) {
            var paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);

            paymentTransferCaches = new PaymentTransferCaches(userVisit, paymentControl);
        }
        
        return paymentTransferCaches;
    }
    
 }
