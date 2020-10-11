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

package com.echothree.control.user.workrequirement.server.command;

import com.echothree.control.user.workrequirement.common.form.GetWorkRequirementScopesForm;
import com.echothree.control.user.workrequirement.common.result.GetWorkRequirementScopesResult;
import com.echothree.control.user.workrequirement.common.result.WorkRequirementResultFactory;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWorkRequirementScopesCommand
        extends BaseSimpleCommand<GetWorkRequirementScopesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WorkRequirementTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WorkEffortScopeName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetWorkRequirementScopesCommand */
    public GetWorkRequirementScopesCommand(UserVisitPK userVisitPK, GetWorkRequirementScopesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);
        GetWorkRequirementScopesResult result = WorkRequirementResultFactory.getGetWorkRequirementScopesResult();
        String workEffortTypeName = form.getWorkEffortTypeName();
        WorkEffortType workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            UserVisit userVisit = getUserVisit();
            String workEffortScopeName = form.getWorkEffortScopeName();
            String workRequirementTypeName = form.getWorkRequirementTypeName();
            int parameterCount = (workEffortScopeName == null? 0: 1) + (workRequirementTypeName == null? 0: 1);
            
            result.setWorkEffortType(workEffortControl.getWorkEffortTypeTransfer(userVisit, workEffortType));
            
            if(parameterCount == 1) {
                var workRequirementControl = (WorkRequirementControl)Session.getModelController(WorkRequirementControl.class);
                
                if(workEffortScopeName != null) {
                    WorkEffortScope workEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, workEffortScopeName);
                    
                    if(workEffortScope != null) {
                        result.setWorkEffortScope(workEffortControl.getWorkEffortScopeTransfer(userVisit, workEffortScope));
                        result.setWorkRequirementScopes(workRequirementControl.getWorkRequirementScopeTransfersByWorkEffortScope(userVisit, workEffortScope));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkEffortScopeName.name(), workEffortScopeName);
                    }
                } else if(workRequirementTypeName != null) {
                    WorkRequirementType workRequirementType = workRequirementControl.getWorkRequirementTypeByName(workEffortType, workRequirementTypeName);
                    
                    if(workRequirementType != null) {
                        result.setWorkRequirementType(workRequirementControl.getWorkRequirementTypeTransfer(userVisit, workRequirementType));
                        result.setWorkRequirementScopes(workRequirementControl.getWorkRequirementScopeTransfersByWorkRequirementType(userVisit, workRequirementType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkRequirementTypeName.name(), workRequirementTypeName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }
        
        return result;
    }
    
}
