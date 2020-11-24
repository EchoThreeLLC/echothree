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

package com.echothree.control.user.contactlist.server.command;

import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.edit.ContactListTypeDescriptionEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListTypeDescriptionForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditContactListTypeDescriptionResult;
import com.echothree.control.user.contactlist.common.spec.ContactListTypeDescriptionSpec;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListType;
import com.echothree.model.data.contactlist.server.entity.ContactListTypeDescription;
import com.echothree.model.data.contactlist.server.value.ContactListTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditContactListTypeDescriptionCommand
        extends BaseAbstractEditCommand<ContactListTypeDescriptionSpec, ContactListTypeDescriptionEdit, EditContactListTypeDescriptionResult, ContactListTypeDescription, ContactListType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactListType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditContactListTypeDescriptionCommand */
    public EditContactListTypeDescriptionCommand(UserVisitPK userVisitPK, EditContactListTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditContactListTypeDescriptionResult getResult() {
        return ContactListResultFactory.getEditContactListTypeDescriptionResult();
    }

    @Override
    public ContactListTypeDescriptionEdit getEdit() {
        return ContactListEditFactory.getContactListTypeDescriptionEdit();
    }

    @Override
    public ContactListTypeDescription getEntity(EditContactListTypeDescriptionResult result) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListTypeDescription contactListTypeDescription = null;
        String contactListTypeName = spec.getContactListTypeName();
        ContactListType contactListType = contactListControl.getContactListTypeByName(contactListTypeName);

        if(contactListType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contactListTypeDescription = contactListControl.getContactListTypeDescription(contactListType, language);
                } else { // EditMode.UPDATE
                    contactListTypeDescription = contactListControl.getContactListTypeDescriptionForUpdate(contactListType, language);
                }

                if(contactListTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownContactListTypeDescription.name(), contactListTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContactListTypeName.name(), contactListTypeName);
        }

        return contactListTypeDescription;
    }

    @Override
    public ContactListType getLockEntity(ContactListTypeDescription contactListTypeDescription) {
        return contactListTypeDescription.getContactListType();
    }

    @Override
    public void fillInResult(EditContactListTypeDescriptionResult result, ContactListTypeDescription contactListTypeDescription) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setContactListTypeDescription(contactListControl.getContactListTypeDescriptionTransfer(getUserVisit(), contactListTypeDescription));
    }

    @Override
    public void doLock(ContactListTypeDescriptionEdit edit, ContactListTypeDescription contactListTypeDescription) {
        edit.setDescription(contactListTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ContactListTypeDescription contactListTypeDescription) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListTypeDescriptionValue contactListTypeDescriptionValue = contactListControl.getContactListTypeDescriptionValue(contactListTypeDescription);

        contactListTypeDescriptionValue.setDescription(edit.getDescription());

        contactListControl.updateContactListTypeDescriptionFromValue(contactListTypeDescriptionValue, getPartyPK());
    }

}
