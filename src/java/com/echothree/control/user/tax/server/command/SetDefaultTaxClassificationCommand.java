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

package com.echothree.control.user.tax.server.command;

import com.echothree.control.user.tax.common.form.SetDefaultTaxClassificationForm;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.tax.server.value.TaxClassificationDetailValue;
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

public class SetDefaultTaxClassificationCommand
        extends BaseSimpleCommand<SetDefaultTaxClassificationForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.TaxClassification.name(), SecurityRoles.Edit.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TaxClassificationName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultTaxClassificationCommand */
    public SetDefaultTaxClassificationCommand(UserVisitPK userVisitPK, SetDefaultTaxClassificationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        String countryName = form.getCountryName();
        GeoCode geoCode = geoControl.getCountryByAlias(countryName);
        
        if(geoCode != null) {
            var taxControl = (TaxControl)Session.getModelController(TaxControl.class);
            String taxClassificationName = form.getTaxClassificationName();
            TaxClassificationDetailValue taxClassificationDetailValue = taxControl.getTaxClassificationDetailValueByNameForUpdate(geoCode,
                    taxClassificationName);
            
            if(taxClassificationDetailValue != null) {
                taxClassificationDetailValue.setIsDefault(Boolean.TRUE);
                taxControl.updateTaxClassificationFromValue(taxClassificationDetailValue, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownTaxClassificationName.name(), countryName, taxClassificationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
        }
        
        return null;
    }
    
}
