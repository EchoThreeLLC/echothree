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
import com.echothree.control.user.order.common.edit.OrderLineAdjustmentTypeEdit;
import com.echothree.control.user.order.common.form.EditOrderLineAdjustmentTypeForm;
import com.echothree.control.user.order.common.result.EditOrderLineAdjustmentTypeResult;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.control.user.order.common.spec.OrderLineAdjustmentTypeSpec;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentTypeDescription;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentTypeDetail;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderLineAdjustmentTypeDescriptionValue;
import com.echothree.model.data.order.server.value.OrderLineAdjustmentTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditOrderLineAdjustmentTypeCommand
        extends BaseAbstractEditCommand<OrderLineAdjustmentTypeSpec, OrderLineAdjustmentTypeEdit, EditOrderLineAdjustmentTypeResult, OrderLineAdjustmentType, OrderLineAdjustmentType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.OrderLineAdjustmentType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("OrderLineAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderLineAdjustmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditOrderLineAdjustmentTypeCommand */
    public EditOrderLineAdjustmentTypeCommand(UserVisitPK userVisitPK, EditOrderLineAdjustmentTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditOrderLineAdjustmentTypeResult getResult() {
        return OrderResultFactory.getEditOrderLineAdjustmentTypeResult();
    }

    @Override
    public OrderLineAdjustmentTypeEdit getEdit() {
        return OrderEditFactory.getOrderLineAdjustmentTypeEdit();
    }

    @Override
    public OrderLineAdjustmentType getEntity(EditOrderLineAdjustmentTypeResult result) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderLineAdjustmentType orderLineAdjustmentType = null;
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderLineAdjustmentTypeName = spec.getOrderLineAdjustmentTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                orderLineAdjustmentType = orderControl.getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName);
            } else { // EditMode.UPDATE
                orderLineAdjustmentType = orderControl.getOrderLineAdjustmentTypeByNameForUpdate(orderType, orderLineAdjustmentTypeName);
            }

            if(orderLineAdjustmentType != null) {
                result.setOrderLineAdjustmentType(orderControl.getOrderLineAdjustmentTypeTransfer(getUserVisit(), orderLineAdjustmentType));
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderLineAdjustmentTypeName.name(), orderTypeName, orderLineAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }

        return orderLineAdjustmentType;
    }

    @Override
    public OrderLineAdjustmentType getLockEntity(OrderLineAdjustmentType orderLineAdjustmentType) {
        return orderLineAdjustmentType;
    }

    @Override
    public void fillInResult(EditOrderLineAdjustmentTypeResult result, OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);

        result.setOrderLineAdjustmentType(orderControl.getOrderLineAdjustmentTypeTransfer(getUserVisit(), orderLineAdjustmentType));
    }

    @Override
    public void doLock(OrderLineAdjustmentTypeEdit edit, OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription = orderControl.getOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, getPreferredLanguage());
        OrderLineAdjustmentTypeDetail orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getLastDetail();

        edit.setOrderLineAdjustmentTypeName(orderLineAdjustmentTypeDetail.getOrderLineAdjustmentTypeName());
        edit.setIsDefault(orderLineAdjustmentTypeDetail.getIsDefault().toString());
        edit.setSortOrder(orderLineAdjustmentTypeDetail.getSortOrder().toString());

        if(orderLineAdjustmentTypeDescription != null) {
            edit.setDescription(orderLineAdjustmentTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        String orderTypeName = spec.getOrderTypeName();
        OrderType orderType = orderControl.getOrderTypeByName(orderTypeName);

        if(orderType != null) {
            String orderLineAdjustmentTypeName = edit.getOrderLineAdjustmentTypeName();
            OrderLineAdjustmentType duplicateOrderLineAdjustmentType = orderControl.getOrderLineAdjustmentTypeByName(orderType, orderLineAdjustmentTypeName);

            if(duplicateOrderLineAdjustmentType != null && !orderLineAdjustmentType.equals(duplicateOrderLineAdjustmentType)) {
                addExecutionError(ExecutionErrors.DuplicateOrderLineAdjustmentTypeName.name(), orderTypeName, orderLineAdjustmentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOrderTypeName.name(), orderTypeName);
        }
    }

    @Override
    public void doUpdate(OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        PartyPK partyPK = getPartyPK();
        OrderLineAdjustmentTypeDetailValue orderLineAdjustmentTypeDetailValue = orderControl.getOrderLineAdjustmentTypeDetailValueForUpdate(orderLineAdjustmentType);
        OrderLineAdjustmentTypeDescription orderLineAdjustmentTypeDescription = orderControl.getOrderLineAdjustmentTypeDescriptionForUpdate(orderLineAdjustmentType, getPreferredLanguage());
        String description = edit.getDescription();

        orderLineAdjustmentTypeDetailValue.setOrderLineAdjustmentTypeName(edit.getOrderLineAdjustmentTypeName());
        orderLineAdjustmentTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        orderLineAdjustmentTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        orderControl.updateOrderLineAdjustmentTypeFromValue(orderLineAdjustmentTypeDetailValue, partyPK);

        if(orderLineAdjustmentTypeDescription == null && description != null) {
            orderControl.createOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, getPreferredLanguage(), description, partyPK);
        } else {
            if(orderLineAdjustmentTypeDescription != null && description == null) {
                orderControl.deleteOrderLineAdjustmentTypeDescription(orderLineAdjustmentTypeDescription, partyPK);
            } else {
                if(orderLineAdjustmentTypeDescription != null && description != null) {
                    OrderLineAdjustmentTypeDescriptionValue orderLineAdjustmentTypeDescriptionValue = orderControl.getOrderLineAdjustmentTypeDescriptionValue(orderLineAdjustmentTypeDescription);

                    orderLineAdjustmentTypeDescriptionValue.setDescription(description);
                    orderControl.updateOrderLineAdjustmentTypeDescriptionFromValue(orderLineAdjustmentTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
