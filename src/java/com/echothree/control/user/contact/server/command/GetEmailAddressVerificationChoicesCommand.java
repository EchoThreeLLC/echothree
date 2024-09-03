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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.GetEmailAddressVerificationChoicesForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
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

public class GetEmailAddressVerificationChoicesCommand
        extends BaseSimpleCommand<GetEmailAddressVerificationChoicesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultEmailAddressVerificationChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)));
    }

    /** Creates a new instance of GetEmailAddressVerificationChoicesCommand */
    public GetEmailAddressVerificationChoicesCommand(UserVisitPK userVisitPK, GetEmailAddressVerificationChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var contactControl = Session.getModelController(ContactControl.class);
        var result = ContactResultFactory.getGetEmailAddressVerificationChoicesResult();
        var contactMechanismName = form.getContactMechanismName();
        var contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);

        if(contactMechanism != null) {
            var contactMechanismTypeName = contactMechanism.getLastDetail().getContactMechanismType().getContactMechanismTypeName();

            if(contactMechanismTypeName.equals(ContactMechanismTypes.EMAIL_ADDRESS.name())) {
                var defaultEmailAddressVerificationChoice = form.getDefaultEmailAddressVerificationChoice();
                var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                result.setEmailAddressVerificationChoices(contactControl.getEmailAddressVerificationChoices(defaultEmailAddressVerificationChoice, getPreferredLanguage(), allowNullChoice, contactMechanism,
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
