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

package com.echothree.control.user.returnpolicy.server.command;

import com.echothree.control.user.returnpolicy.common.form.SetDefaultReturnTypeShippingMethodForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.returnpolicy.server.entity.ReturnKind;
import com.echothree.model.data.returnpolicy.server.entity.ReturnType;
import com.echothree.model.data.returnpolicy.server.value.ReturnTypeShippingMethodValue;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetDefaultReturnTypeShippingMethodCommand
        extends BaseSimpleCommand<SetDefaultReturnTypeShippingMethodForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ReturnTypeShippingMethod.name(), SecurityRoles.Edit.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReturnKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ReturnTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultReturnTypeShippingMethodCommand */
    public SetDefaultReturnTypeShippingMethodCommand(UserVisitPK userVisitPK, SetDefaultReturnTypeShippingMethodForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        String returnKindName = form.getReturnKindName();
        ReturnKind returnKind = returnPolicyControl.getReturnKindByName(returnKindName);
        
        if(returnKind != null) {
            String returnTypeName = form.getReturnTypeName();
            ReturnType returnType = returnPolicyControl.getReturnTypeByName(returnKind, returnTypeName);
            
            if(returnType != null) {
                var shippingControl = (ShippingControl)Session.getModelController(ShippingControl.class);
                String shippingMethodName = form.getShippingMethodName();
                ShippingMethod shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
                
                if(shippingMethod != null) {
                    ReturnTypeShippingMethodValue returnTypeShippingMethodValue = returnPolicyControl.getReturnTypeShippingMethodValueForUpdate(returnType,
                            shippingMethod);
                    
                    if(returnTypeShippingMethodValue != null) {
                        returnTypeShippingMethodValue.setIsDefault(Boolean.TRUE);
                        returnPolicyControl.updateReturnTypeShippingMethodFromValue(returnTypeShippingMethodValue, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownReturnTypeShippingMethod.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownReturnTypeName.name(), returnTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownReturnKindName.name(), returnKindName);
        }
        
        return null;
    }
    
}
