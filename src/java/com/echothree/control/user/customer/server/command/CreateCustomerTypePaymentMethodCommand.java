// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.common.form.CreateCustomerTypePaymentMethodForm;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypePaymentMethod;
import com.echothree.model.data.payment.server.entity.PaymentMethod;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCustomerTypePaymentMethodCommand
        extends BaseSimpleCommand<CreateCustomerTypePaymentMethodForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultSelectionPriority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCustomerTypePaymentMethodCommand */
    public CreateCustomerTypePaymentMethodCommand(UserVisitPK userVisitPK, CreateCustomerTypePaymentMethodForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var customerControl = Session.getModelController(CustomerControl.class);
        String customerTypeName = form.getCustomerTypeName();
        CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);
        
        if(customerType != null) {
            var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
            String paymentMethodName = form.getPaymentMethodName();
            PaymentMethod paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);
            
            if(paymentMethod != null) {
                CustomerTypePaymentMethod customerTypePaymentMethod = customerControl.getCustomerTypePaymentMethod(customerType,
                        paymentMethod);
                
                if(customerTypePaymentMethod == null) {
                    Integer defaultSelectionPriority = Integer.valueOf(form.getDefaultSelectionPriority());
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    
                    customerControl.createCustomerTypePaymentMethod(customerType, paymentMethod, defaultSelectionPriority, isDefault, sortOrder, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.DuplicateCustomerTypePaymentMethod.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }
        
        return null;
    }
    
}
