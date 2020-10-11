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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.form.GetQueuedLetterForm;
import com.echothree.control.user.letter.common.result.GetQueuedLetterResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.letter.server.entity.QueuedLetter;
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

public class GetQueuedLetterCommand
        extends BaseSimpleCommand<GetQueuedLetterForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.QueuedLetter.name(), SecurityRoles.Review.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainInstanceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("QueuedLetterSequence", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetQueuedLetterCommand */
    public GetQueuedLetterCommand(UserVisitPK userVisitPK, GetQueuedLetterForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        GetQueuedLetterResult result = LetterResultFactory.getGetQueuedLetterResult();
        String chainInstanceName = form.getChainInstanceName();
        ChainInstance chainInstance = chainControl.getChainInstanceByName(chainInstanceName);
        
        if(chainInstance != null) {
            var letterControl = (LetterControl)Session.getModelController(LetterControl.class);
            String strQueuedLetterSequence = form.getQueuedLetterSequence();
            Integer queuedLetterSequence = Integer.valueOf(strQueuedLetterSequence);
            QueuedLetter queuedLetter = letterControl.getQueuedLetter(chainInstance, queuedLetterSequence);

            if(queuedLetter != null) {
                result.setQueuedLetter(letterControl.getQueuedLetterTransfer(getUserVisit(), queuedLetter));
            } else {
                addExecutionError(ExecutionErrors.UnknownQueuedLetter.name(), chainInstanceName, strQueuedLetterSequence);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainInstanceName.name(), chainInstanceName);
        }
        
        return result;
    }
    
}
