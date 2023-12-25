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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.form.GetWorkflowDestinationSecurityRolesForm;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationPartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSecurityRole;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetWorkflowDestinationSecurityRolesCommand
        extends BaseMultipleEntitiesCommand<WorkflowDestinationSecurityRole, GetWorkflowDestinationSecurityRolesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.SecurityRole.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetWorkflowDestinationSecurityRolesCommand */
    public GetWorkflowDestinationSecurityRolesCommand(UserVisitPK userVisitPK, GetWorkflowDestinationSecurityRolesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    WorkflowDestinationPartyType workflowDestinationPartyType;

    @Override
    protected Collection<WorkflowDestinationSecurityRole> getEntities() {
        var workflowName = form.getWorkflowName();
        var workflowStepName = form.getWorkflowStepName();
        var workflowDestinationName = form.getWorkflowDestinationName();
        var partyTypeName = form.getPartyTypeName();
        Collection<WorkflowDestinationSecurityRole> workflowDestinationSecurityRoles = null;

        workflowDestinationPartyType = WorkflowDestinationLogic.getInstance().getWorkflowDestinationPartyTypeByName(this, workflowName,
                workflowStepName, workflowDestinationName, partyTypeName);

        if(!this.hasExecutionErrors()) {
            var workflowControl = Session.getModelController(WorkflowControl.class);

            workflowDestinationSecurityRoles = workflowControl.getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType);
        }

        return workflowDestinationSecurityRoles;
    }

    @Override
    protected BaseResult getTransfers(Collection<WorkflowDestinationSecurityRole> entities) {
        var result = WorkflowResultFactory.getGetWorkflowDestinationSecurityRolesResult();

        if(entities != null) {
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var userVisit = getUserVisit();

            result.setWorkflowDestinationPartyType(workflowControl.getWorkflowDestinationPartyTypeTransfer(userVisit, workflowDestinationPartyType));
            result.setWorkflowDestinationSecurityRoles(workflowControl.getWorkflowDestinationSecurityRoleTransfers(userVisit, entities));
        }

        return result;
    }

}
