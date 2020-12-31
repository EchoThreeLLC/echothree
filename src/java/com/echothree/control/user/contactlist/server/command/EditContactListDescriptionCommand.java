// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.contactlist.common.edit.ContactListDescriptionEdit;
import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.form.EditContactListDescriptionForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditContactListDescriptionResult;
import com.echothree.control.user.contactlist.common.spec.ContactListDescriptionSpec;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListDescription;
import com.echothree.model.data.contactlist.server.value.ContactListDescriptionValue;
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

public class EditContactListDescriptionCommand
        extends BaseAbstractEditCommand<ContactListDescriptionSpec, ContactListDescriptionEdit, EditContactListDescriptionResult, ContactListDescription, ContactList> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditContactListDescriptionCommand */
    public EditContactListDescriptionCommand(UserVisitPK userVisitPK, EditContactListDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditContactListDescriptionResult getResult() {
        return ContactListResultFactory.getEditContactListDescriptionResult();
    }

    @Override
    public ContactListDescriptionEdit getEdit() {
        return ContactListEditFactory.getContactListDescriptionEdit();
    }

    @Override
    public ContactListDescription getEntity(EditContactListDescriptionResult result) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListDescription contactListDescription = null;
        String contactListName = spec.getContactListName();
        ContactList contactList = contactListControl.getContactListByName(contactListName);

        if(contactList != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contactListDescription = contactListControl.getContactListDescription(contactList, language);
                } else { // EditMode.UPDATE
                    contactListDescription = contactListControl.getContactListDescriptionForUpdate(contactList, language);
                }

                if(contactListDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownContactListDescription.name(), contactListName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContactListName.name(), contactListName);
        }

        return contactListDescription;
    }

    @Override
    public ContactList getLockEntity(ContactListDescription contactListDescription) {
        return contactListDescription.getContactList();
    }

    @Override
    public void fillInResult(EditContactListDescriptionResult result, ContactListDescription contactListDescription) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setContactListDescription(contactListControl.getContactListDescriptionTransfer(getUserVisit(), contactListDescription));
    }

    @Override
    public void doLock(ContactListDescriptionEdit edit, ContactListDescription contactListDescription) {
        edit.setDescription(contactListDescription.getDescription());
    }

    @Override
    public void doUpdate(ContactListDescription contactListDescription) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListDescriptionValue contactListDescriptionValue = contactListControl.getContactListDescriptionValue(contactListDescription);

        contactListDescriptionValue.setDescription(edit.getDescription());

        contactListControl.updateContactListDescriptionFromValue(contactListDescriptionValue, getPartyPK());
    }

}
