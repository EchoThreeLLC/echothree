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

import com.echothree.control.user.warehouse.common.edit.LocationTypeDescriptionEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEditFactory;
import com.echothree.control.user.warehouse.common.form.EditLocationTypeDescriptionForm;
import com.echothree.control.user.warehouse.common.result.EditLocationTypeDescriptionResult;
import com.echothree.control.user.warehouse.common.result.WarehouseResultFactory;
import com.echothree.control.user.warehouse.common.spec.LocationTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.LocationTypeDescription;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.value.LocationTypeDescriptionValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditLocationTypeDescriptionCommand
        extends BaseEditCommand<LocationTypeDescriptionSpec, LocationTypeDescriptionEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LocationType.name(), SecurityRoles.Description.name())
                ))
        ));

        List<FieldDefinition> temp = new ArrayList<>(3);
        temp.add(new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LocationTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditLocationTypeDescriptionCommand */
    public EditLocationTypeDescriptionCommand(UserVisitPK userVisitPK, EditLocationTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        EditLocationTypeDescriptionResult result = WarehouseResultFactory.getEditLocationTypeDescriptionResult();
        String warehouseName = spec.getWarehouseName();
        Warehouse warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            Party warehouseParty = warehouse.getParty();
            String locationTypeName = spec.getLocationTypeName();
            LocationType locationType = warehouseControl.getLocationTypeByName(warehouseParty, locationTypeName);
            
            if(locationType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        LocationTypeDescription locationTypeDescription = warehouseControl.getLocationTypeDescription(locationType, language);
                        
                        if(locationTypeDescription != null) {
                            result.setLocationTypeDescription(warehouseControl.getLocationTypeDescriptionTransfer(getUserVisit(), locationTypeDescription));
                            
                            if(lockEntity(locationType)) {
                                LocationTypeDescriptionEdit edit = WarehouseEditFactory.getLocationTypeDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(locationTypeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(locationType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLocationTypeDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        LocationTypeDescriptionValue locationTypeDescriptionValue = warehouseControl.getLocationTypeDescriptionValueForUpdate(locationType, language);
                        
                        if(locationTypeDescriptionValue != null) {
                            if(lockEntityForUpdate(locationType)) {
                                try {
                                    String description = edit.getDescription();
                                    
                                    locationTypeDescriptionValue.setDescription(description);
                                    
                                    warehouseControl.updateLocationTypeDescriptionFromValue(locationTypeDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(locationType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLocationTypeDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLocationTypeName.name(), locationTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
