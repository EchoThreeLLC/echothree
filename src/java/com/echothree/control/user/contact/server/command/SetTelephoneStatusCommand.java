// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.contact.common.form.SetTelephoneStatusForm;
import com.echothree.model.control.contact.common.ContactConstants;
import com.echothree.model.control.contact.server.ContactControl;
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

public class SetTelephoneStatusCommand
        extends BaseSimpleCommand<SetTelephoneStatusForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TelephoneStatusChoice", FieldType.ENTITY_NAME, true, null, null)));
    }

    /** Creates a new instance of SetTelephoneStatusCommand */
    public SetTelephoneStatusCommand(UserVisitPK userVisitPK, SetTelephoneStatusForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
        String contactMechanismName = form.getContactMechanismName();
        ContactMechanism contactMechanism = contactControl.getContactMechanismByName(contactMechanismName);

        if(contactMechanism != null) {
            String contactMechanismTypeName = contactMechanism.getLastDetail().getContactMechanismType().getContactMechanismTypeName();

            if(contactMechanismTypeName.equals(ContactConstants.ContactMechanismType_TELECOM_ADDRESS)) {
                String contactMechanismStatusChoice = form.getTelephoneStatusChoice();

                contactControl.setTelephoneStatus(this, contactMechanism, contactMechanismStatusChoice, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.InvalidContactMechanismType.name(), contactMechanismName, contactMechanismTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
        }

        return null;
    }

}
