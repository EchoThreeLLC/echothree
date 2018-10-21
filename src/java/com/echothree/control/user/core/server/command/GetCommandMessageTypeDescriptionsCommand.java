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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.remote.form.GetCommandMessageTypeDescriptionsForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.GetCommandMessageTypeDescriptionsResult;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCommandMessageTypeDescriptionsCommand
        extends BaseSimpleCommand<GetCommandMessageTypeDescriptionsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CommandMessageType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandMessageTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetCommandMessageTypeDescriptionsCommand */
    public GetCommandMessageTypeDescriptionsCommand(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CoreControl coreControl = getCoreControl();
        GetCommandMessageTypeDescriptionsResult result = CoreResultFactory.getGetCommandMessageTypeDescriptionsResult();
        String commandMessageTypeName = form.getCommandMessageTypeName();
        CommandMessageType commandMessageType = coreControl.getCommandMessageTypeByName(commandMessageTypeName);
        
        if(commandMessageType != null) {
            result.setCommandMessageType(coreControl.getCommandMessageTypeTransfer(getUserVisit(), commandMessageType));
            result.setCommandMessageTypeDescriptions(coreControl.getCommandMessageTypeDescriptionTransfers(getUserVisit(), commandMessageType));
        } else {
            addExecutionError(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName);
        }
        
        return result;
    }
    
}
