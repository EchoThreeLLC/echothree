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

package com.echothree.control.user.tag.server.command;


import com.echothree.control.user.tag.common.form.GetTagChoicesForm;
import com.echothree.control.user.tag.common.result.GetTagChoicesResult;
import com.echothree.control.user.tag.common.result.TagResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tag.server.TagControl;
import com.echothree.model.data.tag.server.entity.TagScope;
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

public class GetTagChoicesCommand
        extends BaseSimpleCommand<GetTagChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Tag.name(), SecurityRoles.Choices.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TagScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultTagChoice", FieldType.TAG, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetTagChoicesCommand */
    public GetTagChoicesCommand(UserVisitPK userVisitPK, GetTagChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var tagControl = (TagControl)Session.getModelController(TagControl.class);
        GetTagChoicesResult result = TagResultFactory.getGetTagChoicesResult();
        String tagScopeName = form.getTagScopeName();
        TagScope tagScope = tagControl.getTagScopeByName(tagScopeName);
        
        if(tagScope != null) {
            String defaultTagChoice = form.getDefaultTagChoice();
            boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            
            result.setTagChoices(tagControl.getTagChoices(defaultTagChoice, getPreferredLanguage(), allowNullChoice, tagScope));
        } else {
            addExecutionError(ExecutionErrors.UnknownTagScopeName.name(), tagScopeName);
        }
        
        return result;
    }
    
}
