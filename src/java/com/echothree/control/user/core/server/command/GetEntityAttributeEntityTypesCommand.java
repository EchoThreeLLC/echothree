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

import com.echothree.control.user.core.remote.form.GetEntityAttributeEntityTypesForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.GetEntityAttributeEntityTypesResult;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetEntityAttributeEntityTypesCommand
        extends BaseSimpleCommand<GetEntityAttributeEntityTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EntityAttributeEntityType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowedComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowedEntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetEntityAttributeEntityTypesCommand */
    public GetEntityAttributeEntityTypesCommand(UserVisitPK userVisitPK, GetEntityAttributeEntityTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetEntityAttributeEntityTypesResult result = CoreResultFactory.getGetEntityAttributeEntityTypesResult();
        String componentVendorName = form.getComponentVendorName();
        String entityTypeName = form.getEntityTypeName();
        String entityAttributeName = form.getEntityAttributeName();
        String allowedComponentVendorName = form.getAllowedComponentVendorName();
        String allowedEntityTypeName = form.getAllowedEntityTypeName();
        int parameterCount = (componentVendorName == null && entityTypeName == null && entityAttributeName == null && allowedComponentVendorName != null && allowedEntityTypeName != null ? 0 : 1)
                + (componentVendorName != null && entityTypeName != null && entityAttributeName != null && allowedComponentVendorName == null && allowedEntityTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            CoreControl coreControl = getCoreControl();

            if(componentVendorName != null) {
                ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);

                if(componentVendor != null) {
                    EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);

                    if(entityType != null) {
                        EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

                        if(entityAttribute != null) {
                            result.setEntityAttribute(coreControl.getEntityAttributeTransfer(getUserVisit(), entityAttribute, null));
                            result.setEntityAttributeEntityTypes(coreControl.getEntityAttributeEntityTypeTransfersByEntityAttribute(getUserVisit(), entityAttribute, null));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownEntityAttributeName.name(), entityAttributeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
                }
            } else {
                ComponentVendor allowedComponentVendor = coreControl.getComponentVendorByName(allowedComponentVendorName);

                if(allowedComponentVendor != null) {
                    EntityType allowedEntityType = coreControl.getEntityTypeByName(allowedComponentVendor, allowedEntityTypeName);

                    if(allowedEntityType != null) {
                        result.setEntityType(coreControl.getEntityTypeTransfer(getUserVisit(), allowedEntityType));
                        result.setEntityAttributeEntityTypes(coreControl.getEntityAttributeEntityTypeTransfersByAllowedEntityType(getUserVisit(), allowedEntityType, null));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownAllowedEntityTypeName.name(), allowedComponentVendorName, allowedEntityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownAllowedComponentVendorName.name(), allowedComponentVendorName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
