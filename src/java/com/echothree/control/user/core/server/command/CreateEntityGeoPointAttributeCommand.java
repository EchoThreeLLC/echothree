// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CreateEntityGeoPointAttributeForm;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateEntityGeoPointAttributeCommand
        extends BaseSimpleCommand<CreateEntityGeoPointAttributeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUlid", FieldType.ULID, false, null, null),
                new FieldDefinition("Latitude", FieldType.LATITUDE, true, null, null),
                new FieldDefinition("Longitude", FieldType.LONGITUDE, true, null, null),
                new FieldDefinition("Elevation", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ElevationUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Altitude", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("AltitudeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateEntityGeoPointAttributeCommand */
    public CreateEntityGeoPointAttributeCommand(UserVisitPK userVisitPK, CreateEntityGeoPointAttributeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var parameterCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form);

            if(!hasExecutionErrors()) {
                var entityAttributeName = form.getEntityAttributeName();
                var entityAttributeUlid = form.getEntityAttributeUlid();
                
                parameterCount = (entityAttributeName == null ? 0 : 1) + (entityAttributeUlid == null ? 0 : 1);
                
                if(parameterCount == 1) {
                    var entityAttribute = entityAttributeName == null ?
                            EntityAttributeLogic.getInstance().getEntityAttributeByUlid(this, entityAttributeUlid) :
                            EntityAttributeLogic.getInstance().getEntityAttributeByName(this, entityInstance.getEntityType(), entityAttributeName);

                    if(!hasExecutionErrors()) {
                        var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

                        if(EntityAttributeTypes.GEOPOINT.name().equals(entityAttributeTypeName)) {
                            if(entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                                var coreControl = getCoreControl();
                                var entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance);

                                if(entityGeoPointAttribute == null) {
                                    var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
                                    var elevation = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_ELEVATION,
                                            form.getElevation(), form.getElevationUnitOfMeasureTypeName(),
                                            null, ExecutionErrors.MissingRequiredElevation.name(), null, ExecutionErrors.MissingRequiredElevationUnitOfMeasureTypeName.name(),
                                            null, ExecutionErrors.UnknownElevationUnitOfMeasureTypeName.name());

                                    if(!hasExecutionErrors()) {
                                        var altitude = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_ALTITUDE,
                                                form.getAltitude(), form.getAltitudeUnitOfMeasureTypeName(),
                                                null, ExecutionErrors.MissingRequiredAltitude.name(), null, ExecutionErrors.MissingRequiredAltitudeUnitOfMeasureTypeName.name(),
                                                null, ExecutionErrors.UnknownAltitudeUnitOfMeasureTypeName.name());

                                        if(!hasExecutionErrors()) {
                                            var latitude = Integer.valueOf(form.getLatitude());
                                            var longitude = Integer.valueOf(form.getLongitude());

                                            coreControl.createEntityGeoPointAttribute(entityAttribute, entityInstance, latitude, longitude, elevation, altitude, getPartyPK());
                                        }
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.DuplicateEntityGeoPointAttribute.name(),
                                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                            entityAttribute.getLastDetail().getEntityAttributeName());
                                }
                            } else {
                                var expectedEntityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();
                                var suppliedEntityTypeDetail = entityInstance.getEntityType().getLastDetail();
                                
                                addExecutionError(ExecutionErrors.MismatchedEntityType.name(),
                                        expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                        expectedEntityTypeDetail.getEntityTypeName(),
                                        suppliedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                        suppliedEntityTypeDetail.getEntityTypeName());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MismatchedEntityAttributeType.name(),
                                    EntityAttributeTypes.GEOPOINT.name(), entityAttributeTypeName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return null;
    }

}
