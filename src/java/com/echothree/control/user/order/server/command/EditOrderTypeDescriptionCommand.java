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

package com.echothree.control.user.order.server.command;

import com.echothree.control.user.order.common.edit.OrderEditFactory;
import com.echothree.control.user.order.common.edit.OrderTypeDescriptionEdit;
import com.echothree.control.user.order.common.form.EditOrderTypeDescriptionForm;
import com.echothree.control.user.order.common.result.EditOrderTypeDescriptionResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderTypeDescriptionSpec;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.entity.OrderTypeDescription;
import com.echothree.model.data.order.server.value.OrderTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditOrderTypeDescriptionCommand
        extends BaseAbstractEditCommand<OrderTypeDescriptionSpec, OrderTypeDescriptionEdit, EditOrderTypeDescriptionResult, OrderTypeDescription, OrderType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OrderType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOrderTypeDescriptionCommand */
    public EditOrderTypeDescriptionCommand(UserVisitPK userVisitPK, EditOrderTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditOrderTypeDescriptionResult getResult() {
        return OrderResultFactory.getEditOrderTypeDescriptionResult();
    }

    @Override
    public OrderTypeDescriptionEdit getEdit() {
        return OrderEditFactory.getOrderTypeDescriptionEdit();
    }

    @Override
    public OrderTypeDescription getEntity(EditOrderTypeDescriptionResult result) {
        var orderTypeControl = (OrderTypeControl)Session.getModelController(OrderTypeControl.class);
        OrderTypeDescription orderTypeDescription = null;
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    orderTypeDescription = orderTypeControl.getOrderTypeDescription(orderType, language);
                } else { // EditMode.UPDATE
                    orderTypeDescription = orderTypeControl.getOrderTypeDescriptionForUpdate(orderType, language);
                }

                if(orderTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownOrderTypeDescription.name(), orderTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderTypeDescription;
    }

    @Override
    public OrderType getLockEntity(OrderTypeDescription orderTypeDescription) {
        return orderTypeDescription.getOrderType();
    }

    @Override
    public void fillInResult(EditOrderTypeDescriptionResult result, OrderTypeDescription orderTypeDescription) {
        var orderTypeControl = (OrderTypeControl)Session.getModelController(OrderTypeControl.class);

        result.setOrderTypeDescription(orderTypeControl.getOrderTypeDescriptionTransfer(getUserVisit(), orderTypeDescription));
    }

    @Override
    public void doLock(OrderTypeDescriptionEdit edit, OrderTypeDescription orderTypeDescription) {
        edit.setDescription(orderTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(OrderTypeDescription orderTypeDescription) {
        var orderTypeControl = (OrderTypeControl)Session.getModelController(OrderTypeControl.class);
        OrderTypeDescriptionValue orderTypeDescriptionValue = orderTypeControl.getOrderTypeDescriptionValue(orderTypeDescription);
        orderTypeDescriptionValue.setDescription(edit.getDescription());

        orderTypeControl.updateOrderTypeDescriptionFromValue(orderTypeDescriptionValue, getPartyPK());
    }
    
}
