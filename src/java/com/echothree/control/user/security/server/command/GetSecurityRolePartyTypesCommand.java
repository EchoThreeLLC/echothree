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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.remote.form.GetSecurityRolePartyTypesForm;
import com.echothree.control.user.security.remote.result.GetSecurityRolePartyTypesResult;
import com.echothree.control.user.security.remote.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetSecurityRolePartyTypesCommand
        extends BaseSimpleCommand<GetSecurityRolePartyTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRolePartyType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetSecurityRolePartyTypesCommand */
    public GetSecurityRolePartyTypesCommand(UserVisitPK userVisitPK, GetSecurityRolePartyTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
        GetSecurityRolePartyTypesResult result = SecurityResultFactory.getGetSecurityRolePartyTypesResult();
        String securityRoleGroupName = form.getSecurityRoleGroupName();
        SecurityRoleGroup securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);
        
        if(securityRoleGroup != null) {
            String securityRoleName = form.getSecurityRoleName();
            SecurityRole securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);
            
            if(securityRole != null) {
                result.setSecurityRole(securityControl.getSecurityRoleTransfer(getUserVisit(), securityRole));
                result.setSecurityRolePartyTypes(securityControl.getSecurityRolePartyTypeTransfersBySecurityRole(getUserVisit(), securityRole));
            } else {
                addExecutionError(ExecutionErrors.UnknownSecurityRoleName.name(), securityRoleGroupName, securityRoleName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
        }
        
        return result;
    }
    
}
