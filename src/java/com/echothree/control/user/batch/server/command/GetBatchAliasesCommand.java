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

package com.echothree.control.user.batch.server.command;

import com.echothree.control.user.batch.remote.form.GetBatchAliasesForm;
import com.echothree.control.user.batch.remote.result.BatchResultFactory;
import com.echothree.control.user.batch.remote.result.GetBatchAliasesResult;
import com.echothree.control.user.batch.server.command.util.BatchAliasUtil;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetBatchAliasesCommand
        extends BaseSimpleCommand<GetBatchAliasesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetBatchAliasesCommand */
    public GetBatchAliasesCommand(UserVisitPK userVisitPK, GetBatchAliasesForm form) {
        super(userVisitPK, form, new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(BatchAliasUtil.getInstance().getSecurityRoleGroupNameByBatchTypeSpec(form), SecurityRoles.List.name())
                        )))
                ))), FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        GetBatchAliasesResult result = BatchResultFactory.getGetBatchAliasesResult();
        String batchTypeName = form.getBatchTypeName();
        BatchType batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType != null) {
            String batchName = form.getBatchName();
            Batch batch = batchControl.getBatchByName(batchType, batchName);

            if(batch != null) {
                UserVisit userVisit = getUserVisit();

                result.setBatch(batchControl.getBatchTransfer(userVisit, batch));
                result.setBatchAliases(batchControl.getBatchAliasTransfersByBatch(userVisit, batch));
            } else {
                addExecutionError(ExecutionErrors.UnknownBatchName.name(), batchTypeName, batchName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return result;
    }
    
}
