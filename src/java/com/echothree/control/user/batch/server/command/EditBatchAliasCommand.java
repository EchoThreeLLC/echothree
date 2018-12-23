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

package com.echothree.control.user.batch.server.command;

import com.echothree.control.user.batch.common.edit.BatchAliasEdit;
import com.echothree.control.user.batch.common.edit.BatchEditFactory;
import com.echothree.control.user.batch.common.form.EditBatchAliasForm;
import com.echothree.control.user.batch.common.result.BatchResultFactory;
import com.echothree.control.user.batch.common.result.EditBatchAliasResult;
import com.echothree.control.user.batch.common.spec.BatchAliasSpec;
import com.echothree.control.user.batch.server.command.util.BatchAliasUtil;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchAlias;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.batch.server.entity.BatchAliasTypeDetail;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.batch.server.value.BatchAliasValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditBatchAliasCommand
        extends BaseAbstractEditCommand<BatchAliasSpec, BatchAliasEdit, EditBatchAliasResult, BatchAlias, BatchAlias> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("BatchAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditBatchAliasCommand */
    public EditBatchAliasCommand(UserVisitPK userVisitPK, EditBatchAliasForm form) {
        super(userVisitPK, form, new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(BatchAliasUtil.getInstance().getSecurityRoleGroupNameByBatchTypeSpec(form == null ? null : form.getSpec()), SecurityRoles.Edit.name())
                        )))
                ))), SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditBatchAliasResult getResult() {
        return BatchResultFactory.getEditBatchAliasResult();
    }

    @Override
    public BatchAliasEdit getEdit() {
        return BatchEditFactory.getBatchAliasEdit();
    }

    BatchAliasType batchAliasType;
    
    @Override
    public BatchAlias getEntity(EditBatchAliasResult result) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchAlias batchAlias = null;
        String batchTypeName = spec.getBatchTypeName();
        BatchType batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType != null) {
            String batchName = spec.getBatchName();
            Batch batch = batchControl.getBatchByName(batchType, batchName);

            if(batch != null) {
                String batchAliasTypeName = spec.getBatchAliasTypeName();

                batchAliasType = batchControl.getBatchAliasTypeByName(batchType, batchAliasTypeName);

                if(batchAliasType != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        batchAlias = batchControl.getBatchAlias(batch, batchAliasType);
                    } else { // EditMode.UPDATE
                        batchAlias = batchControl.getBatchAliasForUpdate(batch, batchAliasType);
                    }

                    if(batchAlias != null) {
                        result.setBatchAlias(batchControl.getBatchAliasTransfer(getUserVisit(), batchAlias));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownBatchAlias.name(), batchTypeName, batchName, batchAliasTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownBatchAliasTypeName.name(), batchTypeName, batchAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownBatchName.name(), batchTypeName, batchName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return batchAlias;
    }

    @Override
    public BatchAlias getLockEntity(BatchAlias batchAlias) {
        return batchAlias;
    }

    @Override
    public void fillInResult(EditBatchAliasResult result, BatchAlias batchAlias) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);

        result.setBatchAlias(batchControl.getBatchAliasTransfer(getUserVisit(), batchAlias));
    }

    @Override
    public void doLock(BatchAliasEdit edit, BatchAlias batchAlias) {
        edit.setAlias(batchAlias.getAlias());
    }

    @Override
    public void canUpdate(BatchAlias batchAlias) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        String alias = edit.getAlias();
        BatchAlias duplicateBatchAlias = batchControl.getBatchAliasByAlias(batchAliasType, alias);

        if(duplicateBatchAlias != null && !batchAlias.equals(duplicateBatchAlias)) {
            BatchAliasTypeDetail batchAliasTypeDetail = batchAlias.getBatchAliasType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateBatchAlias.name(), batchAliasTypeDetail.getBatchType().getLastDetail().getBatchTypeName(),
                    batchAliasTypeDetail.getBatchAliasTypeName(), alias);
        }
    }

    @Override
    public void doUpdate(BatchAlias batchAlias) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchAliasValue batchAliasValue = batchControl.getBatchAliasValue(batchAlias);

        batchAliasValue.setAlias(edit.getAlias());

        batchControl.updateBatchAliasFromValue(batchAliasValue, getPartyPK());
    }

}
