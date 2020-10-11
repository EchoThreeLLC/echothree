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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.edit.GeoCodeDateTimeFormatEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeDateTimeFormatForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeDateTimeFormatResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeDateTimeFormatSpec;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeDateTimeFormat;
import com.echothree.model.data.geo.server.value.GeoCodeDateTimeFormatValue;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditGeoCodeDateTimeFormatCommand
        extends BaseEditCommand<GeoCodeDateTimeFormatSpec, GeoCodeDateTimeFormatEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeDateTimeFormat.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DateTimeFormatName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditGeoCodeDateTimeFormatCommand */
    public EditGeoCodeDateTimeFormatCommand(UserVisitPK userVisitPK, EditGeoCodeDateTimeFormatForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        EditGeoCodeDateTimeFormatResult result = GeoResultFactory.getEditGeoCodeDateTimeFormatResult();
        String geoCodeName = spec.getGeoCodeName();
        GeoCode geoCode = geoControl.getGeoCodeByName(geoCodeName);
        
        if(geoCode != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String dateTimeFormatName = spec.getDateTimeFormatName();
            DateTimeFormat dateTimeFormat = partyControl.getDateTimeFormatByName(dateTimeFormatName);
            
            if(dateTimeFormat != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    GeoCodeDateTimeFormat geoCodeDateTimeFormat = geoControl.getGeoCodeDateTimeFormat(geoCode, dateTimeFormat);
                    
                    if(geoCodeDateTimeFormat != null) {
                        result.setGeoCodeDateTimeFormat(geoControl.getGeoCodeDateTimeFormatTransfer(getUserVisit(), geoCodeDateTimeFormat));
                        
                        if(lockEntity(geoCode)) {
                            GeoCodeDateTimeFormatEdit edit = GeoEditFactory.getGeoCodeDateTimeFormatEdit();
                            
                            result.setEdit(edit);
                            edit.setIsDefault(geoCodeDateTimeFormat.getIsDefault().toString());
                            edit.setSortOrder(geoCodeDateTimeFormat.getSortOrder().toString());
                            
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(geoCode));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeDateTimeFormat.name(), geoCodeName, dateTimeFormatName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    GeoCodeDateTimeFormat geoCodeDateTimeFormat = geoControl.getGeoCodeDateTimeFormatForUpdate(geoCode, dateTimeFormat);
                    
                    if(geoCodeDateTimeFormat != null) {
                        GeoCodeDateTimeFormatValue geoCodeDateTimeFormatValue = geoControl.getGeoCodeDateTimeFormatValue(geoCodeDateTimeFormat);
                        
                        if(lockEntityForUpdate(geoCode)) {
                            try {
                                geoCodeDateTimeFormatValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                geoCodeDateTimeFormatValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                
                                geoControl.updateGeoCodeDateTimeFormatFromValue(geoCodeDateTimeFormatValue, getPartyPK());
                            } finally {
                                unlockEntity(geoCode);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeDateTimeFormat.name(), geoCodeName, dateTimeFormatName);
                    }
                    
                    if(hasExecutionErrors()) {
                        result.setGeoCodeDateTimeFormat(geoControl.getGeoCodeDateTimeFormatTransfer(getUserVisit(), geoCodeDateTimeFormat));
                        result.setEntityLock(getEntityLockTransfer(geoCode));
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownDateTimeFormatName.name(), dateTimeFormatName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return result;
    }
    
}
