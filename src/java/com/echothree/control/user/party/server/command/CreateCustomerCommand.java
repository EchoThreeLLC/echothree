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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.remote.form.CreateCustomerForm;
import com.echothree.control.user.party.remote.result.CreateCustomerResult;
import com.echothree.control.user.party.remote.result.PartyResultFactory;
import com.echothree.model.control.accounting.common.AccountingConstants;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.cancellationpolicy.common.CancellationPolicyConstants;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.contact.common.ContactMechanismPurposes;
import com.echothree.model.control.contact.server.logic.ContactEmailAddressLogic;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.party.server.logic.PartyChainLogic;
import com.echothree.model.control.returnpolicy.common.ReturnPolicyConstants;
import com.echothree.model.control.returnpolicy.server.ReturnPolicyControl;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.control.customer.common.workflow.CustomerCreditStatusConstants;
import com.echothree.model.control.customer.common.workflow.CustomerStatusConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationPolicy;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.Customer;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeDetail;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnPolicy;
import com.echothree.model.data.term.server.entity.CustomerTypeCreditLimit;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;

public class CreateCustomerCommand
        extends BaseSimpleCommand<CreateCustomerForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Customer.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReturnPolicyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ArGlAccountName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InitialOfferName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InitialUseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InitialSourceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
                new FieldDefinition("Name", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("PreferredLanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredCurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PreferredJavaTimeZoneName", FieldType.TIME_ZONE_NAME, false, null, null),
                new FieldDefinition("PreferredDateTimeFormatName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EmailAddress", FieldType.EMAIL_ADDRESS, false, null, null),
                new FieldDefinition("AllowSolicitation", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("CustomerStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CustomerCreditStatusChoice", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCustomerCommand */
    public CreateCustomerCommand(UserVisitPK userVisitPK, CreateCustomerForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CreateCustomerResult result = PartyResultFactory.getCreateCustomerResult();
        CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        Customer customer = null;
        String customerTypeName = form.getCustomerTypeName();
        CustomerType customerType = customerTypeName == null ? customerControl.getDefaultCustomerType() : customerControl.getCustomerTypeByName(customerTypeName);

        if(customerType != null) {
            String cancellationPolicyName = form.getCancellationPolicyName();
            CancellationPolicy cancellationPolicy = null;

            if(cancellationPolicyName != null) {
                CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
                CancellationKind returnKind = cancellationPolicyControl.getCancellationKindByName(CancellationPolicyConstants.CancellationKind_CUSTOMER_CANCELLATION);

                cancellationPolicy = cancellationPolicyControl.getCancellationPolicyByName(returnKind, cancellationPolicyName);
            }

            if(cancellationPolicyName == null || cancellationPolicy != null) {
                String returnPolicyName = form.getReturnPolicyName();
                ReturnPolicy returnPolicy = null;

                if(returnPolicyName != null) {
                    ReturnPolicyControl returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
                    ReturnKind returnKind = returnPolicyControl.getReturnKindByName(ReturnPolicyConstants.ReturnKind_CUSTOMER_RETURN);

                    returnPolicy = returnPolicyControl.getReturnPolicyByName(returnKind, returnPolicyName);
                }

                if(returnPolicyName == null || returnPolicy != null) {
                    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                    String arGlAccountName = form.getArGlAccountName();
                    GlAccount arGlAccount = arGlAccountName == null ? null : accountingControl.getGlAccountByName(arGlAccountName);

                    if(arGlAccountName == null || arGlAccount != null) {
                        String glAccountCategoryName = arGlAccount == null ? null
                                : arGlAccount.getLastDetail().getGlAccountCategory().getLastDetail().getGlAccountCategoryName();

                        if(glAccountCategoryName == null || glAccountCategoryName.equals(AccountingConstants.GlAccountCategory_ACCOUNTS_RECEIVABLE)) {
                            TermControl termControl = (TermControl)Session.getModelController(TermControl.class);
                            CustomerTypeDetail customerTypeDetail = customerType.getLastDetail();
                            Term term = customerTypeDetail.getDefaultTerm();

                            if(term == null) {
                                term = termControl.getDefaultTerm();
                            }

                            if(term != null) {
                                OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
                                String initialOfferName = form.getInitialOfferName();
                                String initialUseName = form.getInitialUseName();
                                String initialSourceName = form.getInitialSourceName();
                                OfferUse initialOfferUse = null;
                                boolean invalidInitialOfferOrSourceSpecification = false;

                                if(initialOfferName != null && initialUseName != null && initialSourceName == null) {
                                    Offer initialOffer = offerControl.getOfferByName(initialOfferName);

                                    if(initialOffer != null) {
                                        Use initialUse = offerControl.getUseByName(initialUseName);

                                        if(initialUse != null) {
                                            initialOfferUse = offerControl.getOfferUse(initialOffer, initialUse);

                                            if(initialOfferUse == null) {
                                                addExecutionError(ExecutionErrors.UnknownInitialOfferUse.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownInitialUseName.name(), initialUseName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownInitialOfferName.name(), initialOfferName);
                                    }
                                } else {
                                    if(initialOfferName == null && initialUseName == null && initialSourceName != null) {
                                        Source source = offerControl.getSourceByName(initialSourceName);

                                        if(source != null) {
                                            initialOfferUse = source.getLastDetail().getOfferUse();
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownInitialSourceName.name(), initialSourceName);
                                        }
                                    } else {
                                        initialOfferUse = getUserVisit().getOfferUse();

                                        if(initialOfferUse == null) {
                                            // If all three parameters are null, then try to get the default Source and use its OfferUse.
                                            Source source = offerControl.getDefaultSource();

                                            if(source != null) {
                                                initialOfferUse = source.getLastDetail().getOfferUse();
                                            } else {
                                                addExecutionError(ExecutionErrors.InvalidInitialOfferOrSourceSpecification.name());
                                                invalidInitialOfferOrSourceSpecification = true;
                                            }
                                        }
                                    }
                                }

                                if(initialOfferUse != null) {
                                    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                                    String preferredLanguageIsoName = form.getPreferredLanguageIsoName();
                                    Language preferredLanguage = preferredLanguageIsoName == null ? null : partyControl.getLanguageByIsoName(preferredLanguageIsoName);

                                    if(preferredLanguageIsoName == null || (preferredLanguage != null)) {
                                        String preferredJavaTimeZoneName = form.getPreferredJavaTimeZoneName();
                                        TimeZone preferredTimeZone = preferredJavaTimeZoneName == null ? null : partyControl.getTimeZoneByJavaName(preferredJavaTimeZoneName);

                                        if(preferredJavaTimeZoneName == null || (preferredTimeZone != null)) {
                                            String preferredDateTimeFormatName = form.getPreferredDateTimeFormatName();
                                            DateTimeFormat preferredDateTimeFormat = preferredDateTimeFormatName == null ? null : partyControl.getDateTimeFormatByName(preferredDateTimeFormatName);

                                            if(preferredDateTimeFormatName == null || (preferredDateTimeFormat != null)) {
                                                String preferredCurrencyIsoName = form.getPreferredCurrencyIsoName();
                                                Currency preferredCurrency;

                                                if(preferredCurrencyIsoName == null) {
                                                    preferredCurrency = null;
                                                } else {
                                                    preferredCurrency = accountingControl.getCurrencyByIsoName(preferredCurrencyIsoName);
                                                }

                                                if(preferredCurrencyIsoName == null || (preferredCurrency != null)) {
                                                    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                                                    Soundex soundex = new Soundex();
                                                    PartyType partyType = partyControl.getPartyTypeByName(PartyConstants.PartyType_CUSTOMER);
                                                    BasePK createdBy = getPartyPK();
                                                    String personalTitleId = form.getPersonalTitleId();
                                                    PersonalTitle personalTitle = personalTitleId == null ? null : partyControl.convertPersonalTitleIdToEntity(personalTitleId,
                                                            EntityPermission.READ_ONLY);
                                                    String firstName = form.getFirstName();
                                                    String middleName = form.getMiddleName();
                                                    String lastName = form.getLastName();
                                                    String nameSuffixId = form.getNameSuffixId();
                                                    NameSuffix nameSuffix = nameSuffixId == null ? null : partyControl.convertNameSuffixIdToEntity(nameSuffixId,
                                                            EntityPermission.READ_ONLY);
                                                    String name = form.getName();
                                                    String emailAddress = form.getEmailAddress();
                                                    Boolean allowSolicitation = Boolean.valueOf(form.getAllowSolicitation());

                                                    String firstNameSdx;
                                                    try {
                                                        firstNameSdx = firstName == null ? null : soundex.encode(firstName);
                                                    } catch(IllegalArgumentException iae) {
                                                        firstNameSdx = null;
                                                    }

                                                    String middleNameSdx;
                                                    try {
                                                        middleNameSdx = middleName == null ? null : soundex.encode(middleName);
                                                    } catch(IllegalArgumentException iae) {
                                                        middleNameSdx = null;
                                                    }

                                                    String lastNameSdx;
                                                    try {
                                                        lastNameSdx = lastName == null ? null : soundex.encode(lastName);
                                                    } catch(IllegalArgumentException iae) {
                                                        lastNameSdx = null;
                                                    }

                                                    Party party = partyControl.createParty(null, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, createdBy);

                                                    if(createdBy == null) {
                                                        createdBy = party.getPrimaryKey();
                                                    }
                                                    if(personalTitle != null || firstName != null || middleName != null || lastName != null || nameSuffix != null) {
                                                        partyControl.createPerson(party, personalTitle, firstName, firstNameSdx, middleName, middleNameSdx,
                                                                lastName, lastNameSdx, nameSuffix, createdBy);
                                                    }

                                                    if(name != null) {
                                                        partyControl.createPartyGroup(party, name, createdBy);
                                                    }

                                                    customer = customerControl.createCustomer(party, customerType, initialOfferUse, cancellationPolicy,
                                                            returnPolicy, arGlAccount, customerTypeDetail.getDefaultHoldUntilComplete(),
                                                            customerTypeDetail.getDefaultAllowBackorders(), customerTypeDetail.getDefaultAllowSubstitutions(),
                                                            customerTypeDetail.getDefaultAllowCombiningShipments(), customerTypeDetail.getDefaultRequireReference(),
                                                            customerTypeDetail.getDefaultAllowReferenceDuplicates(), customerTypeDetail.getDefaultReferenceValidationPattern(),
                                                            createdBy);

                                                    if(emailAddress != null) {
                                                        ContactEmailAddressLogic.getInstance().createContactEmailAddress(party,
                                                                emailAddress, allowSolicitation, null,
                                                                ContactMechanismPurposes.PRIMARY_EMAIL.name(), createdBy);
                                                    }

                                                    termControl.createPartyTerm(party, term, customerTypeDetail.getDefaultTaxable(), createdBy);

                                                    for(CustomerTypeCreditLimit customerTypeCreditLimit : termControl.getCustomerTypeCreditLimitsByCustomerType(customerType)) {
                                                        Currency currency = customerTypeCreditLimit.getCurrency();
                                                        Long creditLimit = customerTypeCreditLimit.getCreditLimit();
                                                        Long potentialCreditLimit = customerTypeCreditLimit.getPotentialCreditLimit();

                                                        termControl.createPartyCreditLimit(party, currency, creditLimit, potentialCreditLimit, createdBy);
                                                    }

                                                    // TODO: error checking for unknown customerStatusChoice
                                                    String customerStatusChoice = form.getCustomerStatusChoice();
                                                    Workflow customerStatusWorkflow = workflowControl.getWorkflowByName(CustomerStatusConstants.Workflow_CUSTOMER_STATUS);
                                                    WorkflowEntrance customerStatusWorkflowEntrance = customerStatusChoice == null ? customerTypeDetail.getDefaultCustomerStatus()
                                                            : workflowControl.getWorkflowEntranceByName(customerStatusWorkflow,
                                                            customerStatusChoice);

                                                    if(customerStatusWorkflowEntrance == null) {
                                                        customerStatusWorkflowEntrance = workflowControl.getDefaultWorkflowEntrance(customerStatusWorkflow);
                                                    }

                                                    // TODO: error checking for unknown customerCreditStatusChoice
                                                    String customerCreditStatusChoice = form.getCustomerCreditStatusChoice();
                                                    Workflow customerCreditStatusWorkflow = workflowControl.getWorkflowByName(CustomerCreditStatusConstants.Workflow_CUSTOMER_CREDIT_STATUS);
                                                    WorkflowEntrance customerCreditStatusWorkflowEntrance = customerCreditStatusChoice == null ? customerTypeDetail.getDefaultCustomerCreditStatus()
                                                            : workflowControl.getWorkflowEntranceByName(customerCreditStatusWorkflow, customerCreditStatusChoice);

                                                    if(customerCreditStatusWorkflowEntrance == null) {
                                                        customerCreditStatusWorkflowEntrance = workflowControl.getDefaultWorkflowEntrance(customerCreditStatusWorkflow);
                                                    }

                                                    EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(party.getPrimaryKey());
                                                    workflowControl.addEntityToWorkflow(customerStatusWorkflowEntrance, entityInstance, null, null, createdBy);
                                                    workflowControl.addEntityToWorkflow(customerCreditStatusWorkflowEntrance, entityInstance, null, null, createdBy);

                                                    ContactListLogic.getInstance().setupInitialContactLists(this, party, createdBy);
                                                    
                                                    // ExecutionErrorAccumulator is passed in as null so that an Exception will be thrown if there is an error.
                                                    PartyChainLogic.getInstance().createPartyWelcomeChainInstance(null, party, createdBy);
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), preferredCurrencyIsoName);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownDateTimeFormatName.name(), preferredDateTimeFormatName);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownJavaTimeZoneName.name(), preferredJavaTimeZoneName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), preferredLanguageIsoName);
                                    }
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownDefaultTerm.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidGlAccountCategory.name(), glAccountCategoryName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownArGlAccountName.name(), arGlAccountName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownReturnPolicyName.name(), returnPolicyName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationPolicyName.name(), cancellationPolicyName);
            }
        } else {
            if(customerTypeName != null) {
                addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
            } else {
                addExecutionError(ExecutionErrors.UnknownDefaultCustomerType.name());
            }
        }

        if(customer != null) {
            Party party = customer.getParty();

            result.setEntityRef(party.getPrimaryKey().getEntityRef());
            result.setCustomerName(customer.getCustomerName());
            result.setPartyName(party.getLastDetail().getPartyName());
        }

        return result;
    }
    
}
