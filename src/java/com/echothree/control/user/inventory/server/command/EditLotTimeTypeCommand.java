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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.LotTimeTypeEdit;
import com.echothree.control.user.inventory.common.form.EditLotTimeTypeForm;
import com.echothree.control.user.inventory.common.result.EditLotTimeTypeResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.LotTimeTypeSpec;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.LotTimeType;
import com.echothree.model.data.inventory.server.entity.LotTimeTypeDescription;
import com.echothree.model.data.inventory.server.entity.LotTimeTypeDetail;
import com.echothree.model.data.inventory.server.entity.LotType;
import com.echothree.model.data.inventory.server.value.LotTimeTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.LotTimeTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditLotTimeTypeCommand
        extends BaseAbstractEditCommand<LotTimeTypeSpec, LotTimeTypeEdit, EditLotTimeTypeResult, LotTimeType, LotTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.LotTimeType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LotTimeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditLotTimeTypeCommand */
    public EditLotTimeTypeCommand(UserVisitPK userVisitPK, EditLotTimeTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLotTimeTypeResult getResult() {
        return InventoryResultFactory.getEditLotTimeTypeResult();
    }

    @Override
    public LotTimeTypeEdit getEdit() {
        return InventoryEditFactory.getLotTimeTypeEdit();
    }

    @Override
    public LotTimeType getEntity(EditLotTimeTypeResult result) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        LotTimeType lotTimeType = null;
        String lotTypeName = spec.getLotTypeName();
        LotType lotType = inventoryControl.getLotTypeByName(lotTypeName);

        if(lotType != null) {
            String lotTimeTypeName = spec.getLotTimeTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                lotTimeType = inventoryControl.getLotTimeTypeByName(lotType, lotTimeTypeName);
            } else { // EditMode.UPDATE
                lotTimeType = inventoryControl.getLotTimeTypeByNameForUpdate(lotType, lotTimeTypeName);
            }

            if(lotTimeType != null) {
                result.setLotTimeType(inventoryControl.getLotTimeTypeTransfer(getUserVisit(), lotTimeType));
            } else {
                addExecutionError(ExecutionErrors.UnknownLotTimeTypeName.name(), lotTypeName, lotTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLotTypeName.name(), lotTypeName);
        }

        return lotTimeType;
    }

    @Override
    public LotTimeType getLockEntity(LotTimeType lotTimeType) {
        return lotTimeType;
    }

    @Override
    public void fillInResult(EditLotTimeTypeResult result, LotTimeType lotTimeType) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);

        result.setLotTimeType(inventoryControl.getLotTimeTypeTransfer(getUserVisit(), lotTimeType));
    }

    @Override
    public void doLock(LotTimeTypeEdit edit, LotTimeType lotTimeType) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        LotTimeTypeDescription lotTimeTypeDescription = inventoryControl.getLotTimeTypeDescription(lotTimeType, getPreferredLanguage());
        LotTimeTypeDetail lotTimeTypeDetail = lotTimeType.getLastDetail();

        edit.setLotTimeTypeName(lotTimeTypeDetail.getLotTimeTypeName());
        edit.setIsDefault(lotTimeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(lotTimeTypeDetail.getSortOrder().toString());

        if(lotTimeTypeDescription != null) {
            edit.setDescription(lotTimeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LotTimeType lotTimeType) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        String lotTypeName = spec.getLotTypeName();
        LotType lotType = inventoryControl.getLotTypeByName(lotTypeName);

        if(lotType != null) {
            String lotTimeTypeName = edit.getLotTimeTypeName();
            LotTimeType duplicateLotTimeType = inventoryControl.getLotTimeTypeByName(lotType, lotTimeTypeName);

            if(duplicateLotTimeType != null && !lotTimeType.equals(duplicateLotTimeType)) {
                addExecutionError(ExecutionErrors.DuplicateLotTimeTypeName.name(), lotTypeName, lotTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLotTypeName.name(), lotTypeName);
        }
    }

    @Override
    public void doUpdate(LotTimeType lotTimeType) {
        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        PartyPK partyPK = getPartyPK();
        LotTimeTypeDetailValue lotTimeTypeDetailValue = inventoryControl.getLotTimeTypeDetailValueForUpdate(lotTimeType);
        LotTimeTypeDescription lotTimeTypeDescription = inventoryControl.getLotTimeTypeDescriptionForUpdate(lotTimeType, getPreferredLanguage());
        String description = edit.getDescription();

        lotTimeTypeDetailValue.setLotTimeTypeName(edit.getLotTimeTypeName());
        lotTimeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        lotTimeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        inventoryControl.updateLotTimeTypeFromValue(lotTimeTypeDetailValue, partyPK);

        if(lotTimeTypeDescription == null && description != null) {
            inventoryControl.createLotTimeTypeDescription(lotTimeType, getPreferredLanguage(), description, partyPK);
        } else {
            if(lotTimeTypeDescription != null && description == null) {
                inventoryControl.deleteLotTimeTypeDescription(lotTimeTypeDescription, partyPK);
            } else {
                if(lotTimeTypeDescription != null && description != null) {
                    LotTimeTypeDescriptionValue lotTimeTypeDescriptionValue = inventoryControl.getLotTimeTypeDescriptionValue(lotTimeTypeDescription);

                    lotTimeTypeDescriptionValue.setDescription(description);
                    inventoryControl.updateLotTimeTypeDescriptionFromValue(lotTimeTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
