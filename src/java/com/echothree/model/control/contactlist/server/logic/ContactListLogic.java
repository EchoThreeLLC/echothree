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

package com.echothree.model.control.contactlist.server.logic;

import com.echothree.model.control.contactlist.common.exception.UnknownContactListContactMechanismPurposeException;
import com.echothree.model.control.contactlist.common.exception.UnknownContactListNameException;
import com.echothree.model.control.contactlist.common.workflow.PartyContactListStatusConstants;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactList;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactListGroup;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactList;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactListGroup;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.HashSet;

public class ContactListLogic
    extends BaseLogic {
    
    private ContactListLogic() {
        super();
    }
    
    private static class ContactListLogicHolder {
        static ContactListLogic instance = new ContactListLogic();
    }
    
    public static ContactListLogic getInstance() {
        return ContactListLogicHolder.instance;
    }
    
    public ContactList getContactListByName(final ExecutionErrorAccumulator eea, final String contactListName) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        var contactList = contactListControl.getContactListByName(contactListName);

        if(contactList == null) {
            handleExecutionError(UnknownContactListNameException.class, eea, ExecutionErrors.UnknownContactListName.name(), contactListName);
        }

        return contactList;
    }
    
    public ContactListContactMechanismPurpose getContactListContactMechanismPurpose(final ExecutionErrorAccumulator eea, final ContactList contactList,
            final ContactMechanismPurpose contactMechanismPurpose) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        var contactListContactMechanismPurpose = contactListControl.getContactListContactMechanismPurpose(contactList, contactMechanismPurpose);
        
        if(contactListContactMechanismPurpose == null) {
            handleExecutionError(UnknownContactListContactMechanismPurposeException.class, eea, ExecutionErrors.UnknownContactListContactMechanismPurpose.name(),
                    contactList.getLastDetail().getContactListName(), contactMechanismPurpose.getContactMechanismPurposeName());
        }
        
        return contactListContactMechanismPurpose;
    }

    public boolean hasContactListAccess(final Party executingParty, final ContactList contactList) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        var partyType = executingParty.getLastDetail().getPartyType();
        var partyTypeName = partyType.getPartyTypeName();
        var contactListGroup = contactList.getLastDetail().getContactListGroup();

        // Employees may do anything they want with lists.
        var hasAccess = partyTypeName.equals(PartyTypes.EMPLOYEE.name());

        // If the Contact List Group that the Contact List is in has been explicitly given access to the Party Type,
        // then allow access.
        if(!hasAccess) {
            hasAccess = contactListControl.partyTypeContactListGroupExists(partyType, contactListGroup);
        }

        // If access hasn't been granted, allow access if the Party Type has been explicitly given access to the
        // Contact List, or if the Contact List has no further restrictions.
        if(!hasAccess) {
            hasAccess = contactListControl.partyTypeContactListExists(partyType, contactList)
                    || contactListControl.countPartyTypeContactListsByContactList(contactList) == 0;
        }

        // Customers have some special checks based on Customer Type, if access still has not yet been granted.
        if(!hasAccess && partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
            var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
            var customerType = customerControl.getCustomer(executingParty).getCustomerType();

            // If the Contact List is in has been explicitly given access to the Party Type, then allow access.
            hasAccess = contactListControl.customerTypeContactListGroupExists(customerType, contactListGroup);

            // If access hasn't been granted, allow access if the Customer Type has been explicitly given access to the
            // Contact List, or if the Contact List has no further restrictions.
            if(!hasAccess) {
                hasAccess = contactListControl.customerTypeContactListExists(customerType, contactList)
                        || contactListControl.countCustomerTypeContactListsByContactList(contactList) == 0;
            }
        }

        return hasAccess;
    }
    
    public PartyContactList addContactListToParty(final ExecutionErrorAccumulator eea, final Party party, final ContactList contactList,
            final ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose, final BasePK createdBy) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        var partyContactList = contactListControl.createPartyContactList(party, contactList, preferredContactListContactMechanismPurpose, createdBy);
        var entityInstance = coreControl.getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
        var workflowEntrance = contactList.getLastDetail().getDefaultPartyContactListStatus();
        var workflowEntranceName = workflowEntrance.getLastDetail().getWorkflowEntranceName();

        // Add the PartyContactList to the DefaultPartyContactListStatus for the Contact List.
        workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);

        // Based on the DefaultPartyContactListStatus, add the PartyContactList to a Chain.
        if(workflowEntranceName.equals(PartyContactListStatusConstants.WorkflowEntrance_NEW_AWAITING_VERIFICATION)) {
            ContactListChainLogic.getInstance().createContactListConfirmationChainInstance(eea, party, partyContactList, createdBy);
        } else if(workflowEntranceName.equals(PartyContactListStatusConstants.WorkflowEntrance_NEW_ACTIVE)) {
            ContactListChainLogic.getInstance().createContactListSubscribeChainInstance(eea, party, partyContactList, createdBy);
        }

        return partyContactList;
    }
    
    public void removeContactListFromParty(final ExecutionErrorAccumulator eea, final PartyContactList partyContactList, final BasePK deletedBy) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        var entityInstance = coreControl.getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS, entityInstance);
        var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();

        // If they were an active subscriber, add them to an appropriate Chain when unsubscribing. If they haven't
        // confirmed, we'll skip any follow-up.
        if(workflowStepName.equals(PartyContactListStatusConstants.WorkflowStep_ACTIVE)) {
            ContactListChainLogic.getInstance().createContactListUnsubscribeChainInstance(eea, partyContactList.getLastDetail().getParty(), partyContactList, deletedBy);
        }
        
        contactListControl.deletePartyContactList(partyContactList, deletedBy);
        workflowControl.deleteWorkflowEntityStatus(workflowEntityStatus, deletedBy);
    }
    
    public void setupInitialContactLists(final ExecutionErrorAccumulator eea, final Party party, final BasePK createdBy) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        var partyType = party.getLastDetail().getPartyType();
        var contactLists = new HashSet<ContactList>();

        contactListControl.getPartyTypeContactListsByPartyType(partyType).stream()
                .filter(PartyTypeContactList::getAddWhenCreated)
                .map(PartyTypeContactList::getContactList)
                .forEach(contactLists::add);

        contactListControl.getPartyTypeContactListGroupsByPartyType(partyType).stream()
                .filter(PartyTypeContactListGroup::getAddWhenCreated)
                .forEach((partyTypeContactListGroup) -> contactLists.addAll(contactListControl.getContactListsByContactListGroup(partyTypeContactListGroup.getContactListGroup()))
        );

        // If the party is a CUSTOMER, check to see if their CustomerType is tied to any specific ContactLists
        // or ContactListGroups.
        if(PartyLogic.getInstance().isPartyType(party, PartyTypes.CUSTOMER.name())) {
            var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
            var customerType = customerControl.getCustomer(party).getCustomerType();

            contactListControl.getCustomerTypeContactListsByCustomerType(customerType).stream()
                    .filter(CustomerTypeContactList::getAddWhenCreated)
                    .map(CustomerTypeContactList::getContactList)
                    .forEach(contactLists::add);

            contactListControl.getCustomerTypeContactListGroupsByCustomerType(customerType).stream()
                    .filter(CustomerTypeContactListGroup::getAddWhenCreated)
                    .forEach((customerTypeContactListGroup) -> contactLists.addAll(contactListControl.getContactListsByContactListGroup(customerTypeContactListGroup.getContactListGroup()))
            );
        }

        if(!hasExecutionErrors(eea)) {
            contactLists.forEach((contactList) -> {
                addContactListToParty(eea, party, contactList, null, createdBy);
            });
        }
    }
    
}
