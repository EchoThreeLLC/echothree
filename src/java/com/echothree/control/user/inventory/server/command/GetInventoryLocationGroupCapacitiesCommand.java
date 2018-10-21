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


import com.echothree.control.user.inventory.remote.form.GetInventoryLocationGroupCapacitiesForm;
import com.echothree.control.user.inventory.remote.result.GetInventoryLocationGroupCapacitiesResult;
import com.echothree.control.user.inventory.remote.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetInventoryLocationGroupCapacitiesCommand
        extends BaseSimpleCommand<GetInventoryLocationGroupCapacitiesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetInventoryLocationGroupCapacitiesCommand */
    public GetInventoryLocationGroupCapacitiesCommand(UserVisitPK userVisitPK, GetInventoryLocationGroupCapacitiesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        WarehouseControl warehouseControl = (WarehouseControl)Session.getModelController(WarehouseControl.class);
        GetInventoryLocationGroupCapacitiesResult result = InventoryResultFactory.getGetInventoryLocationGroupCapacitiesResult();
        String warehouseName = form.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
            String inventoryLocationGroupName = form.getInventoryLocationGroupName();
            InventoryLocationGroup inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouse.getParty(), inventoryLocationGroupName);
            
            if(inventoryLocationGroup != null) {
                UserVisit userVisit = getUserVisit();
                
                result.setInventoryLocationGroup(inventoryControl.getInventoryLocationGroupTransfer(userVisit, inventoryLocationGroup));
                result.setInventoryLocationGroupCapacities(inventoryControl.getInventoryLocationGroupCapacityTransfersByInventoryLocationGroup(userVisit, inventoryLocationGroup));
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
