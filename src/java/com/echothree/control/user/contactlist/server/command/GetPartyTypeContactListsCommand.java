// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.contactlist.remote.form.GetPartyTypeContactListsForm;
import com.echothree.control.user.contactlist.remote.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.remote.result.GetPartyTypeContactListsResult;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPartyTypeContactListsCommand
        extends BaseSimpleCommand<GetPartyTypeContactListsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.PartyTypeContactList.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyTypeContactListsCommand */
    public GetPartyTypeContactListsCommand(UserVisitPK userVisitPK, GetPartyTypeContactListsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetPartyTypeContactListsResult result = ContactListResultFactory.getGetPartyTypeContactListsResult();
        String partyTypeName = form.getPartyTypeName();
        String contactListName = form.getContactListName();
        int parameterCount = (partyTypeName != null? 1: 0) + (contactListName != null? 1: 0);
        
        if(parameterCount == 1) {
            ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
            UserVisit userVisit = getUserVisit();
            
            if(partyTypeName != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);
                
                if(partyType != null) {
                    result.setPartyType(partyControl.getPartyTypeTransfer(userVisit, partyType));
                    result.setPartyTypeContactLists(contactListControl.getPartyTypeContactListTransfersByPartyType(userVisit, partyType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
                }
            } else if(contactListName != null) {
                ContactList contactList = contactListControl.getContactListByName(contactListName);
                
                if(contactList != null) {
                    result.setContactList(contactListControl.getContactListTransfer(userVisit, contactList));
                    result.setPartyTypeContactLists(contactListControl.getPartyTypeContactListTransfersByContactList(userVisit, contactList));
                } else {
                    addExecutionError(ExecutionErrors.UnknownContactListName.name(), contactListName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
