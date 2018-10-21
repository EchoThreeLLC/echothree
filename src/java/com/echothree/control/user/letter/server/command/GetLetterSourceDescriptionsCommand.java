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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.remote.form.GetLetterSourceDescriptionsForm;
import com.echothree.control.user.letter.remote.result.GetLetterSourceDescriptionsResult;
import com.echothree.control.user.letter.remote.result.LetterResultFactory;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.letter.server.entity.LetterSource;
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

public class GetLetterSourceDescriptionsCommand
        extends BaseSimpleCommand<GetLetterSourceDescriptionsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LetterSource.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LetterSourceName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetLetterSourceDescriptionsCommand */
    public GetLetterSourceDescriptionsCommand(UserVisitPK userVisitPK, GetLetterSourceDescriptionsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        LetterControl letterControl = (LetterControl)Session.getModelController(LetterControl.class);
        GetLetterSourceDescriptionsResult result = LetterResultFactory.getGetLetterSourceDescriptionsResult();
        String letterSourceName = form.getLetterSourceName();
        LetterSource letterSource = letterControl.getLetterSourceByName(letterSourceName);
        
        if(letterSource != null) {
            result.setLetterSource(letterControl.getLetterSourceTransfer(getUserVisit(), letterSource));
            result.setLetterSourceDescriptions(letterControl.getLetterSourceDescriptionTransfersByLetterSource(getUserVisit(), letterSource));
        } else {
            addExecutionError(ExecutionErrors.UnknownLetterSourceName.name(), letterSourceName);
        }
        
        return result;
    }
    
}
