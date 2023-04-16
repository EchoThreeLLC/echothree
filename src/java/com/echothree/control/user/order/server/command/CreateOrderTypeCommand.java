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

package com.echothree.control.user.order.server.command;

import com.echothree.control.user.order.common.form.CreateOrderTypeForm;
import com.echothree.control.user.order.common.result.OrderResultFactory;
import com.echothree.model.control.order.server.control.OrderTypeControl;
import com.echothree.model.control.order.server.logic.OrderTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
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

public class CreateOrderTypeCommand
        extends BaseSimpleCommand<CreateOrderTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.OrderType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OrderTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentOrderTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OrderWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateOrderTypeCommand */
    public CreateOrderTypeCommand(UserVisitPK userVisitPK, CreateOrderTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = OrderResultFactory.getCreateOrderTypeResult();
        var orderTypeControl = Session.getModelController(OrderTypeControl.class);
        String parentOrderTypeName = form.getParentOrderTypeName();
        OrderType parentOrderType = null;
        OrderType orderType = null;

        if(parentOrderTypeName != null) {
            parentOrderType = orderTypeControl.getOrderTypeByName(parentOrderTypeName);
        }

        if(parentOrderTypeName == null || parentOrderType != null) {
            var sequenceControl = Session.getModelController(SequenceControl.class);
            String orderSequenceTypeName = form.getOrderSequenceTypeName();
            SequenceType orderSequenceType = sequenceControl.getSequenceTypeByName(orderSequenceTypeName);

            if(orderSequenceTypeName == null || orderSequenceType != null) {
                var workflowControl = Session.getModelController(WorkflowControl.class);
                String orderWorkflowName = form.getOrderWorkflowName();
                Workflow orderWorkflow = orderWorkflowName == null ? null : workflowControl.getWorkflowByName(orderWorkflowName);

                if(orderWorkflowName == null || orderWorkflow != null) {
                    String orderWorkflowEntranceName = form.getOrderWorkflowEntranceName();

                    if(orderWorkflowEntranceName == null || (orderWorkflow != null && orderWorkflowEntranceName != null)) {
                        WorkflowEntrance orderWorkflowEntrance = orderWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(orderWorkflow, orderWorkflowEntranceName);

                        if(orderWorkflowEntranceName == null || orderWorkflowEntrance != null) {
                            var orderTypeName = form.getOrderTypeName();
                            var isDefault = Boolean.valueOf(form.getIsDefault());
                            var sortOrder = Integer.valueOf(form.getSortOrder());
                            var description = form.getDescription();
                            var partyPK = getPartyPK();

                            orderType = OrderTypeLogic.getInstance().createOrderType(this, orderTypeName, parentOrderType, orderSequenceType, orderWorkflow,
                                    orderWorkflowEntrance, isDefault, sortOrder, getPreferredLanguage(), description, partyPK);
                        } else {
                            addExecutionError(ExecutionErrors.UnknownOrderWorkflowEntranceName.name(), orderWorkflowName, orderWorkflowEntranceName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.MissingRequiredOrderWorkflowName.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownOrderWorkflowName.name(), orderWorkflowName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownOrderSequenceTypeName.name(), orderSequenceTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownParentOrderTypeName.name(), parentOrderTypeName);
        }

        if(orderType != null) {
            result.setEntityRef(orderType.getPrimaryKey().getEntityRef());
            result.setOrderTypeName(orderType.getLastDetail().getOrderTypeName());
        }

        return result;
    }
    
}
