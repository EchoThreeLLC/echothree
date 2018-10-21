// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.item.remote.form.GetHarmonizedTariffScheduleCodeUnitForm;
import com.echothree.control.user.item.remote.result.GetHarmonizedTariffScheduleCodeUnitResult;
import com.echothree.control.user.item.remote.result.ItemResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnit;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetHarmonizedTariffScheduleCodeUnitCommand
        extends BaseSimpleCommand<GetHarmonizedTariffScheduleCodeUnitForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCodeUnit.name(), SecurityRoles.Review.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeUnitName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetHarmonizedTariffScheduleCodeUnitCommand */
    public GetHarmonizedTariffScheduleCodeUnitCommand(UserVisitPK userVisitPK, GetHarmonizedTariffScheduleCodeUnitForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        GetHarmonizedTariffScheduleCodeUnitResult result = ItemResultFactory.getGetHarmonizedTariffScheduleCodeUnitResult();
        String harmonizedTariffScheduleCodeUnitName = form.getHarmonizedTariffScheduleCodeUnitName();
        HarmonizedTariffScheduleCodeUnit harmonizedTariffScheduleCodeUnit = itemControl.getHarmonizedTariffScheduleCodeUnitByName(harmonizedTariffScheduleCodeUnitName);
        
        if(harmonizedTariffScheduleCodeUnit != null) {
            result.setHarmonizedTariffScheduleCodeUnit(itemControl.getHarmonizedTariffScheduleCodeUnitTransfer(getUserVisit(), harmonizedTariffScheduleCodeUnit));
            
            sendEventUsingNames(harmonizedTariffScheduleCodeUnit.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUnitName.name(), harmonizedTariffScheduleCodeUnitName);
        }
        
        return result;
    }
    
}
