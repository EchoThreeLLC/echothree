// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.cancellationpolicy.server.command;

import com.echothree.control.user.cancellationpolicy.common.form.GetCancellationReasonTypesForm;
import com.echothree.control.user.cancellationpolicy.common.result.CancellationPolicyResultFactory;
import com.echothree.control.user.cancellationpolicy.common.result.GetCancellationReasonTypesResult;
import com.echothree.model.control.cancellationpolicy.server.CancellationPolicyControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationKind;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationReason;
import com.echothree.model.data.cancellationpolicy.server.entity.CancellationType;
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

public class GetCancellationReasonTypesCommand
        extends BaseSimpleCommand<GetCancellationReasonTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.CancellationReasonType.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CancellationKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CancellationReasonName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CancellationTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetCancellationReasonTypesCommand */
    public GetCancellationReasonTypesCommand(UserVisitPK userVisitPK, GetCancellationReasonTypesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        CancellationPolicyControl cancellationPolicyControl = (CancellationPolicyControl)Session.getModelController(CancellationPolicyControl.class);
        GetCancellationReasonTypesResult result = CancellationPolicyResultFactory.getGetCancellationReasonTypesResult();
        String cancellationReasonName = form.getCancellationReasonName();
        String cancellationTypeName = form.getCancellationTypeName();
        int parameterCount = (cancellationReasonName != null? 1: 0) + (cancellationTypeName != null? 1: 0);
        
        if(parameterCount == 1) {
            String cancellationKindName = form.getCancellationKindName();
            CancellationKind cancellationKind = cancellationPolicyControl.getCancellationKindByName(cancellationKindName);
            
            if(cancellationKind != null) {
                if(cancellationReasonName != null) {
                    CancellationReason cancellationReason = cancellationPolicyControl.getCancellationReasonByName(cancellationKind, cancellationReasonName);
                    
                    if(cancellationReason != null) {
                        result.setCancellationReason(cancellationPolicyControl.getCancellationReasonTransfer(getUserVisit(), cancellationReason));
                        result.setCancellationReasonTypes(cancellationPolicyControl.getCancellationReasonTypeTransfersByCancellationReason(getUserVisit(), cancellationReason));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCancellationReasonName.name(), cancellationReasonName);
                    }
                } else if(cancellationTypeName != null) {
                    CancellationType cancellationType = cancellationPolicyControl.getCancellationTypeByName(cancellationKind, cancellationTypeName);
                    
                    if(cancellationType != null) {
                        result.setCancellationType(cancellationPolicyControl.getCancellationTypeTransfer(getUserVisit(), cancellationType));
                        result.setCancellationReasonTypes(cancellationPolicyControl.getCancellationReasonTypeTransfersByCancellationType(getUserVisit(), cancellationType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCancellationTypeName.name(), cancellationTypeName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCancellationKindName.name(), cancellationKindName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
