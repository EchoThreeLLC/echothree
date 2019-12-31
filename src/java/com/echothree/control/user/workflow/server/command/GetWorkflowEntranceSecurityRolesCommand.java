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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.form.GetWorkflowEntranceSecurityRolesForm;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSecurityRole;
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

public class GetWorkflowEntranceSecurityRolesCommand
        extends BaseMultipleEntitiesCommand<WorkflowEntranceSecurityRole, GetWorkflowEntranceSecurityRolesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowEntrance.name(), SecurityRoles.SecurityRole.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetWorkflowEntranceSecurityRolesCommand */
    public GetWorkflowEntranceSecurityRolesCommand(UserVisitPK userVisitPK, GetWorkflowEntranceSecurityRolesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    WorkflowEntrancePartyType workflowEntrancePartyType;

    @Override
    protected Collection<WorkflowEntranceSecurityRole> getEntities() {
        var workflowName = form.getWorkflowName();
        var workflowEntranceName = form.getWorkflowEntranceName();
        var partyTypeName = form.getPartyTypeName();
        Collection<WorkflowEntranceSecurityRole> workflowEntranceSecurityRoles = null;

        workflowEntrancePartyType = WorkflowEntranceLogic.getInstance().getWorkflowEntrancePartyTypeByName(this,
                workflowName, workflowEntranceName, partyTypeName);

        if(!this.hasExecutionErrors()) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

            workflowEntranceSecurityRoles = workflowControl.getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType);
        }

        return workflowEntranceSecurityRoles;
    }

    @Override
    protected BaseResult getTransfers(Collection<WorkflowEntranceSecurityRole> entities) {
        var result = WorkflowResultFactory.getGetWorkflowEntranceSecurityRolesResult();

        if(entities != null) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
            var userVisit = getUserVisit();

            result.setWorkflowEntrancePartyType(workflowControl.getWorkflowEntrancePartyTypeTransfer(userVisit, workflowEntrancePartyType));
            result.setWorkflowEntranceSecurityRoles(workflowControl.getWorkflowEntranceSecurityRoleTransfers(userVisit, entities));
        }

        return result;
    }

}
