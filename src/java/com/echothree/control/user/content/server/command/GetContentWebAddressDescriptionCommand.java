// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentWebAddressDescriptionForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.GetContentWebAddressDescriptionResult;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.content.server.entity.ContentWebAddressDescription;
import com.echothree.model.data.party.server.entity.Language;
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

public class GetContentWebAddressDescriptionCommand
        extends BaseSimpleCommand<GetContentWebAddressDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentWebAddress.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentWebAddressDescriptionCommand */
    public GetContentWebAddressDescriptionCommand(UserVisitPK userVisitPK, GetContentWebAddressDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetContentWebAddressDescriptionResult result = ContentResultFactory.getGetContentWebAddressDescriptionResult();
        var contentControl = Session.getModelController(ContentControl.class);
        String contentWebAddressName = form.getContentWebAddressName();
        ContentWebAddress contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);
        
        if(contentWebAddress != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                ContentWebAddressDescription contentWebAddressDescription = contentControl.getContentWebAddressDescription(contentWebAddress, language);

                if(contentWebAddressDescription != null) {
                    result.setContentWebAddressDescription(contentControl.getContentWebAddressDescriptionTransfer(getUserVisit(), contentWebAddressDescription));
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentWebAddressDescription.name(), contentWebAddressName, contentWebAddressName, contentWebAddressName,
                            languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentWebAddressName.name(), contentWebAddressName);
        }
        
        return result;
    }
    
}
