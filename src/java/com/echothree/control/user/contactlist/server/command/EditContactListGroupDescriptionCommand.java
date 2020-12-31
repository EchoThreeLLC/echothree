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

import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.edit.ContactListGroupDescriptionEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListGroupDescriptionForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditContactListGroupDescriptionResult;
import com.echothree.control.user.contactlist.common.spec.ContactListGroupDescriptionSpec;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.ContactListGroupDescription;
import com.echothree.model.data.contactlist.server.value.ContactListGroupDescriptionValue;
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

public class EditContactListGroupDescriptionCommand
        extends BaseAbstractEditCommand<ContactListGroupDescriptionSpec, ContactListGroupDescriptionEdit, EditContactListGroupDescriptionResult, ContactListGroupDescription, ContactListGroup> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactListGroup.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditContactListGroupDescriptionCommand */
    public EditContactListGroupDescriptionCommand(UserVisitPK userVisitPK, EditContactListGroupDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditContactListGroupDescriptionResult getResult() {
        return ContactListResultFactory.getEditContactListGroupDescriptionResult();
    }

    @Override
    public ContactListGroupDescriptionEdit getEdit() {
        return ContactListEditFactory.getContactListGroupDescriptionEdit();
    }

    @Override
    public ContactListGroupDescription getEntity(EditContactListGroupDescriptionResult result) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListGroupDescription contactListGroupDescription = null;
        String contactListGroupName = spec.getContactListGroupName();
        ContactListGroup contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);

        if(contactListGroup != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contactListGroupDescription = contactListControl.getContactListGroupDescription(contactListGroup, language);
                } else { // EditMode.UPDATE
                    contactListGroupDescription = contactListControl.getContactListGroupDescriptionForUpdate(contactListGroup, language);
                }

                if(contactListGroupDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownContactListGroupDescription.name(), contactListGroupName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContactListGroupName.name(), contactListGroupName);
        }

        return contactListGroupDescription;
    }

    @Override
    public ContactListGroup getLockEntity(ContactListGroupDescription contactListGroupDescription) {
        return contactListGroupDescription.getContactListGroup();
    }

    @Override
    public void fillInResult(EditContactListGroupDescriptionResult result, ContactListGroupDescription contactListGroupDescription) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setContactListGroupDescription(contactListControl.getContactListGroupDescriptionTransfer(getUserVisit(), contactListGroupDescription));
    }

    @Override
    public void doLock(ContactListGroupDescriptionEdit edit, ContactListGroupDescription contactListGroupDescription) {
        edit.setDescription(contactListGroupDescription.getDescription());
    }

    @Override
    public void doUpdate(ContactListGroupDescription contactListGroupDescription) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListGroupDescriptionValue contactListGroupDescriptionValue = contactListControl.getContactListGroupDescriptionValue(contactListGroupDescription);

        contactListGroupDescriptionValue.setDescription(edit.getDescription());

        contactListControl.updateContactListGroupDescriptionFromValue(contactListGroupDescriptionValue, getPartyPK());
    }

}
