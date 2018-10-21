// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.track.server.logic;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.track.common.exception.UnknownTrackNameException;
import com.echothree.model.control.track.common.exception.UnknownTrackStatusChoiceException;
import com.echothree.model.control.track.common.exception.UnknownTrackValueException;
import com.echothree.model.control.track.server.TrackControl;
import com.echothree.model.control.track.common.workflow.TrackStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.track.server.entity.Track;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.Map;
import java.util.Set;

public class TrackLogic
        extends BaseLogic {

    private TrackLogic() {
        super();
    }

    private static class TrackLogicHolder {
        static TrackLogic instance = new TrackLogic();
    }

    public static TrackLogic getInstance() {
        return TrackLogicHolder.instance;
    }
    
    public Track getTrackByName(final ExecutionErrorAccumulator eea, final String trackName) {
        TrackControl trackControl = (TrackControl)Session.getModelController(TrackControl.class);
        Track track = trackControl.getTrackByName(trackName);

        if(track == null) {
            handleExecutionError(UnknownTrackNameException.class, eea, ExecutionErrors.UnknownTrackName.name(), trackName);
        }

        return track;
    }
    
    public Track getTrackByValue(final ExecutionErrorAccumulator eea, final String trackValue) {
        TrackControl trackControl = (TrackControl)Session.getModelController(TrackControl.class);
        Track track = trackControl.getTrackByValue(trackValue);

        if(track == null) {
            handleExecutionError(UnknownTrackValueException.class, eea, ExecutionErrors.UnknownTrackValue.name(), trackValue);
        }

        return track;
    }
    
    public void setTrackStatus(final Session session, ExecutionErrorAccumulator eea, Track track, String trackStatusChoice, PartyPK modifiedBy) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        WorkflowLogic workflowLogic = WorkflowLogic.getInstance();
        Workflow workflow = workflowLogic.getWorkflowByName(eea, TrackStatusConstants.Workflow_TRACK_STATUS);
        EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(track.getPrimaryKey());
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        WorkflowDestination workflowDestination = trackStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), trackStatusChoice);

        if(workflowDestination != null || trackStatusChoice == null) {
            String currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            Map<String, Set<String>> map = workflowLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(TrackStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowLogic.workflowDestinationMapContainsStep(map, TrackStatusConstants.Workflow_TRACK_STATUS, TrackStatusConstants.WorkflowStep_INACTIVE)) {
                    // Nothing at this time.
                }
            } else if(currentWorkflowStepName.equals(TrackStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowLogic.workflowDestinationMapContainsStep(map, TrackStatusConstants.Workflow_TRACK_STATUS, TrackStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownTrackStatusChoiceException.class, eea, ExecutionErrors.UnknownTrackStatusChoice.name(), trackStatusChoice);
        }
    }
    
}
