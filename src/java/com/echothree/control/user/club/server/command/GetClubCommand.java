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

package com.echothree.control.user.club.server.command;

import com.echothree.control.user.club.common.form.GetClubForm;
import com.echothree.control.user.club.common.result.ClubResultFactory;
import com.echothree.control.user.club.common.result.GetClubResult;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetClubCommand
        extends BaseSimpleCommand<GetClubForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ClubName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetClubCommand */
    public GetClubCommand(UserVisitPK userVisitPK, GetClubForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var clubControl = Session.getModelController(ClubControl.class);
        GetClubResult result = ClubResultFactory.getGetClubResult();
        String clubName = form.getClubName();
        Club club = clubControl.getClubByName(clubName);
        
        if(club != null) {
            result.setClub(clubControl.getClubTransfer(getUserVisit(), club));
            
            sendEventUsingNames(club.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownClubName.name(), clubName);
        }
        
        return result;
    }
    
}
