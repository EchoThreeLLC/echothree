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

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.order.server.trigger.OrderTrigger;
import com.echothree.model.control.printer.server.trigger.PrinterGroupJobTrigger;
import com.echothree.model.control.training.server.trigger.PartyTrainingClassTrigger;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.trigger.EntityTypeTrigger;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowTrigger;
import com.echothree.model.data.workflow.server.factory.WorkflowTriggerFactory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class WorkflowTriggerLogic {
    
    private WorkflowTriggerLogic() {
        super();
    }
    
    private static class WorkflowTriggerLogicHolder {
        static WorkflowTriggerLogic instance = new WorkflowTriggerLogic();
    }
    
    public static WorkflowTriggerLogic getInstance() {
        return WorkflowTriggerLogicHolder.instance;
    }
    
    // TODO: configure using a property file.
    private EntityTypeTrigger locateTrigger(final ExecutionErrorAccumulator eea, final String componentVendorName, final String entityTypeName) {
        EntityTypeTrigger result = null;
        
        if(componentVendorName.equals(ComponentVendors.ECHOTHREE.name())) {
            if(entityTypeName.equals(EntityTypes.PartyTrainingClass.name())) {
                result = new PartyTrainingClassTrigger();
            } else if(entityTypeName.equals(EntityTypes.PrinterGroupJob.name())) {
                result = new PrinterGroupJobTrigger();
            } else if(entityTypeName.equals(EntityTypes.Order.name())) {
                result = new OrderTrigger();
            }
        }
        
        if(result == null) {
            eea.addExecutionError(ExecutionErrors.UnknownGeneralTrigger.name(), componentVendorName, entityTypeName);
        }
        
        return result;
    }
    
    private void processWorkflowTrigger(final Session session, final ExecutionErrorAccumulator eea, final WorkflowTrigger workflowTrigger, final PartyPK triggeredBy) {
        WorkflowEntityStatus workflowEntityStatus = workflowTrigger.getWorkflowEntityStatusForUpdate();
        EntityInstance entityInstance = workflowEntityStatus.getEntityInstance();
        EntityTypeDetail entityTypeDetail = entityInstance.getEntityType().getLastDetail();
        String entityTypeName = entityTypeDetail.getEntityTypeName();
        String componentVendorName = entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName();
        EntityTypeTrigger entityTypeTrigger = locateTrigger(eea, componentVendorName, entityTypeName);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            entityTypeTrigger.handleTrigger(session, eea, workflowEntityStatus, triggeredBy);
        }
    }
    
    public void processWorkflowTriggers(final Session session, final ExecutionErrorAccumulator eea, final PartyPK triggeredBy) {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        long workflowTriggersProcessed = 0;
        long remainingTime = (long) 1 * 60 * 1000; // 1 minute
        
        for(WorkflowTrigger workflowTrigger : workflowControl.getWorkflowTriggersByTriggerTime(session.START_TIME_LONG)) {
            Boolean errorsOccurred = workflowTrigger.getErrorsOccurred();

            if(errorsOccurred == null || errorsOccurred == false) {
                long startTime = System.currentTimeMillis();

                workflowTrigger = WorkflowTriggerFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, workflowTrigger.getPrimaryKey());
                processWorkflowTrigger(session, eea, workflowTrigger, triggeredBy);

                if(eea.hasExecutionErrors()) {
                    workflowTrigger.setErrorsOccurred(Boolean.TRUE);
                    break;
                } else {
                    workflowTriggersProcessed++;
                    remainingTime -= System.currentTimeMillis() - startTime;
                    if(remainingTime < 0) {
                        break;
                    }
                }
            }
        }
    }

}
