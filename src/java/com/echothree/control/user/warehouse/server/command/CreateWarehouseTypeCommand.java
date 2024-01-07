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

package com.echothree.control.user.warehouse.server.command;

import com.echothree.control.user.warehouse.common.form.CreateWarehouseTypeForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.warehouse.server.logic.WarehouseTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;

public class CreateWarehouseTypeCommand
        extends BaseSimpleCommand<CreateWarehouseTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.WarehouseType.name(), SecurityRoles.Create.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Priority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of CreateWarehouseTypeCommand */
    public CreateWarehouseTypeCommand(UserVisitPK userVisitPK, CreateWarehouseTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = WarehouseResultFactory.getCreateWarehouseTypeResult();
        var warehouseTypeName = form.getWarehouseTypeName();
        var priority = Integer.valueOf(form.getPriority());
        var isDefault = Boolean.valueOf(form.getIsDefault());
        var sortOrder = Integer.valueOf(form.getSortOrder());
        var description = form.getDescription();
        var partyPK = getPartyPK();

        var warehouseType = WarehouseTypeLogic.getInstance().createWarehouseType(this, warehouseTypeName, priority,
                isDefault, sortOrder, getPreferredLanguage(), description, partyPK);

        if(warehouseType != null) {
            result.setEntityRef(warehouseType.getPrimaryKey().getEntityRef());
            result.setWarehouseTypeName(warehouseType.getLastDetail().getWarehouseTypeName());
        }

        return result;
    }
    
}
