// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.contactlist.common.form.GetCustomerTypeContactListsForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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

public class GetCustomerTypeContactListsCommand
        extends BaseSimpleCommand<GetCustomerTypeContactListsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.CustomerTypeContactList.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetCustomerTypeContactListsCommand */
    public GetCustomerTypeContactListsCommand(UserVisitPK userVisitPK, GetCustomerTypeContactListsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = ContactListResultFactory.getGetCustomerTypeContactListsResult();
        var customerTypeName = form.getCustomerTypeName();
        var contactListName = form.getContactListName();
        var parameterCount = (customerTypeName != null? 1: 0) + (contactListName != null? 1: 0);
        
        if(parameterCount == 1) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            var userVisit = getUserVisit();
            
            if(customerTypeName != null) {
                var customerControl = Session.getModelController(CustomerControl.class);
                var customerType = customerControl.getCustomerTypeByName(customerTypeName);
                
                if(customerType != null) {
                    result.setCustomerType(customerControl.getCustomerTypeTransfer(userVisit, customerType));
                    result.setCustomerTypeContactLists(contactListControl.getCustomerTypeContactListTransfersByCustomerType(userVisit, customerType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
                }
            } else if(contactListName != null) {
                var contactList = contactListControl.getContactListByName(contactListName);
                
                if(contactList != null) {
                    result.setContactList(contactListControl.getContactListTransfer(userVisit, contactList));
                    result.setCustomerTypeContactLists(contactListControl.getCustomerTypeContactListTransfersByContactList(userVisit, contactList));
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
