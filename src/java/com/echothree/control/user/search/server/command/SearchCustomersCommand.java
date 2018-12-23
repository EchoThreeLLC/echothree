// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.SearchCustomersForm;
import com.echothree.control.user.search.common.result.SearchCustomersResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.customer.server.search.CustomerSearchEvaluator;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeCountry;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class SearchCustomersCommand
        extends BaseSimpleCommand<SearchCustomersForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> formAliasFieldDefinitions;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Customer.name(), SecurityRoles.Search.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FirstName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("FirstNameSoundex", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("MiddleName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("MiddleNameSoundex", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("LastName", FieldType.STRING, false, 1L, 20L),
                new FieldDefinition("LastNameSoundex", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("Name", FieldType.STRING, false, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AreaCode", FieldType.STRING, false, 1L, 5L),
                new FieldDefinition("TelephoneNumber", FieldType.STRING, false, 1L, 25L),
                new FieldDefinition("TelephoneExtension", FieldType.NUMBERS, false, 1L, 10L),
                new FieldDefinition("EmailAddress", FieldType.EMAIL_ADDRESS, false, null, null),
                new FieldDefinition("CustomerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CreatedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ModifiedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("Fields", FieldType.STRING, false, null, null)
                ));

        formAliasFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of SearchCustomersCommand */
    public SearchCustomersCommand(UserVisitPK userVisitPK, SearchCustomersForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected ValidationResult validate() {
        Validator validator = new Validator(this);
        ValidationResult validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            String partyAliasTypeName = form.getPartyAliasTypeName();
            
            if(partyAliasTypeName != null) {
                validationResult = validator.validate(form, formAliasFieldDefinitions);
            }
        }
        
        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        SearchControl searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchCustomersResult result = SearchResultFactory.getSearchCustomersResult();
        SearchKind searchKind = searchControl.getSearchKindByName(SearchConstants.SearchKind_CUSTOMER);
        
        if(searchKind != null) {
            String searchTypeName = form.getSearchTypeName();
            SearchType searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);
            
            if(searchType != null) {
                CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
                String customerTypeName = form.getCustomerTypeName();
                CustomerType customerType = customerTypeName == null? null: customerControl.getCustomerTypeByName(customerTypeName);
                
                if(customerTypeName == null || customerType != null) {
                    String partyAliasTypeName = form.getPartyAliasTypeName();
                    String alias = form.getAlias();
                    PartyAliasType partyAliasType = null;
                    
                    if(partyAliasTypeName != null) {
                        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                        PartyType partyType = partyControl.getPartyTypeByName(PartyConstants.PartyType_CUSTOMER);
                        
                        if(partyType != null) {
                            partyAliasType = partyControl.getPartyAliasTypeByName(partyType, partyAliasTypeName);

                            if(partyAliasType == null) {
                                addExecutionError(ExecutionErrors.UnknownPartyAliasTypeName.name(), PartyConstants.PartyType_CUSTOMER, partyAliasTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), PartyConstants.PartyType_CUSTOMER);
                        }
                    }
                    
                    if(!hasExecutionErrors()) {
                        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
                        String countryName = form.getCountryName();
                        String countryAlias = countryName == null ? null : StringUtils.getInstance().cleanStringToName(countryName).toUpperCase();
                        GeoCode countryGeoCode = countryAlias == null ? null : geoControl.getCountryByAlias(countryAlias);

                        if(countryName == null || countryGeoCode != null) {
                            GeoCodeCountry geoCodeCountry = countryGeoCode == null ? null : geoControl.getGeoCodeCountry(countryGeoCode);
                            String areaCode = form.getAreaCode();
                            String areaCodePattern = geoCodeCountry == null ? null : geoCodeCountry.getAreaCodePattern();
                            Pattern pattern = areaCodePattern == null ? null : Pattern.compile(areaCodePattern);

                            if(areaCode == null || (pattern == null || pattern.matcher(areaCode).matches())) {
                                String telephoneNumberPattern = countryGeoCode == null ? null : geoCodeCountry.getTelephoneNumberPattern();
                                String telephoneNumber = form.getTelephoneNumber();

                                pattern = telephoneNumberPattern == null ? null : Pattern.compile(telephoneNumberPattern);

                                if(telephoneNumber == null || (pattern == null || pattern.matcher(telephoneNumber).matches())) {
                                    SearchLogic searchLogic = SearchLogic.getInstance();
                                    UserVisit userVisit = getUserVisit();
                                    CustomerSearchEvaluator customerSearchEvaluator = new CustomerSearchEvaluator(userVisit, searchType,
                                            searchLogic.getDefaultSearchDefaultOperator(null), searchLogic.getDefaultSearchSortOrder(null, searchKind),
                                            searchLogic.getDefaultSearchSortDirection(null));
                                    String firstNameSoundex = form.getFirstNameSoundex();
                                    String middleNameSoundex = form.getMiddleNameSoundex();
                                    String lastNameSoundex = form.getLastNameSoundex();
                                    String createdSince = form.getCreatedSince();
                                    String modifiedSince = form.getModifiedSince();
                                    String fields = form.getFields();

                                    customerSearchEvaluator.setFirstName(form.getFirstName());
                                    customerSearchEvaluator.setFirstNameSoundex(firstNameSoundex == null ? false : Boolean.valueOf(firstNameSoundex));
                                    customerSearchEvaluator.setMiddleName(form.getMiddleName());
                                    customerSearchEvaluator.setMiddleNameSoundex(middleNameSoundex == null ? false : Boolean.valueOf(middleNameSoundex));
                                    customerSearchEvaluator.setLastName(form.getLastName());
                                    customerSearchEvaluator.setLastNameSoundex(lastNameSoundex == null ? false : Boolean.valueOf(lastNameSoundex));
                                    customerSearchEvaluator.setQ(this, form.getName());
                                    customerSearchEvaluator.setCountryGeoCode(countryGeoCode);
                                    customerSearchEvaluator.setAreaCode(areaCode);
                                    customerSearchEvaluator.setTelephoneNumber(telephoneNumber);
                                    customerSearchEvaluator.setTelephoneExtension(form.getTelephoneExtension());
                                    customerSearchEvaluator.setEmailAddress(form.getEmailAddress());
                                    customerSearchEvaluator.setCustomerType(customerType);
                                    customerSearchEvaluator.setPartyAliasType(partyAliasType);
                                    customerSearchEvaluator.setAlias(alias);
                                    customerSearchEvaluator.setCustomerName(form.getCustomerName());
                                    customerSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                                    customerSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                                    customerSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                                    if(!hasExecutionErrors()) {
                                        result.setCount(customerSearchEvaluator.execute(this));
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidTelephoneNumber.name(), telephoneNumber);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidAreaCode.name(), areaCode);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSearchTypeName.name(), SearchConstants.SearchKind_CUSTOMER, searchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), SearchConstants.SearchKind_CUSTOMER);
        }
        
        return result;
    }
}
