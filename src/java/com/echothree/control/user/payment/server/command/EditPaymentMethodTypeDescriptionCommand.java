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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.edit.PaymentEditFactory;
import com.echothree.control.user.payment.common.edit.PaymentMethodTypeDescriptionEdit;
import com.echothree.control.user.payment.common.form.EditPaymentMethodTypeDescriptionForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.result.EditPaymentMethodTypeDescriptionResult;
import com.echothree.control.user.payment.common.spec.PaymentMethodTypeDescriptionSpec;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypeDescription;
import com.echothree.model.data.payment.server.value.PaymentMethodTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPaymentMethodTypeDescriptionCommand
        extends BaseEditCommand<PaymentMethodTypeDescriptionSpec, PaymentMethodTypeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentMethodType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPaymentMethodTypeDescriptionCommand */
    public EditPaymentMethodTypeDescriptionCommand(UserVisitPK userVisitPK, EditPaymentMethodTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        EditPaymentMethodTypeDescriptionResult result = PaymentResultFactory.getEditPaymentMethodTypeDescriptionResult();
        String paymentMethodTypeName = spec.getPaymentMethodTypeName();
        PaymentMethodType paymentMethodType = paymentMethodTypeControl.getPaymentMethodTypeByName(paymentMethodTypeName);
        
        if(paymentMethodType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    PaymentMethodTypeDescription paymentMethodTypeDescription = paymentMethodTypeControl.getPaymentMethodTypeDescription(paymentMethodType, language);
                    
                    if(paymentMethodTypeDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setPaymentMethodTypeDescription(paymentMethodTypeControl.getPaymentMethodTypeDescriptionTransfer(getUserVisit(), paymentMethodTypeDescription));

                            if(lockEntity(paymentMethodType)) {
                                PaymentMethodTypeDescriptionEdit edit = PaymentEditFactory.getPaymentMethodTypeDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(paymentMethodTypeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(paymentMethodType));
                        } else { // EditMode.ABANDON
                            unlockEntity(paymentMethodType);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPaymentMethodTypeDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    PaymentMethodTypeDescriptionValue paymentMethodTypeDescriptionValue = paymentMethodTypeControl.getPaymentMethodTypeDescriptionValueForUpdate(paymentMethodType, language);
                    
                    if(paymentMethodTypeDescriptionValue != null) {
                        if(lockEntityForUpdate(paymentMethodType)) {
                            try {
                                String description = edit.getDescription();
                                
                                paymentMethodTypeDescriptionValue.setDescription(description);
                                
                                paymentMethodTypeControl.updatePaymentMethodTypeDescriptionFromValue(paymentMethodTypeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(paymentMethodType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPaymentMethodTypeDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPaymentMethodTypeName.name(), paymentMethodTypeName);
        }
        
        return result;
    }
    
}
