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

import com.echothree.control.user.order.common.form.CreateOrderLineAdjustmentTypeForm;
import com.echothree.model.control.order.server.control.OrderLineAdjustmentControl;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateOrderLineAdjustmentTypeCommand
        extends BaseSimpleCommand<CreateOrderLineAdjustmentTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.OrderLineAdjustmentType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderLineAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateOrderLineAdjustmentTypeCommand */
    public CreateOrderLineAdjustmentTypeCommand(UserVisitPK userVisitPK, CreateOrderLineAdjustmentTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var orderTypeControl = (OrderTypeControl)Session.getModelController(OrderTypeControl.class);
        String orderTypeName = form.getOrderTypeName();
        OrderType orderType = orderTypeControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            var orderLineAdjustmentControl = (OrderLineAdjustmentControl)Session.getModelController(OrderLineAdjustmentControl.class);
            String orderLineAdjustmentTypeName = form.getOrderLineAdjustmentTypeName();
            OrderLineAdjustmentType orderLineAdjustmentType = orderLineAdjustmentControl.getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName);

            if(orderLineAdjustmentType == null) {
                PartyPK partyPK = getPartyPK();
                Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                Integer sortOrder = Integer.valueOf(form.getSortOrder());
                String description = form.getDescription();

                orderLineAdjustmentType = orderLineAdjustmentControl.createOrderLineAdjustmentType(orderType, orderLineAdjustmentTypeName, isDefault, sortOrder, partyPK);

                if(description != null) {
                    orderLineAdjustmentControl.createOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, getPreferredLanguage(), description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateOrderLineAdjustmentTypeName.name(), orderTypeName, orderLineAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return null;
    }
    
}
