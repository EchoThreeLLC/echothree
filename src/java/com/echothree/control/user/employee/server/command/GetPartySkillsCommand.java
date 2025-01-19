// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.form.GetPartySkillsForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
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

public class GetPartySkillsCommand
        extends BaseSimpleCommand<GetPartySkillsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetPartySkillsCommand */
    public GetPartySkillsCommand(UserVisitPK userVisitPK, GetPartySkillsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = EmployeeResultFactory.getGetPartySkillsResult();
        var partyName = form.getPartyName();
        var skillTypeName = form.getSkillTypeName();
        var parameterCount = (partyName == null ? 0 : 1) + (skillTypeName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var employeeControl = Session.getModelController(EmployeeControl.class);
            
            if(partyName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    result.setParty(partyControl.getPartyTransfer(getUserVisit(), party));
                    result.setPartySkills(employeeControl.getPartySkillTransfersByParty(getUserVisit(), party));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(skillTypeName != null) {
                var skillType = employeeControl.getSkillTypeByName(skillTypeName);
                
                if(skillType != null) {
                    result.setSkillType(employeeControl.getSkillTypeTransfer(getUserVisit(), skillType));
                    result.setPartySkills(employeeControl.getPartySkillTransfersBySkillType(getUserVisit(), skillType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownSkillTypeName.name(), skillTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
