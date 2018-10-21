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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.remote.edit.ComponentVendorEdit;
import com.echothree.control.user.core.remote.edit.CoreEditFactory;
import com.echothree.control.user.core.remote.form.EditComponentVendorForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.EditComponentVendorResult;
import com.echothree.control.user.core.remote.spec.ComponentVendorSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVendorDetail;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditComponentVendorCommand
        extends BaseAbstractEditCommand<ComponentVendorSpec, ComponentVendorEdit, EditComponentVendorResult, ComponentVendor, ComponentVendor> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ComponentVendor.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditComponentVendorCommand */
    public EditComponentVendorCommand(UserVisitPK userVisitPK, EditComponentVendorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditComponentVendorResult getResult() {
        return CoreResultFactory.getEditComponentVendorResult();
    }

    @Override
    public ComponentVendorEdit getEdit() {
        return CoreEditFactory.getComponentVendorEdit();
    }

    @Override
    public ComponentVendor getEntity(EditComponentVendorResult result) {
        CoreControl coreControl = getCoreControl();
        ComponentVendor componentVendor = null;
        String componentVendorName = spec.getComponentVendorName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        } else { // EditMode.UPDATE
            componentVendor = coreControl.getComponentVendorByNameForUpdate(componentVendorName);
        }

        if(componentVendor != null) {
            result.setComponentVendor(coreControl.getComponentVendorTransfer(getUserVisit(), componentVendor));
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }

        return componentVendor;
    }

    @Override
    public ComponentVendor getLockEntity(ComponentVendor componentVendor) {
        return componentVendor;
    }

    @Override
    public void fillInResult(EditComponentVendorResult result, ComponentVendor componentVendor) {
        CoreControl coreControl = getCoreControl();

        result.setComponentVendor(coreControl.getComponentVendorTransfer(getUserVisit(), componentVendor));
    }

    @Override
    public void doLock(ComponentVendorEdit edit, ComponentVendor componentVendor) {
        ComponentVendorDetail componentVendorDetail = componentVendor.getLastDetail();

        edit.setComponentVendorName(componentVendorDetail.getComponentVendorName());
        edit.setDescription(componentVendorDetail.getDescription());
    }

    @Override
    public void canUpdate(ComponentVendor componentVendor) {
        CoreControl coreControl = getCoreControl();
        String componentVendorName = edit.getComponentVendorName();
        ComponentVendor duplicateComponentVendor = coreControl.getComponentVendorByName(componentVendorName);

        if(duplicateComponentVendor != null && !componentVendor.equals(duplicateComponentVendor)) {
            addExecutionError(ExecutionErrors.DuplicateComponentVendorName.name(), componentVendorName);
        }
    }

    @Override
    public void doUpdate(ComponentVendor componentVendor) {
        CoreControl coreControl = getCoreControl();
        ComponentVendorDetailValue componentVendorDetailValue = coreControl.getComponentVendorDetailValueForUpdate(componentVendor);

        componentVendorDetailValue.setComponentVendorName(edit.getComponentVendorName());
        componentVendorDetailValue.setDescription(edit.getDescription());

        coreControl.updateComponentVendorFromValue(componentVendorDetailValue, getPartyPK());

    }
    
}
