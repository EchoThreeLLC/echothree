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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.form.DeletePartyResponsibilityForm;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.employee.server.entity.PartyResponsibility;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.party.server.entity.Party;
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

public class DeletePartyResponsibilityCommand
        extends BaseSimpleCommand<DeletePartyResponsibilityForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeletePartyResponsibilityCommand */
    public DeletePartyResponsibilityCommand(UserVisitPK userVisitPK, DeletePartyResponsibilityForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
            String responsibilityTypeName = form.getResponsibilityTypeName();
            ResponsibilityType responsibilityType = employeeControl.getResponsibilityTypeByName(responsibilityTypeName);
            
            if(responsibilityType != null) {
                PartyResponsibility partyResponsibility = employeeControl.getPartyResponsibilityForUpdate(party, responsibilityType);
                
                if(partyResponsibility != null) {
                    employeeControl.deletePartyResponsibility(partyResponsibility, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyResponsibility.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownResponsibilityTypeName.name(), responsibilityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return null;
    }
    
}
