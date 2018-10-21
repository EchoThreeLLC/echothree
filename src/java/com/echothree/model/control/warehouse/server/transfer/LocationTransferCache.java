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

package com.echothree.model.control.warehouse.server.transfer;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.inventory.remote.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.warehouse.common.WarehouseOptions;
import com.echothree.model.control.warehouse.remote.transfer.LocationTransfer;
import com.echothree.model.control.warehouse.remote.transfer.LocationTypeTransfer;
import com.echothree.model.control.warehouse.remote.transfer.LocationUseTypeTransfer;
import com.echothree.model.control.warehouse.remote.transfer.WarehouseTransfer;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.control.warehouse.common.workflow.LocationStatusConstants;
import com.echothree.model.control.workflow.remote.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationDetail;
import com.echothree.model.data.warehouse.server.entity.LocationVolume;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.remote.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class LocationTransferCache
        extends BaseWarehouseTransferCache<Location, LocationTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    boolean includeCapacities;
    boolean includeVolume;
    
    /** Creates a new instance of LocationTransferCache */
    public LocationTransferCache(UserVisit userVisit, WarehouseControl warehouseControl) {
        super(userVisit, warehouseControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeCapacities = options.contains(WarehouseOptions.LocationIncludeCapacities);
            includeVolume = options.contains(WarehouseOptions.LocationIncludeVolume);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public LocationTransfer getLocationTransfer(Location location) {
        LocationTransfer locationTransfer = get(location);
        
        if(locationTransfer == null) {
            LocationDetail locationDetail = location.getLastDetail();
            Warehouse warehouse = warehouseControl.getWarehouse(locationDetail.getWarehouseParty());
            WarehouseTransfer warehouseTransfer = warehouseControl.getWarehouseTransfer(userVisit, warehouse);
            String locationName = locationDetail.getLocationName();
            LocationTypeTransfer locationTypeTransfer = warehouseControl.getLocationTypeTransfer(userVisit, locationDetail.getLocationType());
            LocationUseTypeTransfer locationUseTypeTransfer = warehouseControl.getLocationUseTypeTransfer(userVisit, locationDetail.getLocationUseType());
            Integer velocity = locationDetail.getVelocity();
            InventoryLocationGroupTransfer inventoryLocationGroup = inventoryControl.getInventoryLocationGroupTransfer(userVisit, locationDetail.getInventoryLocationGroup());
            String description = warehouseControl.getBestLocationDescription(location, getLanguage());
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(location.getPrimaryKey());
            WorkflowEntityStatusTransfer locationStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    LocationStatusConstants.Workflow_LOCATION_STATUS, entityInstance);
            
            locationTransfer = new LocationTransfer(warehouseTransfer, locationName, locationTypeTransfer, locationUseTypeTransfer,
                    velocity, inventoryLocationGroup, description, locationStatusTransfer);
            put(location, locationTransfer);
            
            if(includeCapacities) {
                locationTransfer.setLocationCapacities(new ListWrapper<>(warehouseControl.getLocationCapacityTransfersByLocation(userVisit, location)));
            }
            
            if(includeVolume) {
                LocationVolume locationVolume = warehouseControl.getLocationVolume(location);
                
                if(locationVolume != null) {
                    locationTransfer.setLocationVolume(warehouseControl.getLocationVolumeTransfer(userVisit, locationVolume));
                }
            }
        }
        
        return locationTransfer;
    }
    
}
