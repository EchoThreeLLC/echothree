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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.remote.form.GetPartyResponsibilitiesForm;
import com.echothree.control.user.employee.remote.result.EmployeeResultFactory;
import com.echothree.control.user.employee.remote.result.GetPartyResponsibilitiesResult;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPartyResponsibilitiesCommand
        extends BaseSimpleCommand<GetPartyResponsibilitiesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetPartyResponsibilitiesCommand */
    public GetPartyResponsibilitiesCommand(UserVisitPK userVisitPK, GetPartyResponsibilitiesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetPartyResponsibilitiesResult result = EmployeeResultFactory.getGetPartyResponsibilitiesResult();
        String partyName = form.getPartyName();
        String responsibilityTypeName = form.getResponsibilityTypeName();
        int parameterCount = (partyName == null? 0: 1) + (responsibilityTypeName == null? 0: 1);
        
        if(parameterCount == 1) {
            EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
            
            if(partyName != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                Party party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    result.setParty(partyControl.getPartyTransfer(getUserVisit(), party));
                    result.setPartyResponsibilities(employeeControl.getPartyResponsibilityTransfersByParty(getUserVisit(), party));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(responsibilityTypeName != null) {
                ResponsibilityType responsibilityType = employeeControl.getResponsibilityTypeByName(responsibilityTypeName);
                
                if(responsibilityType != null) {
                    result.setResponsibilityType(employeeControl.getResponsibilityTypeTransfer(getUserVisit(), responsibilityType));
                    result.setPartyResponsibilities(employeeControl.getPartyResponsibilityTransfersByResponsibilityType(getUserVisit(), responsibilityType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownResponsibilityTypeName.name(), responsibilityTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
