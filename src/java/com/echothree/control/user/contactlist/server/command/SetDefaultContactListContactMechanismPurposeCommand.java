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

package com.echothree.control.user.contactlist.server.command;

import com.echothree.control.user.contactlist.common.form.SetDefaultContactListContactMechanismPurposeForm;
import com.echothree.model.control.contact.server.logic.ContactMechanismPurposeLogic;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.value.ContactListContactMechanismPurposeDetailValue;
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

public class SetDefaultContactListContactMechanismPurposeCommand
        extends BaseSimpleCommand<SetDefaultContactListContactMechanismPurposeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.ContactListContactMechanismPurpose.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismPurposeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultContactListContactMechanismPurposeCommand */
    public SetDefaultContactListContactMechanismPurposeCommand(UserVisitPK userVisitPK, SetDefaultContactListContactMechanismPurposeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String contactListName = form.getContactListName();
        ContactList contactList = ContactListLogic.getInstance().getContactListByName(this, contactListName);
        
        if(!hasExecutionErrors()) {
            String contactMechanismPurposeName = form.getContactMechanismPurposeName();
            ContactMechanismPurpose contactMechanismPurpose = ContactMechanismPurposeLogic.getInstance().getContactMechanismPurposeByName(this, contactMechanismPurposeName);
            
            if(!hasExecutionErrors()) {
                var contactListControl = Session.getModelController(ContactListControl.class);
                ContactListContactMechanismPurposeDetailValue contactListContactMechanismPurposeDetailValue = contactListControl.getContactListContactMechanismPurposeDetailValueForUpdate(contactList, contactMechanismPurpose);
                
                if(contactListContactMechanismPurposeDetailValue != null) {
                    contactListContactMechanismPurposeDetailValue.setIsDefault(Boolean.TRUE);
                    contactListControl.updateContactListContactMechanismPurposeFromValue(contactListContactMechanismPurposeDetailValue, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownContactListContactMechanismPurpose.name(), contactListName, contactMechanismPurposeName);
                }
            }
        }
        
        return null;
    }
    
}
