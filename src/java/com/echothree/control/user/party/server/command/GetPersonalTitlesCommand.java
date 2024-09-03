// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.party.common.form.GetPersonalTitlesForm;
import com.echothree.control.user.party.common.result.GetPersonalTitlesResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetPersonalTitlesCommand
        extends BaseMultipleEntitiesCommand<PersonalTitle, GetPersonalTitlesForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetPersonalTitlesCommand */
    public GetPersonalTitlesCommand(UserVisitPK userVisitPK, GetPersonalTitlesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Collection<PersonalTitle> getEntities() {
        var partyControl = Session.getModelController(PartyControl.class);
        
        return partyControl.getPersonalTitles();
    }
    
    @Override
    protected BaseResult getResult(Collection<PersonalTitle> entities) {
        var result = PartyResultFactory.getGetPersonalTitlesResult();
        var partyControl = Session.getModelController(PartyControl.class);
        
        result.setPersonalTitles(partyControl.getPersonalTitleTransfers(getUserVisit(), entities));
        
        return result;
    }
    
}
