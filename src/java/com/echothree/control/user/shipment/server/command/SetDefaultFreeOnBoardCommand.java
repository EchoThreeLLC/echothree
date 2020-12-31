// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.shipment.server.command;

import com.echothree.control.user.shipment.common.form.SetDefaultFreeOnBoardForm;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.shipment.server.value.FreeOnBoardDetailValue;
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

public class SetDefaultFreeOnBoardCommand
        extends BaseSimpleCommand<SetDefaultFreeOnBoardForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.FreeOnBoard.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FreeOnBoardName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultFreeOnBoardCommand */
    public SetDefaultFreeOnBoardCommand(UserVisitPK userVisitPK, SetDefaultFreeOnBoardForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);
        String freeOnBoardName = form.getFreeOnBoardName();
        FreeOnBoardDetailValue freeOnBoardDetailValue = freeOnBoardControl.getFreeOnBoardDetailValueByNameForUpdate(freeOnBoardName);
        
        if(freeOnBoardDetailValue != null) {
            freeOnBoardDetailValue.setIsDefault(Boolean.TRUE);
            freeOnBoardControl.updateFreeOnBoardFromValue(freeOnBoardDetailValue, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownFreeOnBoardName.name(), freeOnBoardName);
        }
        
        return null;
    }
    
}
