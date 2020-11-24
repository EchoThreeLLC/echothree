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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreatePartyAliasTypeForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePartyAliasTypeCommand
        extends BaseSimpleCommand<CreatePartyAliasTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyAliasType.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreatePartyAliasTypeCommand */
    public CreatePartyAliasTypeCommand(UserVisitPK userVisitPK, CreatePartyAliasTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        String partyTypeName = form.getPartyTypeName();
        PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType != null) {
            if(partyType.getAllowPartyAliases()) {
                String partyAliasTypeName = form.getPartyAliasTypeName();
                PartyAliasType partyAliasType = partyControl.getPartyAliasTypeByName(partyType, partyAliasTypeName);

                if(partyAliasType == null) {
                    PartyPK createdBy = getPartyPK();
                    String validationPattern = form.getValidationPattern();
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();

                    partyAliasType = partyControl.createPartyAliasType(partyType, partyAliasTypeName, validationPattern, isDefault, sortOrder, createdBy);

                    if(description != null) {
                        partyControl.createPartyAliasTypeDescription(partyAliasType, getPreferredLanguage(), description, createdBy);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicatePartyAliasTypeName.name(), partyTypeName, partyAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }

        return null;
    }
    
}
