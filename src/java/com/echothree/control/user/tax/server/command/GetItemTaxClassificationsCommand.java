// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.tax.common.form.GetItemTaxClassificationsForm;
import com.echothree.control.user.tax.common.result.GetItemTaxClassificationsResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.Item;
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

public class GetItemTaxClassificationsCommand
        extends BaseSimpleCommand<GetItemTaxClassificationsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ItemTaxClassification.name(), SecurityRoles.List.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetItemTaxClassificationsCommand */
    public GetItemTaxClassificationsCommand(UserVisitPK userVisitPK, GetItemTaxClassificationsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetItemTaxClassificationsResult result = TaxResultFactory.getGetItemTaxClassificationsResult();
        String itemName = form.getItemName();
        String countryName = form.getCountryName();
        var parameterCount = (itemName == null ? 0 : 1) + (countryName == null ? 0 : 1);

        if(parameterCount == 1) {
            var taxControl = Session.getModelController(TaxControl.class);

            if(itemName != null) {
                var itemControl = Session.getModelController(ItemControl.class);
                Item item = itemControl.getItemByName(itemName);

                if(item != null) {
                    result.setItem(itemControl.getItemTransfer(getUserVisit(), item));
                    result.setItemTaxClassifications(taxControl.getItemTaxClassificationTransfersByItem(getUserVisit(), item));
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                }
            } else {
                var geoControl = Session.getModelController(GeoControl.class);
                GeoCode countryGeoCode = geoControl.getCountryByAlias(countryName);

                if(countryGeoCode != null) {
                    result.setCountry(geoControl.getCountryTransfer(getUserVisit(), countryGeoCode));
                    result.setItemTaxClassifications(taxControl.getItemTaxClassificationTransfersByCountryGeoCode(getUserVisit(), countryGeoCode));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }
    
}
