// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.payment.remote.form.CreatePaymentProcessorForm;
import com.echothree.control.user.payment.remote.result.CreatePaymentProcessorResult;
import com.echothree.control.user.payment.remote.result.PaymentResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.payment.server.PaymentControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePaymentProcessorCommand
        extends BaseSimpleCommand<CreatePaymentProcessorForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentProcessor.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentProcessorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentProcessorTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreatePaymentProcessorCommand */
    public CreatePaymentProcessorCommand(UserVisitPK userVisitPK, CreatePaymentProcessorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        PaymentControl paymentControl = (PaymentControl)Session.getModelController(PaymentControl.class);
        CreatePaymentProcessorResult result = PaymentResultFactory.getCreatePaymentProcessorResult();
        String paymentProcessorName = form.getPaymentProcessorName();
        PaymentProcessor paymentProcessor = paymentControl.getPaymentProcessorByName(paymentProcessorName);
        
        if(paymentProcessor == null) {
            String paymentProcessorTypeName = form.getPaymentProcessorTypeName();
            PaymentProcessorType paymentProcessorType = paymentControl.getPaymentProcessorTypeByName(paymentProcessorTypeName);
            
            if(paymentProcessorType != null) {
                PartyPK partyPK = getPartyPK();
                Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                Integer sortOrder = Integer.valueOf(form.getSortOrder());
                String description = form.getDescription();
                
                paymentProcessor = paymentControl.createPaymentProcessor(paymentProcessorName, paymentProcessorType,
                        isDefault, sortOrder, partyPK);
                
                if(description != null) {
                    Language language = getPreferredLanguage();
                    
                    paymentControl.createPaymentProcessorDescription(paymentProcessor, language, description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPaymentProcessorTypeName.name(), paymentProcessorTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicatePaymentProcessorName.name(), paymentProcessorName);
        }

        if(paymentProcessor != null) {
            result.setEntityRef(paymentProcessor.getPrimaryKey().getEntityRef());
        }
        
        return result;
    }
    
}
