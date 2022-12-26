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

package com.echothree.model.control.party.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.party.server.entity.Person;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

public class BasePartyObject
        extends BaseEntityInstanceObject {

    protected final Party party; // Always Present

    public BasePartyObject(Party party) {
        super(party.getPrimaryKey());
        
        this.party = party;
    }

    private PartyDetail partyDetail; // Optional, use getPartyDetail()

    protected PartyDetail getPartyDetail() {
        if(partyDetail == null) {
            partyDetail = party.getLastDetail();
        }
        
        return partyDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("party name")
    @GraphQLNonNull
    public String getPartyName() {
        return getPartyDetail().getPartyName();
    }

    @GraphQLField
    @GraphQLDescription("party type")
    @GraphQLNonNull
    public PartyTypeObject getPartyType() {
        return new PartyTypeObject(getPartyDetail().getPartyType());
    }

    @GraphQLField
    @GraphQLDescription("preferred language")
    public LanguageObject getPreferredLanguage(final DataFetchingEnvironment env) {
        Language preferredLanguage = getPartyDetail().getPreferredLanguage();
        
        return preferredLanguage != null && PartySecurityUtils.getInstance().getHasLanguageAccess(env) ? new LanguageObject(preferredLanguage) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred language")
    public CurrencyObject getPreferredCurrency(final DataFetchingEnvironment env) {
        Currency preferredCurrency = getPartyDetail().getPreferredCurrency();
        
        return preferredCurrency != null && AccountingSecurityUtils.getInstance().getHasCurrencyAccess(env) ? new CurrencyObject(preferredCurrency) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred time zone")
    public TimeZoneObject getPreferredTimeZone(final DataFetchingEnvironment env) {
        TimeZone preferredTimeZone = getPartyDetail().getPreferredTimeZone();
        
        return preferredTimeZone != null && PartySecurityUtils.getInstance().getHasTimeZoneAccess(env) ? new TimeZoneObject(preferredTimeZone) : null;
    }

    @GraphQLField
    @GraphQLDescription("preferred date time format")
    public DateTimeFormatObject getPreferredDateTimeFormat(final DataFetchingEnvironment env) {
        DateTimeFormat preferredDateTimeFormat = getPartyDetail().getPreferredDateTimeFormat();
        
        return preferredDateTimeFormat != null && PartySecurityUtils.getInstance().getHasDateTimeFormatAccess(env) ? new DateTimeFormatObject(preferredDateTimeFormat) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("person")
    public PersonObject getPerson(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        Person person = partyControl.getPerson(party);
        
        return person == null ? null : new PersonObject(person);
    }
    
    @GraphQLField
    @GraphQLDescription("party group")
    public PartyGroupObject getPartyGroup(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyGroup partyGroup = partyControl.getPartyGroup(party);
        
        return partyGroup == null ? null : new PartyGroupObject(partyGroup);
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return partyControl.getBestPartyDescription(party, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }
    
}
