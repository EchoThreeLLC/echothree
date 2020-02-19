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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetHarmonizedTariffScheduleCodeUseForm;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodeUseResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUse;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseType;
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

public class GetHarmonizedTariffScheduleCodeUseCommand
        extends BaseSimpleCommand<GetHarmonizedTariffScheduleCodeUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCode.name(), SecurityRoles.HarmonizedTariffScheduleCodeUse.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetHarmonizedTariffScheduleCodeUseCommand */
    public GetHarmonizedTariffScheduleCodeUseCommand(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GetHarmonizedTariffScheduleCodeUseResult result = ItemResultFactory.getGetHarmonizedTariffScheduleCodeUseResult();
        String countryName = form.getCountryName();
        GeoCode geoCode = geoControl.getCountryByAlias(countryName);
        
        if(geoCode != null) {
            var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            String harmonizedTariffScheduleCodeName = form.getHarmonizedTariffScheduleCodeName();
            HarmonizedTariffScheduleCode harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeByName(geoCode, harmonizedTariffScheduleCodeName);
            
            if(harmonizedTariffScheduleCode != null) {
                String harmonizedTariffScheduleCodeUseTypeName = form.getHarmonizedTariffScheduleCodeUseTypeName();
                HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName);

                if(harmonizedTariffScheduleCodeUseType != null) {
                    HarmonizedTariffScheduleCodeUse harmonizedTariffScheduleCodeUse = itemControl.getHarmonizedTariffScheduleCodeUse(harmonizedTariffScheduleCode, harmonizedTariffScheduleCodeUseType);
                    
                    if(harmonizedTariffScheduleCodeUse != null) {
                        result.setHarmonizedTariffScheduleCodeUse(itemControl.getHarmonizedTariffScheduleCodeUseTransfer(getUserVisit(), harmonizedTariffScheduleCodeUse));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUse.name(), countryName, harmonizedTariffScheduleCodeName, harmonizedTariffScheduleCodeUseTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUseTypeName.name(), harmonizedTariffScheduleCodeUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeName.name(), countryName, harmonizedTariffScheduleCodeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), countryName);
        }
        
        return result;
    }
    
}
