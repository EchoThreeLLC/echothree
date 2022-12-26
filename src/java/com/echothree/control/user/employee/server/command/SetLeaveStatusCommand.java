// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.control.user.employee.common.form.SetLeaveStatusForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.result.SetLeaveStatusResult;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSetStatusCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetLeaveStatusCommand
        extends BaseSetStatusCommand<SetLeaveStatusForm, SetLeaveStatusResult, Leave, Leave> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.LeaveStatus.name(), SecurityRoles.Choices.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LeaveStatusChoice", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetLeaveStatusCommand */
    public SetLeaveStatusCommand(UserVisitPK userVisitPK, SetLeaveStatusForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    public SetLeaveStatusResult getResult() {
        return EmployeeResultFactory.getSetLeaveStatusResult();
    }

    @Override
    public Leave getEntity() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        String leaveName = form.getLeaveName();
        Leave leave = employeeControl.getLeaveByNameForUpdate(leaveName);

        if(leave == null) {
            addExecutionError(ExecutionErrors.UnknownLeave.name(), leaveName);
        }

        return leave;
    }

    @Override
    public Leave getLockEntity(Leave leave) {
        return leave;
    }

    @Override
    public void doUpdate(Leave leave) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        String leaveStatusChoice = form.getLeaveStatusChoice();

        employeeControl.setLeaveStatus(this, leave, leaveStatusChoice, getPartyPK());
    }

}
