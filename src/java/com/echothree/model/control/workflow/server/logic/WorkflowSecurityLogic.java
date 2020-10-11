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

package com.echothree.model.control.workflow.server.logic;

import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.factory.PartyFactory;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationPartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSecurityRole;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSecurityRole;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class WorkflowSecurityLogic {
    
    private WorkflowSecurityLogic() {
        super();
    }
    
    private static class WorkflowSecurityLogicHolder {
        static WorkflowSecurityLogic instance = new WorkflowSecurityLogic();
    }
    
    public static WorkflowSecurityLogic getInstance() {
        return WorkflowSecurityLogicHolder.instance;
    }
    
    public boolean checkWorkflowEntranceAvailable(final WorkflowEntrance workflowEntrance, final PartyPK partyPK) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        boolean checkPassed = false;

        if(workflowControl.countWorkflowEntrancePartyTypes(workflowEntrance) != 0) {
            Party party = PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, partyPK);
            PartyType partyType = party.getLastDetail().getPartyType();
            WorkflowEntrancePartyType workflowEntrancePartyType = workflowControl.getWorkflowEntrancePartyType(workflowEntrance, partyType);

            if(workflowEntrancePartyType != null) {
                var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
                List<WorkflowEntranceSecurityRole> workflowEntranceSecurityRoles = workflowControl.getWorkflowEntranceSecurityRolesByWorkflowEntrancePartyType(workflowEntrancePartyType);

                if(workflowEntranceSecurityRoles.isEmpty()) {
                    // If there are no individual Security Roles, then pass it since the user is in a Party Type that was found.
                    checkPassed = true;
                } else {
                    // Otherwise, check each individual Security Role.
                    for(WorkflowEntranceSecurityRole workflowEntranceSecurityRole: workflowEntranceSecurityRoles) {
                        if(securityControl.partySecurityRoleExists(partyPK, workflowEntranceSecurityRole.getSecurityRolePK())) {
                            // The Party has one of the required Security Roles, allow the transition and stop further checking.
                            checkPassed = true;
                            break;
                        }
                    }
                }
            }
        } else {
            // If there are no Workflow Entrance Party Types, then allow the transition.
            checkPassed = true;
        }

        return checkPassed;
    }

    public boolean checkAddEntityToWorkflow(final ExecutionErrorAccumulator eea, final WorkflowEntrance workflowEntrance, final PartyPK modifiedBy) {
        boolean checkPassed = checkWorkflowEntranceAvailable(workflowEntrance, modifiedBy);
        
        if(!checkPassed) {
            eea.addExecutionError(ExecutionErrors.WorkflowEntranceNotAllowed.name());
        }

        return checkPassed;
    }

    public boolean checkWorkflowDestinationAvailable(final WorkflowDestination workflowDestination, final PartyPK partyPK) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        boolean checkPassed = false;

        if(workflowControl.countWorkflowDestinationPartyTypes(workflowDestination) != 0) {
            Party party = PartyFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, partyPK);
            PartyType partyType = party.getLastDetail().getPartyType();
            WorkflowDestinationPartyType workflowDestinationPartyType = workflowControl.getWorkflowDestinationPartyType(workflowDestination, partyType);

            if(workflowDestinationPartyType != null) {
                var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
                List<WorkflowDestinationSecurityRole> workflowDestinationSecurityRoles = workflowControl.getWorkflowDestinationSecurityRolesByWorkflowDestinationPartyType(workflowDestinationPartyType);

                if(workflowDestinationSecurityRoles.isEmpty()) {
                    // If there are no individual Security Roles, then pass it since the user is in a Party Type that was found.
                    checkPassed = true;
                } else {
                    // Otherwise, check each individual Security Role.
                    for(WorkflowDestinationSecurityRole workflowDestinationSecurityRole: workflowDestinationSecurityRoles) {
                        if(securityControl.partySecurityRoleExists(partyPK, workflowDestinationSecurityRole.getSecurityRolePK())) {
                            // The Party has one of the required Security Roles, allow the transition and stop further checking.
                            checkPassed = true;
                            break;
                        }
                    }
                }
            }
        } else {
            // If there are no Workflow Destination Party Types, then allow the transition.
            checkPassed = true;
        }

        return checkPassed;
    }

    public boolean checkTransitionEntityInWorkflow(final ExecutionErrorAccumulator eea, final WorkflowDestination workflowDestination, final PartyPK modifiedBy) {
        boolean checkPassed = checkWorkflowDestinationAvailable(workflowDestination, modifiedBy);

        if(!checkPassed) {
            eea.addExecutionError(ExecutionErrors.WorkflowDestinationNotAllowed.name());
        }

        return checkPassed;
    }

}
