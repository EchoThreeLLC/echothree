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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.form.GetLocationUseTypesForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.LocationUseTypeControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.model.data.warehouse.server.factory.LocationUseTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetLocationUseTypesCommand
        extends BaseMultipleEntitiesCommand<LocationUseType, GetLocationUseTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationUseType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetLocationUseTypesCommand */
    public GetLocationUseTypesCommand(UserVisitPK userVisitPK, GetLocationUseTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<LocationUseType> getEntities() {
        var locationUseTypeControl = Session.getModelController(LocationUseTypeControl.class);

        return locationUseTypeControl.getLocationUseTypes();
    }

    @Override
    protected BaseResult getTransfers(Collection<LocationUseType> entities) {
        var result = WarehouseResultFactory.getGetLocationUseTypesResult();

        if(entities != null) {
            var locationUseTypeControl = Session.getModelController(LocationUseTypeControl.class);

            if(session.hasLimit(LocationUseTypeFactory.class)) {
                result.setLocationUseTypeCount(locationUseTypeControl.countLocationUseTypes());
            }

            result.setLocationUseTypes(locationUseTypeControl.getLocationUseTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
