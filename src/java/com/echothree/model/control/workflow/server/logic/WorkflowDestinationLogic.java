// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.selector.server.logic.SelectorLogic;
import com.echothree.model.control.workflow.common.exception.UnknownDestinationWorkflowNameException;
import com.echothree.model.control.workflow.common.exception.UnknownDestinationWorkflowStepNameException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowDestinationPartyTypeException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowDestinationSecurityRoleException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowDestinationSelectorException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowDestinationStepException;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowNameException;
import com.echothree.model.control.workflow.common.exception.WorkflowMissingSecurityRoleGroupException;
import com.echothree.model.control.workflow.common.exception.WorkflowMissingSelectorTypeException;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationDetail;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationPartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSecurityRole;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationSelector;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDetail;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorkflowDestinationLogic
        extends BaseLogic {

    private WorkflowDestinationLogic() {
        super();
    }
    
    private static class WorkflowDestinationLogicHolder {
        static WorkflowDestinationLogic instance = new WorkflowDestinationLogic();
    }
    
    public static WorkflowDestinationLogic getInstance() {
        return WorkflowDestinationLogicHolder.instance;
    }
    
    public WorkflowDestination getWorkflowDestinationByName(final ExecutionErrorAccumulator eea, final WorkflowStep workflowStep, final String workflowDestinationName) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        WorkflowDestination workflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);

        if(workflowDestination == null) {
            WorkflowStepDetail workflowStepDetail = workflowStep.getLastDetail();
            
            handleExecutionError(UnknownWorkflowNameException.class, eea, ExecutionErrors.UnknownWorkflowDestinationName.name(),
                    workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName(), workflowStepDetail.getWorkflowStepName(), workflowDestinationName);
        }

        return workflowDestination;
    }

    public WorkflowDestination getWorkflowDestinationByName(final ExecutionErrorAccumulator eea, final Workflow workflow, final String workflowStepName,
            final String workflowDestinationName) {
        WorkflowStep workflowStep = WorkflowStepLogic.getInstance().getWorkflowStepByName(eea, workflow, workflowStepName);
        WorkflowDestination workflowDestination = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            workflowDestination = getWorkflowDestinationByName(eea, workflowStep, workflowDestinationName);
        }
        
        return workflowDestination;
    }

    public WorkflowDestination getWorkflowDestinationByName(final ExecutionErrorAccumulator eea, final String workflowName, final String workflowStepName,
            final String workflowDestinationName) {
        WorkflowStep workflowStep = WorkflowStepLogic.getInstance().getWorkflowStepByName(eea, workflowName, workflowStepName);
        WorkflowDestination workflowDestination = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            workflowDestination = getWorkflowDestinationByName(eea, workflowStep, workflowDestinationName);
        }
        
        return workflowDestination;
    }

    public Set<WorkflowStep> getWorkflowDestinationStepsAsSet(final WorkflowDestination workflowDestination) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        List<WorkflowDestinationStep> workflowDestinationSteps = workflowControl.getWorkflowDestinationStepsByWorkflowDestination(workflowDestination);
        Set<WorkflowStep> workflowSteps = new HashSet<>(workflowDestinationSteps.size());
        
        workflowDestinationSteps.stream().forEach((workflowDestinationStep) -> {
            workflowSteps.add(workflowDestinationStep.getWorkflowStep());
        });
        
        return workflowSteps;
    }
    
    public Map<String, Set<String>> getWorkflowDestinationsAsMap(final WorkflowDestination workflowDestination) {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        List<WorkflowDestinationStep> workflowDestinationSteps = workflowControl.getWorkflowDestinationStepsByWorkflowDestination(workflowDestination);
        Map<String, Set<String>> map = new HashMap<>();
        
        workflowDestinationSteps.stream().map((workflowDestinationStep) -> workflowDestinationStep.getWorkflowStep().getLastDetail()).forEach((workflowStepDetail) -> {
            String workflowStepName = workflowStepDetail.getWorkflowStepName();
            String workflowName = workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName();
            
            workflowDestinationMapContainsStep(map, workflowName, workflowStepName, true);
        });
        
        return map;
    }
    
    private boolean workflowDestinationMapContainsStep(final Map<String, Set<String>> map, final String workflowName, final String workflowStepName, final boolean addIt) {
        Set<String> workflowSteps = map.get(workflowName);

        if(workflowSteps == null && addIt) {
            workflowSteps = new HashSet<>();
            map.put(workflowName, workflowSteps);
        }
        
        boolean found = workflowSteps == null ? false : workflowSteps.contains(workflowStepName);

        if(!found && addIt) {
            workflowSteps.add(workflowStepName);
        }
        
        return found;
    }

    public boolean workflowDestinationMapContainsStep(final Map<String, Set<String>> map, String workflowName, String workflowStepName) {
       return workflowDestinationMapContainsStep(map, workflowName, workflowStepName, false);
    }

    public WorkflowDestinationPartyType getWorkflowDestinationPartyType(final ExecutionErrorAccumulator eea, final WorkflowDestination workflowDestination,
            final PartyType partyType, final EntityPermission entityPermission) {
        WorkflowDestinationPartyType workflowDestinationPartyType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

            workflowDestinationPartyType = workflowControl.getWorkflowDestinationPartyType(workflowDestination, partyType, entityPermission);

            if(workflowDestinationPartyType == null) {
               var workflowDestinationDetail = workflowDestination.getLastDetail();
               var workflowStepDetail = workflowDestinationDetail.getWorkflowStep().getLastDetail();

                handleExecutionError(UnknownWorkflowDestinationPartyTypeException.class, eea, ExecutionErrors.UnknownWorkflowDestinationPartyType.name(),
                        workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName(), workflowStepDetail.getWorkflowStepName(),
                        workflowDestinationDetail.getWorkflowDestinationName(), partyType.getPartyTypeName());
            }
        }

        return workflowDestinationPartyType;
    }

    public WorkflowDestinationPartyType getWorkflowDestinationPartyType(final ExecutionErrorAccumulator eea, final WorkflowDestination workflowDestination,
            final PartyType partyType) {
        return getWorkflowDestinationPartyType(eea, workflowDestination, partyType, EntityPermission.READ_ONLY);
    }

    public WorkflowDestinationPartyType getWorkflowDestinationPartyTypeForUpdate(final ExecutionErrorAccumulator eea, final WorkflowDestination workflowDestination,
            final PartyType partyType) {
        return getWorkflowDestinationPartyType(eea, workflowDestination, partyType, EntityPermission.READ_WRITE);
    }

    public WorkflowDestinationPartyType getWorkflowDestinationPartyTypeByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowStepName, final String workflowDestinationName, final String partyTypeName, final EntityPermission entityPermission) {
        var workflowDestination = getWorkflowDestinationByName(eea, workflowName, workflowStepName, workflowDestinationName);
        var partyType = PartyLogic.getInstance().getPartyTypeByName(eea, partyTypeName);

        return getWorkflowDestinationPartyType(eea, workflowDestination, partyType, entityPermission);
    }

    public WorkflowDestinationPartyType getWorkflowDestinationPartyTypeByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowStepName, final String workflowDestinationName, final String partyTypeName) {
        return getWorkflowDestinationPartyTypeByName(eea, workflowName, workflowStepName, workflowDestinationName, partyTypeName,
                EntityPermission.READ_ONLY);
    }


    public WorkflowDestinationPartyType getWorkflowDestinationPartyTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowStepName, final String workflowDestinationName, final String partyTypeName) {
        return getWorkflowDestinationPartyTypeByName(eea, workflowName, workflowStepName, workflowDestinationName, partyTypeName,
                EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSecurityRole getWorkflowDestinationSecurityRoleByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowStepName, final String workflowDestinationName, final String partyTypeName, final String securityRoleName,
            final EntityPermission entityPermission) {
        var workflowDestination = getWorkflowDestinationByName(eea, workflowName, workflowStepName, workflowDestinationName);
        var partyType = PartyLogic.getInstance().getPartyTypeByName(eea, partyTypeName);
        WorkflowDestinationSecurityRole workflowDestinationSecurityRole = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var securityRoleGroup = workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflow().getLastDetail().getSecurityRoleGroup();

            if(securityRoleGroup != null) {
                var securityRole = SecurityRoleLogic.getInstance().getSecurityRoleByName(eea, securityRoleGroup, securityRoleName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var workflowDestinationPartyType = getWorkflowDestinationPartyType(eea, workflowDestination, partyType);

                    if(eea == null || !eea.hasExecutionErrors()) {
                        var workflowControl = (WorkflowControl) Session.getModelController(WorkflowControl.class);

                        workflowDestinationSecurityRole = workflowControl.getWorkflowDestinationSecurityRole(workflowDestinationPartyType,
                                securityRole, entityPermission);

                        if(workflowDestinationSecurityRole == null) {
                            var workflowDestinationDetail = workflowDestination.getLastDetail();
                            var workflowStepDetail = workflowDestinationDetail.getWorkflowStep().getLastDetail();
                            var securityRoleDetail = securityRole.getLastDetail();

                            handleExecutionError(UnknownWorkflowDestinationSecurityRoleException.class, eea, ExecutionErrors.UnknownWorkflowDestinationSecurityRole.name(),
                                    workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName(),
                                    workflowStepDetail.getWorkflowStepName(), workflowDestinationDetail.getWorkflowDestinationName(),
                                    partyType.getPartyTypeName(), securityRoleDetail.getSecurityRole().getLastDetail().getSecurityRoleName());
                        }
                    }
                }
            } else {
                var workflowStepDetail = workflowDestination.getLastDetail().getWorkflowStep().getLastDetail();

                handleExecutionError(WorkflowMissingSecurityRoleGroupException.class, eea, ExecutionErrors.WorkflowMissingSecurityRoleGroup.name(),
                        workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName());
            }
        }

        return workflowDestinationSecurityRole;
    }

    public WorkflowDestinationSecurityRole getWorkflowDestinationSecurityRoleByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowStepName, final String workflowDestinationName, final String partyTypeName, final String securityRoleName) {
        return getWorkflowDestinationSecurityRoleByName(eea, workflowName, workflowStepName, workflowDestinationName, partyTypeName,
                securityRoleName, EntityPermission.READ_ONLY);
    }


    public WorkflowDestinationSecurityRole getWorkflowDestinationSecurityRoleByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowStepName, final String workflowDestinationName, final String partyTypeName, final String securityRoleName) {
        return getWorkflowDestinationSecurityRoleByName(eea, workflowName, workflowStepName, workflowDestinationName, partyTypeName,
                securityRoleName, EntityPermission.READ_WRITE);
    }
    
    public WorkflowDestinationSelector getWorkflowDestinationSelectorByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowStepName, final String workflowDestinationName, final String selectorName,
            final EntityPermission entityPermission) {
        var workflowDestination = getWorkflowDestinationByName(eea, workflowName, workflowStepName, workflowDestinationName);
        WorkflowDestinationSelector workflowDestinationSelector = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var selectorType = workflowDestination.getLastDetail().getWorkflowStep().getLastDetail().getWorkflow().getLastDetail().getSelectorType();

            if(selectorType != null) {
                var selector = SelectorLogic.getInstance().getSelectorByName(eea, selectorType, selectorName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var workflowControl = (WorkflowControl) Session.getModelController(WorkflowControl.class);

                    workflowDestinationSelector = workflowControl.getWorkflowDestinationSelector(workflowDestination,
                            selector, entityPermission);

                    if(workflowDestinationSelector == null) {
                        var workflowDestinationDetail = workflowDestination.getLastDetail();
                        var workflowStepDetail = workflowDestinationDetail.getWorkflowStep().getLastDetail();
                        var selectorDetail = selector.getLastDetail();

                        handleExecutionError(UnknownWorkflowDestinationSelectorException.class, eea, ExecutionErrors.UnknownWorkflowDestinationSelector.name(),
                                workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName(),
                                workflowStepDetail.getWorkflowStepName(), workflowDestinationDetail.getWorkflowDestinationName(),
                                selectorDetail.getSelector().getLastDetail().getSelectorName());
                    }
                }
            } else {
                var workflowStepDetail = workflowDestination.getLastDetail().getWorkflowStep().getLastDetail();

                handleExecutionError(WorkflowMissingSelectorTypeException.class, eea, ExecutionErrors.WorkflowMissingSelectorType.name(),
                        workflowStepDetail.getWorkflow().getLastDetail().getWorkflowName());
            }
        }

        return workflowDestinationSelector;
    }

    public WorkflowDestinationSelector getWorkflowDestinationSelectorByName(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowStepName, final String workflowDestinationName, final String selectorName) {
        return getWorkflowDestinationSelectorByName(eea, workflowName, workflowStepName, workflowDestinationName, selectorName,
                EntityPermission.READ_ONLY);
    }


    public WorkflowDestinationSelector getWorkflowDestinationSelectorByNameForUpdate(final ExecutionErrorAccumulator eea, final String workflowName,
            final String workflowStepName, final String workflowDestinationName, final String selectorName) {
        return getWorkflowDestinationSelectorByName(eea, workflowName, workflowStepName, workflowDestinationName, selectorName,
                EntityPermission.READ_WRITE);
    }

    public WorkflowStep getDestinationWorkflowStep(final ExecutionErrorAccumulator eea, final String destinationWorkflowName,
            final String destinationWorkflowStepName) {
        return WorkflowStepLogic.getInstance().getWorkflowStepByName(
                UnknownDestinationWorkflowNameException.class, ExecutionErrors.UnknownDestinationWorkflowName,
                UnknownDestinationWorkflowStepNameException.class, ExecutionErrors.UnknownDestinationWorkflowStepName,
                eea, destinationWorkflowName, destinationWorkflowStepName);
    }

    public WorkflowDestinationStep getWorkflowDestinationStep(final ExecutionErrorAccumulator eea,
            WorkflowDestination workflowDestination, WorkflowStep destinationWorkflowStep) {
        var workflowControl = (WorkflowControl) Session.getModelController(WorkflowControl.class);
        var workflowDestinationStep = workflowControl.getWorkflowDestinationStep(workflowDestination, destinationWorkflowStep);

        if(workflowDestinationStep == null) {
            var workflowDestinationDetail = workflowDestination.getLastDetail();
            var destinationWorkflowStepDetail = destinationWorkflowStep.getLastDetail();

            handleExecutionError(UnknownWorkflowDestinationStepException.class, eea, ExecutionErrors.UnknownWorkflowDestinationStep.name(),
                    workflowDestinationDetail.getWorkflowStep().getLastDetail().getWorkflow().getLastDetail().getWorkflowName(),
                    workflowDestinationDetail.getWorkflowStep().getLastDetail().getWorkflowStepName(),
                    workflowDestinationDetail.getWorkflowDestinationName(),
                    destinationWorkflowStepDetail.getWorkflow().getLastDetail().getWorkflowName(),
                    destinationWorkflowStepDetail.getWorkflowStepName());
        }

        return workflowDestinationStep;
    }

    public WorkflowDestinationStep getWorkflowDestinationStepByName(final ExecutionErrorAccumulator eea,
            final String workflowName, final String workflowStepName, final String workflowDestinationName,
            final String destinationWorkflowName, final String destinationWorkflowStepName) {
        var workflowDestination = getWorkflowDestinationByName(eea, workflowName, workflowStepName, workflowDestinationName);
        var destinationWorkflowStep = getDestinationWorkflowStep(eea, destinationWorkflowName, destinationWorkflowStepName);
        WorkflowDestinationStep workflowDestinationStep = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            workflowDestinationStep = getWorkflowDestinationStep(eea, workflowDestination, destinationWorkflowStep);
        }

        return workflowDestinationStep;
    }

}
