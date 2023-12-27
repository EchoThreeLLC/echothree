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

import com.echothree.control.user.warehouse.common.form.CreateLocationForm;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.model.control.inventory.common.workflow.InventoryLocationGroupStatusConstants;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.common.workflow.LocationStatusConstants;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.warehouse.server.logic.LocationUseTypeLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.LocationNameElement;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.regex.Pattern;

public class CreateLocationCommand
        extends BaseSimpleCommand<CreateLocationForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Location.name(), SecurityRoles.Create.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LocationUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Velocity", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of CreateLocationCommand */
    public CreateLocationCommand(UserVisitPK userVisitPK, CreateLocationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = WarehouseResultFactory.getCreateLocationResult();
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouseName = form.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var warehouseParty = warehouse.getParty();
            var locationName = form.getLocationName();
            var location = warehouseControl.getLocationByName(warehouseParty, locationName);
            
            if(location == null) {
                var locationTypeName = form.getLocationTypeName();
                var locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
                
                if(locationType != null) {
                    var locationNameElements = warehouseControl.getLocationNameElementsByLocationType(locationType);
                    var endIndex = 0;
                    var validLocationName = true;
                    
                    for(var iter = locationNameElements.iterator(); iter.hasNext() && validLocationName;) {
                        var locationNameElement = (LocationNameElement)iter.next();
                        var locationNameElementDetail = locationNameElement.getLastDetail();
                        var validationPattern = locationNameElementDetail.getValidationPattern();

                        var beginIndex = locationNameElementDetail.getOffset();

                        // LocationNameElements are sorted by their starting index, the last one will always
                        // be able to give the ending index (the required length) for the location name.
                        endIndex = beginIndex + locationNameElementDetail.getLength();

                        // If there is a validation pattern for the LocationNameElement, test that substring
                        // to ensure that it matches.
                        try {
                            // Get the substring first, this will throw an exception if the string is too short
                            // and cause the validation to fail.
                            var substr = locationName.substring(beginIndex, endIndex);

                            if(validationPattern != null) {
                                var pattern = Pattern.compile(validationPattern);
                                var m = pattern.matcher(substr);
                                
                                if(!m.matches()) {
                                    validLocationName = false;
                                }
                            }
                        } catch (IndexOutOfBoundsException ioobe) {
                            validLocationName = false;
                        }
                    }

                    var x = Sets.newConcurrentHashSet();

                    // Ensure the location name is of the appropriate length based on the final LocationNameElement.
                    if(locationName.length() > endIndex)
                        validLocationName = false;
                    
                    if(validLocationName) {
                        var locationUseTypeName = form.getLocationUseTypeName();
                        var locationUseType = LocationUseTypeLogic.getInstance().getLocationUseTypeByName(this, locationUseTypeName, null, false);
                        
                        if(!hasExecutionErrors()) {
                            boolean multipleUseError = false;
                            
                            if(!locationUseType.getAllowMultiple()) {
                                if(warehouseControl.countLocationsByLocationUseType(warehouseParty, locationUseType) != 0)
                                    multipleUseError = true;
                            }
                            
                            if(!multipleUseError) {
                                var inventoryControl = Session.getModelController(InventoryControl.class);
                                var inventoryLocationGroupName = form.getInventoryLocationGroupName();
                                var inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName);
                                
                                if(inventoryLocationGroup != null) {
                                    var coreControl = getCoreControl();
                                    var velocity = Integer.valueOf(form.getVelocity());
                                    var workflowControl = Session.getModelController(WorkflowControl.class);
                                    var createdBy = getPartyPK();
                                    var description = form.getDescription();
                                    
                                    location = warehouseControl.createLocation(warehouseParty, locationName, locationType, locationUseType,
                                            velocity, inventoryLocationGroup, createdBy);

                                    var entityInstance = coreControl.getEntityInstanceByBasePK(inventoryLocationGroup.getPrimaryKey());
                                    var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(InventoryLocationGroupStatusConstants.Workflow_INVENTORY_LOCATION_GROUP_STATUS, entityInstance);
                                    var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
                                    var workflowEntranceName = switch(workflowStepName) {
                                        case InventoryLocationGroupStatusConstants.WorkflowStep_ACTIVE ->
                                                LocationStatusConstants.WorkflowEntrance_NEW_LOCATION_ACTIVE;
                                        case InventoryLocationGroupStatusConstants.WorkflowStep_INVENTORY_PREP ->
                                                LocationStatusConstants.WorkflowEntrance_NEW_LOCATION_INVENTORY_PREP;
                                        case InventoryLocationGroupStatusConstants.WorkflowStep_INVENTORY ->
                                                LocationStatusConstants.WorkflowEntrance_NEW_LOCATION_INVENTORY;
                                        default -> null;
                                    };

                                    entityInstance = coreControl.getEntityInstanceByBasePK(location.getPrimaryKey());
                                    workflowControl.addEntityToWorkflowUsingNames(null, LocationStatusConstants.Workflow_LOCATION_STATUS, workflowEntranceName, entityInstance, null, null, createdBy);
                                    
                                    if(description != null) {
                                        warehouseControl.createLocationDescription(location, getPreferredLanguage(), description, createdBy);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.MultipleLocationUseTypesNotAllowed.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidLocationName.name(), locationName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), locationTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateLocationName.name(), locationName);
            }
            
            if(location != null) {
                result.setEntityRef(location.getPrimaryKey().getEntityRef());
                result.setLocationName(location.getLastDetail().getLocationName());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
