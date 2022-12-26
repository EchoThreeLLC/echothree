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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.GetWebAddressStatusChoicesForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.GetWebAddressStatusChoicesResult;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWebAddressStatusChoicesCommand
        extends BaseSimpleCommand<GetWebAddressStatusChoicesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultWebAddressStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)));
    }

    /** Creates a new instance of GetWebAddressStatusChoicesCommand */
    public GetWebAddressStatusChoicesCommand(UserVisitPK userVisitPK, GetWebAddressStatusChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var contactControl = Session.getModelController(ContactControl.class);
        GetWebAddressStatusChoicesResult result = ContactResultFactory.getGetWebAddressStatusChoicesResult();
        String contactMechanismName = form.getContactMechanismName();
        ContactMechanism contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);

        if(contactMechanism != null) {
            String contactMechanismTypeName = contactMechanism.getLastDetail().getContactMechanismType().getContactMechanismTypeName();

            if(contactMechanismTypeName.equals(ContactMechanismTypes.WEB_ADDRESS.name())) {
                String defaultWebAddressStatusChoice = form.getDefaultWebAddressStatusChoice();
                boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                result.setWebAddressStatusChoices(contactControl.getWebAddressStatusChoices(defaultWebAddressStatusChoice, getPreferredLanguage(), allowNullChoice, contactMechanism,
                        getPartyPK()));
            } else {
                addExecutionError(ExecutionErrors.InvalidContactMechanismType.name(), contactMechanismName, contactMechanismTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
        }

        return result;
    }

}
