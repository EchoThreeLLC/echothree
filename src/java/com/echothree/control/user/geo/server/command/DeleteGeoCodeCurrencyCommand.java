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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.form.DeleteGeoCodeCurrencyForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeCurrency;
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

public class DeleteGeoCodeCurrencyCommand
        extends BaseSimpleCommand<DeleteGeoCodeCurrencyForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeCurrency.name(), SecurityRoles.Delete.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteGeoCodeCurrencyCommand */
    public DeleteGeoCodeCurrencyCommand(UserVisitPK userVisitPK, DeleteGeoCodeCurrencyForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = Session.getModelController(GeoControl.class);
        String geoCodeName = form.getGeoCodeName();
        GeoCode geoCode = geoControl.getGeoCodeByName(geoCodeName);
        
        if(geoCode != null) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            String currencyIsoName = form.getCurrencyIsoName();
            Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
            
            if(currency != null) {
                GeoCodeCurrency geoCodeCurrency = geoControl.getGeoCodeCurrencyForUpdate(geoCode, currency);
                
                if(geoCodeCurrency != null) {
                    geoControl.deleteGeoCodeCurrency(geoCodeCurrency, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeCurrency.name(), geoCodeName, currencyIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return null;
    }
    
}
