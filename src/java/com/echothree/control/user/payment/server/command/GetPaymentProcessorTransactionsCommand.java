// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.form.GetPaymentProcessorTransactionsForm;
import com.echothree.control.user.payment.common.result.GetPaymentProcessorTransactionsResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentProcessorTransactionControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetPaymentProcessorTransactionsCommand
        extends BaseMultipleEntitiesCommand<PaymentProcessorTransaction, GetPaymentProcessorTransactionsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessorTransaction.name(), SecurityRoles.List.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
        ));
    }
    
    /** Creates a new instance of GetPaymentProcessorTransactionsCommand */
    public GetPaymentProcessorTransactionsCommand(UserVisitPK userVisitPK, GetPaymentProcessorTransactionsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<PaymentProcessorTransaction> getEntities() {
        var paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);
        
        return paymentProcessorTransactionControl.getPaymentProcessorTransactions();
    }
    
    @Override
    protected BaseResult getResult(Collection<PaymentProcessorTransaction> entities) {
        GetPaymentProcessorTransactionsResult result = PaymentResultFactory.getGetPaymentProcessorTransactionsResult();
        var paymentProcessorTransactionControl = Session.getModelController(PaymentProcessorTransactionControl.class);
        
        result.setPaymentProcessorTransactions(paymentProcessorTransactionControl.getPaymentProcessorTransactionTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
