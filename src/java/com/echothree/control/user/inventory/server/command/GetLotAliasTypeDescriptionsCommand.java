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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.remote.form.GetLotAliasTypeDescriptionsForm;
import com.echothree.control.user.inventory.remote.result.GetLotAliasTypeDescriptionsResult;
import com.echothree.control.user.inventory.remote.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.LotAliasType;
import com.echothree.model.data.inventory.server.entity.LotType;
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

public class GetLotAliasTypeDescriptionsCommand
        extends BaseSimpleCommand<GetLotAliasTypeDescriptionsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LotAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LotAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetLotAliasTypeDescriptionsCommand */
    public GetLotAliasTypeDescriptionsCommand(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        GetLotAliasTypeDescriptionsResult result = InventoryResultFactory.getGetLotAliasTypeDescriptionsResult();
        String lotTypeName = form.getLotTypeName();
        LotType lotType = inventoryControl.getLotTypeByName(lotTypeName);

        if(lotType != null) {
            String lotAliasTypeName = form.getLotAliasTypeName();
            LotAliasType lotAliasType = inventoryControl.getLotAliasTypeByName(lotType, lotAliasTypeName);

            if(lotAliasType != null) {
                result.setLotAliasType(inventoryControl.getLotAliasTypeTransfer(getUserVisit(), lotAliasType));
                result.setLotAliasTypeDescriptions(inventoryControl.getLotAliasTypeDescriptionTransfersByLotAliasType(getUserVisit(), lotAliasType));
            } else {
                addExecutionError(ExecutionErrors.UnknownLotAliasTypeName.name(), lotTypeName, lotAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLotTypeName.name(), lotTypeName);
        }
        
        return result;
    }
    
}
