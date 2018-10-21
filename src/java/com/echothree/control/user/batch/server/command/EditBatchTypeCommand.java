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

import com.echothree.control.user.batch.remote.edit.BatchEditFactory;
import com.echothree.control.user.batch.remote.edit.BatchTypeEdit;
import com.echothree.control.user.batch.remote.form.EditBatchTypeForm;
import com.echothree.control.user.batch.remote.result.BatchResultFactory;
import com.echothree.control.user.batch.remote.result.EditBatchTypeResult;
import com.echothree.control.user.batch.remote.spec.BatchTypeSpec;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.batch.server.entity.BatchTypeDescription;
import com.echothree.model.data.batch.server.entity.BatchTypeDetail;
import com.echothree.model.data.batch.server.value.BatchTypeDescriptionValue;
import com.echothree.model.data.batch.server.value.BatchTypeDetailValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditBatchTypeCommand
        extends BaseAbstractEditCommand<BatchTypeSpec, BatchTypeEdit, EditBatchTypeResult, BatchType, BatchType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.BatchType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentBatchTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditBatchTypeCommand */
    public EditBatchTypeCommand(UserVisitPK userVisitPK, EditBatchTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditBatchTypeResult getResult() {
        return BatchResultFactory.getEditBatchTypeResult();
    }

    @Override
    public BatchTypeEdit getEdit() {
        return BatchEditFactory.getBatchTypeEdit();
    }

    @Override
    public BatchType getEntity(EditBatchTypeResult result) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchType batchType = null;
        String batchTypeName = spec.getBatchTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            batchType = batchControl.getBatchTypeByName(batchTypeName);
        } else { // EditMode.UPDATE
            batchType = batchControl.getBatchTypeByNameForUpdate(batchTypeName);
        }

        if(batchType != null) {
            result.setBatchType(batchControl.getBatchTypeTransfer(getUserVisit(), batchType));
        } else {
            addExecutionError(ExecutionErrors.UnknownBatchTypeName.name(), batchTypeName);
        }

        return batchType;
    }

    @Override
    public BatchType getLockEntity(BatchType batchType) {
        return batchType;
    }

    @Override
    public void fillInResult(EditBatchTypeResult result, BatchType batchType) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);

        result.setBatchType(batchControl.getBatchTypeTransfer(getUserVisit(), batchType));
    }

    BatchType parentBatchType = null;
    SequenceType batchSequenceType = null;
    Workflow batchWorkflow = null;
    WorkflowEntrance batchWorkflowEntrance = null;

    @Override
    public void doLock(BatchTypeEdit edit, BatchType batchType) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        BatchTypeDescription batchTypeDescription = batchControl.getBatchTypeDescription(batchType, getPreferredLanguage());
        BatchTypeDetail batchTypeDetail = batchType.getLastDetail();

        parentBatchType = batchTypeDetail.getParentBatchType();
        batchSequenceType = batchTypeDetail.getBatchSequenceType();
        batchWorkflow = batchTypeDetail.getBatchWorkflow();
        batchWorkflowEntrance = batchTypeDetail.getBatchWorkflowEntrance();

        edit.setBatchTypeName(batchTypeDetail.getBatchTypeName());
        edit.setParentBatchTypeName(parentBatchType == null ? null : parentBatchType.getLastDetail().getBatchTypeName());
        edit.setBatchSequenceTypeName(batchSequenceType == null ? null : batchSequenceType.getLastDetail().getSequenceTypeName());
        edit.setBatchWorkflowName(batchWorkflow == null ? null : batchWorkflow.getLastDetail().getWorkflowName());
        edit.setBatchWorkflowEntranceName(batchWorkflowEntrance == null ? null : batchWorkflowEntrance.getLastDetail().getWorkflowEntranceName());
        edit.setIsDefault(batchTypeDetail.getIsDefault().toString());
        edit.setSortOrder(batchTypeDetail.getSortOrder().toString());

        if(batchTypeDescription != null) {
            edit.setDescription(batchTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(BatchType batchType) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        String batchTypeName = edit.getBatchTypeName();
        BatchType duplicateBatchType = batchControl.getBatchTypeByName(batchTypeName);

        if(duplicateBatchType == null || batchType.equals(duplicateBatchType)) {
            String parentBatchTypeName = edit.getParentBatchTypeName();

            if(parentBatchTypeName != null) {
                parentBatchType = batchControl.getBatchTypeByName(parentBatchTypeName);
            }

            if(parentBatchTypeName == null || parentBatchType != null) {
                if(batchControl.isParentBatchTypeSafe(batchType, parentBatchType)) {
                    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                    String batchSequenceTypeName = edit.getBatchSequenceTypeName();

                    batchSequenceType = sequenceControl.getSequenceTypeByName(batchSequenceTypeName);

                    if(batchSequenceTypeName == null || batchSequenceType != null) {
                        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
                        String batchWorkflowName = edit.getBatchWorkflowName();

                        batchWorkflow = batchWorkflowName == null ? null : workflowControl.getWorkflowByName(batchWorkflowName);

                        if(batchWorkflowName == null || batchWorkflow != null) {
                            String batchWorkflowEntranceName = edit.getBatchWorkflowEntranceName();

                            if(batchWorkflowEntranceName == null || (batchWorkflow != null && batchWorkflowEntranceName != null)) {
                                batchWorkflowEntrance = batchWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(batchWorkflow, batchWorkflowEntranceName);

                                if(batchWorkflowEntranceName != null && batchWorkflowEntrance == null) {
                                    addExecutionError(ExecutionErrors.UnknownBatchWorkflowEntranceName.name(), batchWorkflowName, batchWorkflowEntranceName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.MissingRequiredBatchWorkflowName.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownBatchWorkflowName.name(), batchWorkflowName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownBatchSequenceTypeName.name(), batchSequenceTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParentBatchType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentBatchTypeName.name(), parentBatchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateBatchTypeName.name(), batchTypeName);
        }
    }

    @Override
    public void doUpdate(BatchType batchType) {
        BatchControl batchControl = (BatchControl)Session.getModelController(BatchControl.class);
        PartyPK partyPK = getPartyPK();
        BatchTypeDetailValue batchTypeDetailValue = batchControl.getBatchTypeDetailValueForUpdate(batchType);
        BatchTypeDescription batchTypeDescription = batchControl.getBatchTypeDescriptionForUpdate(batchType, getPreferredLanguage());
        String description = edit.getDescription();

        batchTypeDetailValue.setBatchTypeName(edit.getBatchTypeName());
        batchTypeDetailValue.setParentBatchTypePK(parentBatchType == null ? null : parentBatchType.getPrimaryKey());
        batchTypeDetailValue.setBatchSequenceTypePK(batchSequenceType == null ? null : batchSequenceType.getPrimaryKey());
        batchTypeDetailValue.setBatchWorkflowPK(batchWorkflow == null ? null : batchWorkflow.getPrimaryKey());
        batchTypeDetailValue.setBatchWorkflowEntrancePK(batchWorkflow == null ? null : batchWorkflowEntrance.getPrimaryKey());
        batchTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        batchTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        batchControl.updateBatchTypeFromValue(batchTypeDetailValue, partyPK);

        if(batchTypeDescription == null && description != null) {
            batchControl.createBatchTypeDescription(batchType, getPreferredLanguage(), description, partyPK);
        } else {
            if(batchTypeDescription != null && description == null) {
                batchControl.deleteBatchTypeDescription(batchTypeDescription, partyPK);
            } else {
                if(batchTypeDescription != null && description != null) {
                    BatchTypeDescriptionValue batchTypeDescriptionValue = batchControl.getBatchTypeDescriptionValue(batchTypeDescription);

                    batchTypeDescriptionValue.setDescription(description);
                    batchControl.updateBatchTypeDescriptionFromValue(batchTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
