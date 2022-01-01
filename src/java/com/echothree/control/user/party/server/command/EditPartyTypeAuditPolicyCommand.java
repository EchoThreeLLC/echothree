// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.PartyTypeAuditPolicyEdit;
import com.echothree.control.user.party.common.form.EditPartyTypeAuditPolicyForm;
import com.echothree.control.user.party.common.result.EditPartyTypeAuditPolicyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyTypeSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeAuditPolicyDetail;
import com.echothree.model.data.party.server.value.PartyTypeAuditPolicyDetailValue;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditPartyTypeAuditPolicyCommand
        extends BaseEditCommand<PartyTypeSpec, PartyTypeAuditPolicyEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AuditCommands", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("RetainUserVisitsTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("RetainUserVisitsTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyTypeAuditPolicyCommand */
    public EditPartyTypeAuditPolicyCommand(UserVisitPK userVisitPK, EditPartyTypeAuditPolicyForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        EditPartyTypeAuditPolicyResult result = PartyResultFactory.getEditPartyTypeAuditPolicyResult();
        String partyTypeName = spec.getPartyTypeName();
        PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType != null) {
            PartyTypeAuditPolicy partyTypeAuditPolicy = partyControl.getPartyTypeAuditPolicy(partyType);

            if(partyTypeAuditPolicy != null) {
                var uomControl = Session.getModelController(UomControl.class);
                UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

                if(timeUnitOfMeasureKind != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyType));

                        if(lockEntity(partyTypeAuditPolicy)) {
                            PartyTypeAuditPolicyEdit edit = PartyEditFactory.getPartyTypeAuditPolicyEdit();
                            PartyTypeAuditPolicyDetail partyTypeAuditPolicyDetail = partyTypeAuditPolicy.getLastDetail();
                            Long retainUserVisitsTime = partyTypeAuditPolicyDetail.getRetainUserVisitsTime();
                            Conversion retainUserVisitsTimeConversion = retainUserVisitsTime == null ? null
                                    : new Conversion(uomControl, timeUnitOfMeasureKind, retainUserVisitsTime).convertToHighestUnitOfMeasureType();

                            result.setEdit(edit);
                            edit.setAuditCommands(partyTypeAuditPolicyDetail.getAuditCommands().toString());
                            if(retainUserVisitsTimeConversion != null) {
                                edit.setRetainUserVisitsTime(retainUserVisitsTimeConversion.getQuantity().toString());
                                edit.setRetainUserVisitsTimeUnitOfMeasureTypeName(retainUserVisitsTimeConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }

                        result.setEntityLock(getEntityLockTransfer(partyTypeAuditPolicy));
                    } else if(editMode.equals(EditMode.ABANDON)) {
                        unlockEntity(partyTypeAuditPolicy);
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        PartyTypeAuditPolicyDetailValue partyTypeAuditPolicyDetailValue = partyControl.getPartyTypeAuditPolicyDetailValueForUpdate(partyTypeAuditPolicy);

                        if(partyTypeAuditPolicyDetailValue != null) {
                            String rawRetainUserVisitsTime = edit.getRetainUserVisitsTime();
                            String retainUserVisitsTimeUnitOfMeasureTypeName = edit.getRetainUserVisitsTimeUnitOfMeasureTypeName();
                            int retainUserVisitsTimeParameterCount = (rawRetainUserVisitsTime == null ? 0 : 1) + (retainUserVisitsTimeUnitOfMeasureTypeName == null ? 0 : 1);

                            if(retainUserVisitsTimeParameterCount == 0 || retainUserVisitsTimeParameterCount == 2) {
                                UnitOfMeasureType retainUserVisitsTimeUnitOfMeasureType = retainUserVisitsTimeUnitOfMeasureTypeName == null ? null
                                        : uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind, retainUserVisitsTimeUnitOfMeasureTypeName);

                                if(retainUserVisitsTimeUnitOfMeasureTypeName == null || retainUserVisitsTimeUnitOfMeasureType != null) {
                                    if(lockEntityForUpdate(partyTypeAuditPolicy)) {
                                        try {
                                            String rawAuditCommands = edit.getAuditCommands();
                                            Boolean auditCommands = rawAuditCommands == null ? null : Boolean.valueOf(rawAuditCommands);
                                            Long retainUserVisitsTime = rawRetainUserVisitsTime == null ? null : Long.valueOf(rawRetainUserVisitsTime);
                                            Conversion retainUserVisitsTimeConversion = retainUserVisitsTime == null ? null
                                                    : new Conversion(uomControl, retainUserVisitsTimeUnitOfMeasureType, retainUserVisitsTime).convertToLowestUnitOfMeasureType();

                                            partyTypeAuditPolicyDetailValue.setAuditCommands(auditCommands);
                                            partyTypeAuditPolicyDetailValue.setRetainUserVisitsTime(retainUserVisitsTimeConversion == null ? null : retainUserVisitsTimeConversion.getQuantity());

                                            partyControl.updatePartyTypeAuditPolicyFromValue(partyTypeAuditPolicyDetailValue, getPartyPK());
                                        } finally {
                                            unlockEntity(partyTypeAuditPolicy);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownRetainUserVisitsTimeUnitOfMeasureTypeName.name(), retainUserVisitsTimeUnitOfMeasureTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                            }

                            if(hasExecutionErrors()) {
                                result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyType));
                                result.setEntityLock(getEntityLockTransfer(partyTypeAuditPolicy));
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownPartyTypeAuditPolicy.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTimeUnitOfMeasureKind.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyTypeAuditPolicy.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }

        return result;
    }
    
}
