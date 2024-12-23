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

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTypePartyTypeTransfer;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypePartyType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PaymentMethodTypePartyTypeTransferCache
        extends BasePaymentTransferCache<PaymentMethodTypePartyType, PaymentMethodTypePartyTypeTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    PaymentMethodTypeControl paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of PaymentMethodTypePartyTypeTransferCache */
    public PaymentMethodTypePartyTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
    }

    @Override
    public PaymentMethodTypePartyTypeTransfer getTransfer(PaymentMethodTypePartyType paymentMethodTypePartyType) {
        var paymentMethodTypePartyTypeTransfer = get(paymentMethodTypePartyType);
        
        if(paymentMethodTypePartyTypeTransfer == null) {
            var paymentMethodTypePartyTypeDetail = paymentMethodTypePartyType.getLastDetail();
            var paymentMethodType = paymentMethodTypeControl.getPaymentMethodTypeTransfer(userVisit, paymentMethodTypePartyTypeDetail.getPaymentMethodType());
            var partyType = partyControl.getPartyTypeTransfer(userVisit, paymentMethodTypePartyTypeDetail.getPartyType());
            var partyPaymentMethodWorkflow = workflowControl.getWorkflowTransfer(userVisit, paymentMethodTypePartyTypeDetail.getPartyPaymentMethodWorkflow());
            var partyContactMechanismWorkflow = workflowControl.getWorkflowTransfer(userVisit, paymentMethodTypePartyTypeDetail.getPartyContactMechanismWorkflow());
            
            paymentMethodTypePartyTypeTransfer = new PaymentMethodTypePartyTypeTransfer(paymentMethodType, partyType, partyPaymentMethodWorkflow,
                    partyContactMechanismWorkflow);
            put(paymentMethodTypePartyType, paymentMethodTypePartyTypeTransfer);
        }
        return paymentMethodTypePartyTypeTransfer;
    }
    
}
