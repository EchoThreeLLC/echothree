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

package com.echothree.model.control.training.server.trigger;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic.PreparedPartyTrainingClass;
import com.echothree.model.control.training.common.training.PartyTrainingClassStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.trigger.BaseTrigger;
import com.echothree.model.control.workflow.server.trigger.EntityTypeTrigger;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateUse;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.PartyTrainingClassDetail;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class PartyTrainingClassTrigger
        extends BaseTrigger
        implements EntityTypeTrigger {

    private void expireCurrentPartyTrainingClass(final Session session, final ExecutionErrorAccumulator eea, final WorkflowEntityStatus workflowEntityStatus,
            final PartyTrainingClass partyTrainingClass, final PartyPK triggeredBy) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        PartyTrainingClassDetail partyTrainingClassDetail = partyTrainingClass.getLastDetail();
        Party party = partyTrainingClassDetail.getParty();
        TrainingClass trainingClass = partyTrainingClassDetail.getTrainingClass();

        workflowControl.transitionEntityInWorkflowUsingNames(null, workflowEntityStatus, PartyTrainingClassStatusConstants.WorkflowDestination_PASSED_TO_EXPIRED,
                trainingClass.getLastDetail().getExpiredRetentionTime(), triggeredBy);
        coreControl.sendEventUsingNames(partyTrainingClassDetail.getParty().getPrimaryKey(), EventTypes.TOUCH.name(), partyTrainingClass.getPrimaryKey(), EventTypes.MODIFY.name(), triggeredBy);

        // If it is required, then consider creating a new PartyTrainingClass that's assigned to them.
        if(checkTrainingRequired(party, trainingClass)) {
            // Check to see if the TrainingClass is in one of the listed statuses for the Party. If it is, then we do not create a
            // new instance of a PartyTrainingClass for it.
            Set<PartyTrainingClass> partyTrainingClasses = trainingControl.getPartyTrainingClassesByStatuses(party, trainingClass,
                    PartyTrainingClassStatusConstants.WorkflowStep_ASSIGNED, PartyTrainingClassStatusConstants.WorkflowStep_TRAINING,
                    PartyTrainingClassStatusConstants.WorkflowStep_PASSED);

            if(partyTrainingClasses.isEmpty()) {
                PartyTrainingClassLogic partyTrainingClassLogic = PartyTrainingClassLogic.getInstance();
                PreparedPartyTrainingClass preparedPartyTrainingClass = partyTrainingClassLogic.preparePartyTrainingClass(eea, party, trainingClass, null, null);

                if(!eea.hasExecutionErrors()) {
                    partyTrainingClassLogic.createPartyTrainingClass(session, preparedPartyTrainingClass, triggeredBy);
                }
            }
        }
    }
    
    private boolean checkTrainingRequired(final Party party, final TrainingClass trainingClass) {
        boolean trainingRequired = false;

        // Check AlwaysReassignOnExpiration on the TrainingClass, and if that isn't set, check to see if the Party has a
        // PartySecurityRoleTemplateUse. If the PartySecurityRoleTemplate lists this TrainingClass, then it will be required.
        if(trainingClass.getLastDetail().getAlwaysReassignOnExpiration()) {
            trainingRequired = true;
        } else {
            var securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
            PartySecurityRoleTemplateUse partySecurityRoleTemplateUse = securityControl.getPartySecurityRoleTemplateUse(party);
            if(partySecurityRoleTemplateUse != null) {
                if(securityControl.getPartySecurityRoleTemplateTrainingClass(partySecurityRoleTemplateUse.getPartySecurityRoleTemplate(), trainingClass) != null) {
                    trainingRequired = true;
                }
            }
        }
        
        return trainingRequired;
    }
    
    @Override
    public void handleTrigger(final Session session, final ExecutionErrorAccumulator eea, final WorkflowEntityStatus workflowEntityStatus, final PartyPK triggeredBy) {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        PartyTrainingClass partyTrainingClass = trainingControl.convertEntityInstanceToPartyTrainingClassForUpdate(getEntityInstance(workflowEntityStatus));
        String workflowStepName = getWorkflowStepName(workflowEntityStatus);
        
        if(workflowStepName.equals(PartyTrainingClassStatusConstants.WorkflowStep_PASSED)) {
            expireCurrentPartyTrainingClass(session, eea, workflowEntityStatus, partyTrainingClass, triggeredBy);
        } else if(workflowStepName.equals(PartyTrainingClassStatusConstants.WorkflowStep_EXPIRED)) {
            trainingControl.deletePartyTrainingClass(partyTrainingClass, triggeredBy);
        }
    }

}
