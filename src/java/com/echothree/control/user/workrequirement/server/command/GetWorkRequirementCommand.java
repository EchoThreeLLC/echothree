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

package com.echothree.control.user.workrequirement.server.command;

import com.echothree.control.user.workrequirement.common.form.GetWorkRequirementForm;
import com.echothree.control.user.workrequirement.common.result.GetWorkRequirementResult;
import com.echothree.control.user.workrequirement.common.result.WorkRequirementResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirement;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWorkRequirementCommand
        extends BaseSimpleCommand<GetWorkRequirementForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WorkRequirementName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetWorkRequirementCommand */
    public GetWorkRequirementCommand(UserVisitPK userVisitPK, GetWorkRequirementForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetWorkRequirementResult result = WorkRequirementResultFactory.getGetWorkRequirementResult();
        WorkRequirementControl workRequirementControl = (WorkRequirementControl)Session.getModelController(WorkRequirementControl.class);
        String workRequirementName = form.getWorkRequirementName();
        WorkRequirement workRequirement = workRequirementControl.getWorkRequirementByName(workRequirementName);
        
        if(workRequirement != null) {
            result.setWorkRequirement(workRequirementControl.getWorkRequirementTransfer(getUserVisit(), workRequirement));
            
            sendEventUsingNames(workRequirement.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkRequirementName.name(), workRequirementName);
        }
        
        return result;
    }
    
}
