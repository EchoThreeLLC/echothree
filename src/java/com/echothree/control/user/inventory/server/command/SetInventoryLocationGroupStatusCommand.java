// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.inventory.common.form.SetInventoryLocationGroupStatusForm;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetInventoryLocationGroupStatusCommand
        extends BaseSimpleCommand<SetInventoryLocationGroupStatusForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
        new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
        new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
        new FieldDefinition("InventoryLocationGroupStatusChoice", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of SetInventoryLocationGroupStatusCommand */
    public SetInventoryLocationGroupStatusCommand(UserVisitPK userVisitPK, SetInventoryLocationGroupStatusForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouseName = form.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            var warehouseParty = warehouse.getParty();
            var inventoryLocationGroupName = form.getInventoryLocationGroupName();
            var inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
            
            if(inventoryLocationGroup != null) {
                var inventoryLocationGroupStatusChoice = form.getInventoryLocationGroupStatusChoice();
                
                inventoryControl.setInventoryLocationGroupStatus(this, inventoryLocationGroup, inventoryLocationGroupStatusChoice, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return null;
    }
    
}
