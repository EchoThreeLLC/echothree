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

package com.echothree.control.user.payment.server.command;

import com.echothree.control.user.payment.common.edit.PartyPaymentMethodEdit;
import com.echothree.control.user.payment.common.edit.PaymentEditFactory;
import com.echothree.control.user.payment.common.form.EditPartyPaymentMethodForm;
import com.echothree.control.user.payment.common.result.EditPartyPaymentMethodResult;
import com.echothree.control.user.payment.common.result.PaymentResultFactory;
import com.echothree.control.user.payment.common.spec.PartyPaymentMethodSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.common.PaymentMethodTypes;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.control.payment.server.logic.PartyPaymentMethodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethod;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodCreditCard;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodCreditCardSecurityCode;
import com.echothree.model.data.payment.server.entity.PartyPaymentMethodDetail;
import com.echothree.model.data.payment.server.value.PartyPaymentMethodCreditCardSecurityCodeValue;
import com.echothree.model.data.payment.server.value.PartyPaymentMethodCreditCardValue;
import com.echothree.model.data.payment.server.value.PartyPaymentMethodDetailValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.language.Soundex;

public class EditPartyPaymentMethodCommand
        extends BaseAbstractEditCommand<PartyPaymentMethodSpec, PartyPaymentMethodEdit, EditPartyPaymentMethodResult, PartyPaymentMethod, PartyPaymentMethod> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyPaymentMethod.name(), SecurityRoles.Edit.name())
                    )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyPaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("DeleteWhenUnused", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Number", FieldType.STRING, false, 1L, 20L), // RegExp Validated
                new FieldDefinition("SecurityCode", FieldType.STRING, false, 1L, 10L), // RegExp Validated
                new FieldDefinition("ExpirationMonth", FieldType.CREDIT_CARD_MONTH, false, null, null),
                new FieldDefinition("ExpirationYear", FieldType.CREDIT_CARD_YEAR, false, null, null),
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
                new FieldDefinition("Name", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("BillingContactMechanismName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IssuerName", FieldType.STRING, false, 1L, 60L),
                new FieldDefinition("IssuerContactMechanismName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyPaymentMethodCommand */
    public EditPartyPaymentMethodCommand(UserVisitPK userVisitPK, EditPartyPaymentMethodForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyPaymentMethodResult getResult() {
        return PaymentResultFactory.getEditPartyPaymentMethodResult();
    }

    @Override
    public PartyPaymentMethodEdit getEdit() {
        return PaymentEditFactory.getPartyPaymentMethodEdit();
    }

    @Override
    public PartyPaymentMethod getEntity(EditPartyPaymentMethodResult result) {
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
        PartyPaymentMethod partyPaymentMethod = null;
        String partyPaymentMethodName = spec.getPartyPaymentMethodName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            partyPaymentMethod = partyPaymentMethodControl.getPartyPaymentMethodByName(partyPaymentMethodName);
        } else { // EditMode.UPDATE
            partyPaymentMethod = partyPaymentMethodControl.getPartyPaymentMethodByNameForUpdate(partyPaymentMethodName);
        }

        if(partyPaymentMethod != null) {
            Party party = getParty();
            String partyTypeName = party.getLastDetail().getPartyType().getPartyTypeName();

            // If the executing Party is a CUSTOMER, and the PartyPaymentMethod isn't for the executing Party,
            // return a UnknownPartyPaymentMethodName error.
            if(partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
                if(!partyPaymentMethod.getLastDetail().getParty().equals(party)) {
                    partyPaymentMethod = null;
                }
            }
        }

        if(partyPaymentMethod == null) {
            addExecutionError(ExecutionErrors.UnknownPartyPaymentMethodName.name(), partyPaymentMethodName);
        } else {
            result.setPartyPaymentMethod(partyPaymentMethodControl.getPartyPaymentMethodTransfer(getUserVisit(), partyPaymentMethod));
        }

        return partyPaymentMethod;
    }

    @Override
    public PartyPaymentMethod getLockEntity(PartyPaymentMethod partyPaymentMethod) {
        return partyPaymentMethod;
    }

    @Override
    public void fillInResult(EditPartyPaymentMethodResult result, PartyPaymentMethod partyPaymentMethod) {
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);

        result.setPartyPaymentMethod(partyPaymentMethodControl.getPartyPaymentMethodTransfer(getUserVisit(), partyPaymentMethod));
    }

    @Override
    public void doLock(PartyPaymentMethodEdit edit, PartyPaymentMethod partyPaymentMethod) {
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
        PartyPaymentMethodDetail partyPaymentMethodDetail = partyPaymentMethod.getLastDetail();
        String paymentMethodTypeName = partyPaymentMethodDetail.getPaymentMethod().getLastDetail().getPaymentMethodType().getLastDetail().getPaymentMethodTypeName();

        edit.setDescription(partyPaymentMethodDetail.getDescription());
        edit.setDeleteWhenUnused(partyPaymentMethodDetail.getDeleteWhenUnused().toString());
        edit.setIsDefault(partyPaymentMethodDetail.getIsDefault().toString());
        edit.setSortOrder(partyPaymentMethodDetail.getSortOrder().toString());

        if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
            PartyPaymentMethodCreditCard partyPaymentMethodCreditCard = partyPaymentMethodControl.getPartyPaymentMethodCreditCard(partyPaymentMethod);
            PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode = partyPaymentMethodControl.getPartyPaymentMethodCreditCardSecurityCode(partyPaymentMethod);
            boolean includeCreditCardNumber = SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(null, getParty(),
                    SecurityRoleGroups.PartyPaymentMethod.name(), SecurityRoles.CreditCard.name());

            if(partyPaymentMethodCreditCard != null) {
                PersonalTitle personalTitle = partyPaymentMethodCreditCard.getPersonalTitle();
                NameSuffix nameSuffix = partyPaymentMethodCreditCard.getNameSuffix();
                Integer expirationMonth = partyPaymentMethodCreditCard.getExpirationMonth();
                Integer expirationYear = partyPaymentMethodCreditCard.getExpirationYear();
                PartyContactMechanism billingPartyContactMechanism = partyPaymentMethodCreditCard.getBillingPartyContactMechanism();
                PartyContactMechanism issuerPartyContactMechanism = partyPaymentMethodCreditCard.getIssuerPartyContactMechanism();

                edit.setPersonalTitleId(personalTitle == null? null: personalTitle.getPrimaryKey().getEntityId().toString());
                edit.setFirstName(partyPaymentMethodCreditCard.getFirstName());
                edit.setMiddleName(partyPaymentMethodCreditCard.getMiddleName());
                edit.setLastName(partyPaymentMethodCreditCard.getLastName());
                edit.setNameSuffixId(nameSuffix == null? null: nameSuffix.getPrimaryKey().getEntityId().toString());
                edit.setName(partyPaymentMethodCreditCard.getName());
                if(includeCreditCardNumber) {
                    edit.setNumber(partyPaymentMethodControl.decodePartyPaymentMethodCreditCardNumber(partyPaymentMethodCreditCard));
                }
                edit.setExpirationMonth(expirationMonth == null? null: expirationMonth.toString());
                edit.setExpirationYear(expirationYear == null? null: expirationYear.toString());
                edit.setBillingContactMechanismName(billingPartyContactMechanism == null? null: billingPartyContactMechanism.getLastDetail().getContactMechanism().getLastDetail().getContactMechanismName());
                edit.setIssuerName(partyPaymentMethodCreditCard.getIssuerName());
                edit.setIssuerContactMechanismName(issuerPartyContactMechanism == null? null: issuerPartyContactMechanism.getLastDetail().getContactMechanism().getLastDetail().getContactMechanismName());
            }

            if(partyPaymentMethodCreditCardSecurityCode != null) {
                if(includeCreditCardNumber) {
                    edit.setSecurityCode(partyPaymentMethodControl.decodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(partyPaymentMethodCreditCardSecurityCode));
                }
            }
        }
    }

    private Party getPartyFromPartyPaymentMethod(PartyPaymentMethod partyPaymentMethod) {
        return partyPaymentMethod.getLastDetail().getParty();
    }

    @Override
    public void canUpdate(PartyPaymentMethod partyPaymentMethod) {
        PartyPaymentMethodLogic.getInstance().checkPartyPaymentMethod(session, getUserVisit(), this, getPartyFromPartyPaymentMethod(partyPaymentMethod),
                partyPaymentMethod.getLastDetail().getPaymentMethod(), edit);
    }

    @Override
    public void doUpdate(PartyPaymentMethod partyPaymentMethod) {
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
        PartyPK executingPartyPK = getPartyPK();
        PartyPaymentMethodDetailValue partyPaymentMethodDetailValue = partyPaymentMethodControl.getPartyPaymentMethodDetailValueForUpdate(partyPaymentMethod);
        String paymentMethodTypeName = partyPaymentMethod.getLastDetail().getPaymentMethod().getLastDetail().getPaymentMethodType().getLastDetail().getPaymentMethodTypeName();

        partyPaymentMethodDetailValue.setDescription(edit.getDescription());
        partyPaymentMethodDetailValue.setDeleteWhenUnused(Boolean.valueOf(edit.getDeleteWhenUnused()));
        partyPaymentMethodDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        partyPaymentMethodDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        partyPaymentMethodControl.updatePartyPaymentMethodFromValue(partyPaymentMethodDetailValue, executingPartyPK);

        if(paymentMethodTypeName.equals(PaymentMethodTypes.CREDIT_CARD.name())) {
            var contactControl = Session.getModelController(ContactControl.class);
            var partyControl = Session.getModelController(PartyControl.class);
            Party party = getPartyFromPartyPaymentMethod(partyPaymentMethod);
            Soundex soundex = new Soundex();
            String personalTitleId = edit.getPersonalTitleId();
            PersonalTitle personalTitle = personalTitleId == null ? null : partyControl.convertPersonalTitleIdToEntity(personalTitleId, EntityPermission.READ_ONLY);
            String firstName = edit.getFirstName();
            String middleName = edit.getMiddleName();
            String lastName = edit.getLastName();
            String nameSuffixId = edit.getNameSuffixId();
            NameSuffix nameSuffix = nameSuffixId == null ? null : partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
            String name = edit.getName();
            String number = edit.getNumber();
            String securityCode = edit.getSecurityCode();
            String strExpirationMonth = edit.getExpirationMonth();
            Integer expirationMonth = strExpirationMonth != null? Integer.valueOf(strExpirationMonth): null;
            String strExpirationYear = edit.getExpirationYear();
            Integer expirationYear = strExpirationYear != null? Integer.valueOf(strExpirationYear): null;
            String billingContactMechanismName = edit.getBillingContactMechanismName();
            ContactMechanism billingContactMechanism = billingContactMechanismName == null ? null : contactControl.getContactMechanismByName(billingContactMechanismName);
            PartyContactMechanism billingPartyContactMechanism = billingContactMechanism == null? null: contactControl.getPartyContactMechanism(party, billingContactMechanism);
            String issuerName = edit.getIssuerName();
            String issuerContactMechanismName = edit.getIssuerContactMechanismName();
            ContactMechanism issuerContactMechanism = issuerContactMechanismName == null ? null : contactControl.getContactMechanismByName(issuerContactMechanismName);
            PartyContactMechanism issuerPartyContactMechanism = issuerContactMechanism == null? null: contactControl.getPartyContactMechanism(party, issuerContactMechanism);
            PartyPaymentMethodCreditCardValue partyPaymentMethodCreditCardValue = partyPaymentMethodControl.getPartyPaymentMethodCreditCardValueForUpdate(partyPaymentMethod);
            PartyPaymentMethodCreditCardSecurityCode partyPaymentMethodCreditCardSecurityCode = partyPaymentMethodControl.getPartyPaymentMethodCreditCardSecurityCodeForUpdate(partyPaymentMethod);

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

            partyPaymentMethodCreditCardValue.setNumber(partyPaymentMethodControl.encodePartyPaymentMethodCreditCardNumber(number));
            partyPaymentMethodCreditCardValue.setExpirationMonth(expirationMonth);
            partyPaymentMethodCreditCardValue.setExpirationYear(expirationYear);
            partyPaymentMethodCreditCardValue.setPersonalTitlePK(personalTitle == null? null: personalTitle.getPrimaryKey());
            partyPaymentMethodCreditCardValue.setFirstName(firstName);
            partyPaymentMethodCreditCardValue.setFirstNameSdx(firstNameSdx);
            partyPaymentMethodCreditCardValue.setMiddleName(middleName);
            partyPaymentMethodCreditCardValue.setMiddleNameSdx(middleNameSdx);
            partyPaymentMethodCreditCardValue.setLastName(lastName);
            partyPaymentMethodCreditCardValue.setLastNameSdx(lastNameSdx);
            partyPaymentMethodCreditCardValue.setNameSuffixPK(nameSuffix == null? null: nameSuffix.getPrimaryKey());
            partyPaymentMethodCreditCardValue.setName(name);
            partyPaymentMethodCreditCardValue.setBillingPartyContactMechanismPK(billingPartyContactMechanism == null? null: billingPartyContactMechanism.getPrimaryKey());
            partyPaymentMethodCreditCardValue.setIssuerName(issuerName);
            partyPaymentMethodCreditCardValue.setIssuerPartyContactMechanismPK(issuerPartyContactMechanism == null? null: issuerPartyContactMechanism.getPrimaryKey());

            partyPaymentMethodControl.updatePartyPaymentMethodCreditCardFromValue(partyPaymentMethodCreditCardValue, executingPartyPK);

            if(partyPaymentMethodCreditCardSecurityCode == null && securityCode != null) {
                partyPaymentMethodControl.createPartyPaymentMethodCreditCardSecurityCode(partyPaymentMethod, securityCode, executingPartyPK);
            } else {
                if(partyPaymentMethodCreditCardSecurityCode != null && securityCode == null) {
                    partyPaymentMethodControl.deletePartyPaymentMethodCreditCardSecurityCode(partyPaymentMethodCreditCardSecurityCode, executingPartyPK);
                } else {
                    if(partyPaymentMethodCreditCardSecurityCode != null && securityCode != null) {
                        PartyPaymentMethodCreditCardSecurityCodeValue partyPaymentMethodCreditCardSecurityCodeValue = partyPaymentMethodControl.getPartyPaymentMethodCreditCardSecurityCodeValueForUpdate(partyPaymentMethod);

                        partyPaymentMethodCreditCardSecurityCodeValue.setSecurityCode(partyPaymentMethodControl.encodePartyPaymentMethodCreditCardSecurityCodeSecurityCode(securityCode));
                        partyPaymentMethodControl.updatePartyPaymentMethodCreditCardSecurityCodeFromValue(partyPaymentMethodCreditCardSecurityCodeValue, executingPartyPK);
                    }
                }
            }
        }
    }

}
