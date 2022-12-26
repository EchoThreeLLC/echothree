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
import com.echothree.control.user.payment.common.edit.PaymentMethodTypeEdit;
import com.echothree.control.user.payment.common.form.EditPaymentMethodTypeForm;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.result.EditPaymentMethodTypeResult;
import com.echothree.control.user.payment.common.spec.PaymentMethodTypeUniversalSpec;
import com.echothree.model.control.payment.server.control.PaymentMethodTypeControl;
import com.echothree.model.control.payment.server.logic.PaymentMethodTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypeDescription;
import com.echothree.model.data.payment.server.entity.PaymentMethodTypeDetail;
import com.echothree.model.data.payment.server.value.PaymentMethodTypeDescriptionValue;
import com.echothree.model.data.payment.server.value.PaymentMethodTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPaymentMethodTypeCommand
        extends BaseAbstractEditCommand<PaymentMethodTypeUniversalSpec, PaymentMethodTypeEdit, EditPaymentMethodTypeResult, PaymentMethodType, PaymentMethodType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PaymentMethodType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PaymentMethodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPaymentMethodTypeCommand */
    public EditPaymentMethodTypeCommand(UserVisitPK userVisitPK, EditPaymentMethodTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPaymentMethodTypeResult getResult() {
        return PaymentResultFactory.getEditPaymentMethodTypeResult();
    }
    
    @Override
    public PaymentMethodTypeEdit getEdit() {
        return PaymentEditFactory.getPaymentMethodTypeEdit();
    }
    
    @Override
    public PaymentMethodType getEntity(EditPaymentMethodTypeResult result) {
        return PaymentMethodTypeLogic.getInstance().getPaymentMethodTypeByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }
    
    @Override
    public PaymentMethodType getLockEntity(PaymentMethodType paymentMethodType) {
        return paymentMethodType;
    }
    
    @Override
    public void fillInResult(EditPaymentMethodTypeResult result, PaymentMethodType paymentMethodType) {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        
        result.setPaymentMethodType(paymentMethodTypeControl.getPaymentMethodTypeTransfer(getUserVisit(), paymentMethodType));
    }
    
    @Override
    public void doLock(PaymentMethodTypeEdit edit, PaymentMethodType paymentMethodType) {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        PaymentMethodTypeDescription paymentMethodTypeDescription = paymentMethodTypeControl.getPaymentMethodTypeDescription(paymentMethodType, getPreferredLanguage());
        PaymentMethodTypeDetail paymentMethodTypeDetail = paymentMethodType.getLastDetail();
        
        edit.setPaymentMethodTypeName(paymentMethodTypeDetail.getPaymentMethodTypeName());
        edit.setIsDefault(paymentMethodTypeDetail.getIsDefault().toString());
        edit.setSortOrder(paymentMethodTypeDetail.getSortOrder().toString());

        if(paymentMethodTypeDescription != null) {
            edit.setDescription(paymentMethodTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(PaymentMethodType paymentMethodType) {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        String paymentMethodTypeName = edit.getPaymentMethodTypeName();
        PaymentMethodType duplicatePaymentMethodType = paymentMethodTypeControl.getPaymentMethodTypeByName(paymentMethodTypeName);

        if(duplicatePaymentMethodType != null && !paymentMethodType.equals(duplicatePaymentMethodType)) {
            addExecutionError(ExecutionErrors.DuplicatePaymentMethodTypeName.name(), paymentMethodTypeName);
        }
    }
    
    @Override
    public void doUpdate(PaymentMethodType paymentMethodType) {
        var paymentMethodTypeControl = Session.getModelController(PaymentMethodTypeControl.class);
        var partyPK = getPartyPK();
        PaymentMethodTypeDetailValue paymentMethodTypeDetailValue = paymentMethodTypeControl.getPaymentMethodTypeDetailValueForUpdate(paymentMethodType);
        PaymentMethodTypeDescription paymentMethodTypeDescription = paymentMethodTypeControl.getPaymentMethodTypeDescriptionForUpdate(paymentMethodType, getPreferredLanguage());
        String description = edit.getDescription();

        paymentMethodTypeDetailValue.setPaymentMethodTypeName(edit.getPaymentMethodTypeName());
        paymentMethodTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        paymentMethodTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        paymentMethodTypeControl.updatePaymentMethodTypeFromValue(paymentMethodTypeDetailValue, partyPK);

        if(paymentMethodTypeDescription == null && description != null) {
            paymentMethodTypeControl.createPaymentMethodTypeDescription(paymentMethodType, getPreferredLanguage(), description, partyPK);
        } else if(paymentMethodTypeDescription != null && description == null) {
            paymentMethodTypeControl.deletePaymentMethodTypeDescription(paymentMethodTypeDescription, partyPK);
        } else if(paymentMethodTypeDescription != null && description != null) {
            PaymentMethodTypeDescriptionValue paymentMethodTypeDescriptionValue = paymentMethodTypeControl.getPaymentMethodTypeDescriptionValue(paymentMethodTypeDescription);

            paymentMethodTypeDescriptionValue.setDescription(description);
            paymentMethodTypeControl.updatePaymentMethodTypeDescriptionFromValue(paymentMethodTypeDescriptionValue, partyPK);
        }
    }
    
}
