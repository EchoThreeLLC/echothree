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

package com.echothree.control.user.contactlist.server.command;

import com.echothree.control.user.contactlist.common.form.GetPartyContactListsForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.GetPartyContactListsResult;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
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

public class GetPartyContactListsCommand
        extends BaseSimpleCommand<GetPartyContactListsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyContactList.name(), SecurityRoles.List.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyContactListsCommand */
    public GetPartyContactListsCommand(UserVisitPK userVisitPK, GetPartyContactListsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetPartyContactListsResult result = ContactListResultFactory.getGetPartyContactListsResult();
        String partyName = form.getPartyName();
        String contactListName = form.getContactListName();
        var parameterCount = (partyName != null ? 1 : 0) + (contactListName != null ? 1 : 0);
        
        if(parameterCount == 1) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            UserVisit userVisit = getUserVisit();
            
            if(partyName != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                Party party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    result.setParty(partyControl.getPartyTransfer(userVisit, party));
                    result.setPartyContactLists(contactListControl.getPartyContactListTransfersByParty(userVisit, party));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(contactListName != null) {
                ContactList contactList = contactListControl.getContactListByName(contactListName);
                
                if(contactList != null) {
                    result.setContactList(ContactListLogic.getInstance().getContactListTransfer(userVisit, contactList));
                    result.setPartyContactLists(contactListControl.getPartyContactListTransfersByContactList(userVisit, contactList));
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
