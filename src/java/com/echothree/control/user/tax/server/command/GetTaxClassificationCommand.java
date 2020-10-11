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

import com.echothree.control.user.tax.common.form.GetTaxClassificationForm;
import com.echothree.control.user.tax.common.result.GetTaxClassificationResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.tax.server.entity.TaxClassification;
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

public class GetTaxClassificationCommand
        extends BaseSimpleCommand<GetTaxClassificationForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.TaxClassification.name(), SecurityRoles.Review.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TaxClassificationName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetTaxClassificationCommand */
    public GetTaxClassificationCommand(UserVisitPK userVisitPK, GetTaxClassificationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GetTaxClassificationResult result = TaxResultFactory.getGetTaxClassificationResult();
        String countryName = form.getCountryName();
        GeoCode geoCode = geoControl.getCountryByAlias(countryName);
        
        if(geoCode != null) {
            var taxControl = (TaxControl)Session.getModelController(TaxControl.class);
            String taxClassificationName = form.getTaxClassificationName();
            TaxClassification taxClassification = taxControl.getTaxClassificationByName(geoCode, taxClassificationName);
            
            if(taxClassification != null) {
                result.setTaxClassification(taxControl.getTaxClassificationTransfer(getUserVisit(), taxClassification));
                
                sendEventUsingNames(taxClassification.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownTaxClassificationName.name(), countryName, taxClassificationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), countryName);
        }
        
        return result;
    }
    
}
