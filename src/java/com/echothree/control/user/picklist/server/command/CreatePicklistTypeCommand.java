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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.common.form.CreatePicklistTypeForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.picklist.server.PicklistControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePicklistTypeCommand
        extends BaseSimpleCommand<CreatePicklistTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PicklistType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentPicklistTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PicklistSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PicklistWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PicklistWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreatePicklistTypeCommand */
    public CreatePicklistTypeCommand(UserVisitPK userVisitPK, CreatePicklistTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        String picklistTypeName = form.getPicklistTypeName();
        PicklistType picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType == null) {
            String parentPicklistTypeName = form.getParentPicklistTypeName();
            PicklistType parentPicklistType = null;

            if(parentPicklistTypeName != null) {
                parentPicklistType = picklistControl.getPicklistTypeByName(parentPicklistTypeName);
            }

            if(parentPicklistTypeName == null || parentPicklistType != null) {
                var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                String picklistSequenceTypeName = form.getPicklistSequenceTypeName();
                SequenceType picklistSequenceType = sequenceControl.getSequenceTypeByName(picklistSequenceTypeName);

                if(picklistSequenceTypeName == null || picklistSequenceType != null) {
                    var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                    String picklistWorkflowName = form.getPicklistWorkflowName();
                    Workflow picklistWorkflow = picklistWorkflowName == null ? null : workflowControl.getWorkflowByName(picklistWorkflowName);

                    if(picklistWorkflowName == null || picklistWorkflow != null) {
                        String picklistWorkflowEntranceName = form.getPicklistWorkflowEntranceName();

                        if(picklistWorkflowEntranceName == null || (picklistWorkflow != null && picklistWorkflowEntranceName != null)) {
                            WorkflowEntrance picklistWorkflowEntrance = picklistWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(picklistWorkflow, picklistWorkflowEntranceName);

                            if(picklistWorkflowEntranceName == null || picklistWorkflowEntrance != null) {
                                var partyPK = getPartyPK();
                                var isDefault = Boolean.valueOf(form.getIsDefault());
                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                var description = form.getDescription();

                                picklistType = picklistControl.createPicklistType(picklistTypeName, parentPicklistType, picklistSequenceType, picklistWorkflow,
                                        picklistWorkflowEntrance, isDefault, sortOrder, partyPK);

                                if(description != null) {
                                    picklistControl.createPicklistTypeDescription(picklistType, getPreferredLanguage(), description, partyPK);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownPicklistWorkflowEntranceName.name(), picklistWorkflowName, picklistWorkflowEntranceName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MissingRequiredPicklistWorkflowName.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPicklistWorkflowName.name(), picklistWorkflowName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPicklistSequenceTypeName.name(), picklistSequenceTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentPicklistTypeName.name(), parentPicklistTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicatePicklistTypeName.name(), picklistTypeName);
        }

        return null;
    }
    
}
