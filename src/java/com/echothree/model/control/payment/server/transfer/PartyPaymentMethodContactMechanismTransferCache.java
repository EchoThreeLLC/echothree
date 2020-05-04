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

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismPurposeTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.payment.common.PaymentConstants;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodContactMechanismTransfer;
import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.payment.server.PaymentControl;
import static com.echothree.model.control.customer.common.workflow.CustomerCreditCardContactMechanismConstants.Workflow_CUSTOMER_CREDIT_CARD_CONTACT_MECHANISM;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodContactMechanism;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyPaymentMethodContactMechanismTransferCache
        extends BasePaymentTransferCache<PartyPaymentMethodContactMechanism, PartyPaymentMethodContactMechanismTransfer> {
    
    ContactControl contactControl;
    CoreControl coreControl;
    WorkflowControl workflowControl;
    
    /** Creates a new instance of PartyPaymentMethodContactMechanismTransferCache */
    public PartyPaymentMethodContactMechanismTransferCache(UserVisit userVisit, PaymentControl paymentControl) {
        super(userVisit, paymentControl);
        
        contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    }

    @Override
    public PartyPaymentMethodContactMechanismTransfer getTransfer(PartyPaymentMethodContactMechanism partyPaymentMethodContactMechanism) {
        PartyPaymentMethodContactMechanismTransfer partyPaymentMethodContactMechanismTransfer = get(partyPaymentMethodContactMechanism);
        
        if(partyPaymentMethodContactMechanismTransfer == null) {
            PartyPaymentMethod partyPaymentMethod = partyPaymentMethodContactMechanism.getPartyPaymentMethod();
            PartyPaymentMethodTransfer partyPaymentMethodTransfer = paymentControl.getPartyPaymentMethodTransfer(userVisit, partyPaymentMethod);
            PartyContactMechanismPurposeTransfer partyContactMechanismPurposeTransfer = contactControl.getPartyContactMechanismPurposeTransfer(userVisit, partyPaymentMethodContactMechanism.getPartyContactMechanismPurpose());
            WorkflowEntityStatusTransfer partyPaymentMethodContactMechanismStatusTransfer = null;
            
            String paymentMethodTypeName = partyPaymentMethod.getLastDetail().getPaymentMethod().getLastDetail().getPaymentMethodType().getPaymentMethodTypeName();
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(partyPaymentMethodContactMechanism.getPrimaryKey());
            if(paymentMethodTypeName.equals(PaymentConstants.PaymentMethodType_CREDIT_CARD)) {
                    partyPaymentMethodContactMechanismStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                            Workflow_CUSTOMER_CREDIT_CARD_CONTACT_MECHANISM, entityInstance);
            }
            
            partyPaymentMethodContactMechanismTransfer = new PartyPaymentMethodContactMechanismTransfer(partyPaymentMethodTransfer,
                    partyContactMechanismPurposeTransfer, partyPaymentMethodContactMechanismStatusTransfer);
            put(partyPaymentMethodContactMechanism, partyPaymentMethodContactMechanismTransfer);
        }
        return partyPaymentMethodContactMechanismTransfer;
    }
    
}
