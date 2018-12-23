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

package com.echothree.control.user.track.server.command;

import com.echothree.control.user.track.common.edit.TrackEdit;
import com.echothree.control.user.track.common.edit.TrackEditFactory;
import com.echothree.control.user.track.common.form.EditTrackForm;
import com.echothree.control.user.track.common.result.EditTrackResult;
import com.echothree.control.user.track.common.result.TrackResultFactory;
import com.echothree.control.user.track.common.spec.TrackSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.track.server.TrackControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.track.server.entity.Track;
import com.echothree.model.data.track.server.entity.TrackDescription;
import com.echothree.model.data.track.server.entity.TrackDetail;
import com.echothree.model.data.track.server.value.TrackDescriptionValue;
import com.echothree.model.data.track.server.value.TrackDetailValue;
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

public class EditTrackCommand
        extends BaseAbstractEditCommand<TrackSpec, TrackEdit, EditTrackResult, Track, Track> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Track.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrackName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Value", FieldType.STRING, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditTrackCommand */
    public EditTrackCommand(UserVisitPK userVisitPK, EditTrackForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTrackResult getResult() {
        return TrackResultFactory.getEditTrackResult();
    }

    @Override
    public TrackEdit getEdit() {
        return TrackEditFactory.getTrackEdit();
    }

    @Override
    public Track getEntity(EditTrackResult result) {
        TrackControl trackControl = (TrackControl)Session.getModelController(TrackControl.class);
        Track track;
        String trackName = spec.getTrackName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            track = trackControl.getTrackByName(trackName);
        } else { // EditMode.UPDATE
            track = trackControl.getTrackByNameForUpdate(trackName);
        }

        if(track == null) {
            addExecutionError(ExecutionErrors.UnknownTrackName.name(), trackName);
        }

        return track;
    }

    @Override
    public Track getLockEntity(Track track) {
        return track;
    }

    @Override
    public void fillInResult(EditTrackResult result, Track track) {
        TrackControl trackControl = (TrackControl)Session.getModelController(TrackControl.class);

        result.setTrack(trackControl.getTrackTransfer(getUserVisit(), track));
    }

    @Override
    public void doLock(TrackEdit edit, Track track) {
        TrackControl trackControl = (TrackControl)Session.getModelController(TrackControl.class);
        TrackDescription trackDescription = trackControl.getTrackDescription(track, getPreferredLanguage());
        TrackDetail trackDetail = track.getLastDetail();

        edit.setValue(trackDetail.getValue());
        edit.setIsDefault(trackDetail.getIsDefault().toString());
        edit.setSortOrder(trackDetail.getSortOrder().toString());

        if(trackDescription != null) {
            edit.setDescription(trackDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Track track) {
        TrackControl trackControl = (TrackControl)Session.getModelController(TrackControl.class);
        String value = edit.getValue();
        Track duplicateTrack = trackControl.getTrackByValue(value);

        if(duplicateTrack != null && !track.equals(duplicateTrack)) {
            addExecutionError(ExecutionErrors.DuplicateTrackValue.name(), value);
        }
    }

    @Override
    public void doUpdate(Track track) {
        TrackControl trackControl = (TrackControl)Session.getModelController(TrackControl.class);
        PartyPK partyPK = getPartyPK();
        TrackDetailValue trackDetailValue = trackControl.getTrackDetailValueForUpdate(track);
        TrackDescription trackDescription = trackControl.getTrackDescriptionForUpdate(track, getPreferredLanguage());
        String description = edit.getDescription();

        trackDetailValue.setValue(edit.getValue());
        trackDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        trackDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        trackControl.updateTrackFromValue(trackDetailValue, partyPK);

        if(trackDescription == null && description != null) {
            trackControl.createTrackDescription(track, getPreferredLanguage(), description, partyPK);
        } else {
            if(trackDescription != null && description == null) {
                trackControl.deleteTrackDescription(trackDescription, partyPK);
            } else {
                if(trackDescription != null && description != null) {
                    TrackDescriptionValue trackDescriptionValue = trackControl.getTrackDescriptionValue(trackDescription);

                    trackDescriptionValue.setDescription(description);
                    trackControl.updateTrackDescriptionFromValue(trackDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
