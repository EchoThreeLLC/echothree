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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.form.GetCountiesForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetCountiesCommand
        extends BaseMultipleEntitiesCommand<GeoCode, GetCountiesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.VENDOR.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.County.name(), SecurityRoles.List.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("StateName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetCountiesCommand */
    public GetCountiesCommand(UserVisitPK userVisitPK, GetCountiesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    GeoCode stateGeoCode;

    @Override
    protected Collection<GeoCode> getEntities() {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        var countryName = form.getCountryName();
        var countryGeoCode = geoControl.getCountryByAlias(countryName);
        Collection<GeoCode> countyGeoCodes = null;

        if(countryGeoCode != null) {
            var stateName = form.getStateName();

            stateGeoCode = geoControl.getStateByAlias(countryGeoCode, stateName);

            if(stateGeoCode != null) {
                countyGeoCodes = geoControl.getCountiesByState(stateGeoCode);
            } else {
                addExecutionError(ExecutionErrors.UnknownStateName.name(), stateName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
        }

        return countyGeoCodes;
    }

    @Override
    protected BaseResult getTransfers(Collection<GeoCode> entities) {
        var result = GeoResultFactory.getGetCountiesResult();

        if(entities != null) {
            var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
            var userVisit = getUserVisit();

            result.setState(geoControl.getStateTransfer(userVisit, stateGeoCode));
            result.setCounties(geoControl.getCountyTransfers(userVisit, entities));
        }

        return result;
    }

}
