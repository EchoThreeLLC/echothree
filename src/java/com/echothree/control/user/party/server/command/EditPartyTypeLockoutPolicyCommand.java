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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.PartyTypeLockoutPolicyEdit;
import com.echothree.control.user.party.common.form.EditPartyTypeLockoutPolicyForm;
import com.echothree.control.user.party.common.result.EditPartyTypeLockoutPolicyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyTypeSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicy;
import com.echothree.model.data.party.server.entity.PartyTypeLockoutPolicyDetail;
import com.echothree.model.data.party.server.value.PartyTypeLockoutPolicyDetailValue;
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

public class EditPartyTypeLockoutPolicyCommand
        extends BaseEditCommand<PartyTypeSpec, PartyTypeLockoutPolicyEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LockoutFailureCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("ResetFailureCountTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ResetFailureCountTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ManualLockoutReset", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("LockoutInactiveTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("LockoutInactiveTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyTypeLockoutPolicyCommand */
    public EditPartyTypeLockoutPolicyCommand(UserVisitPK userVisitPK, EditPartyTypeLockoutPolicyForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        EditPartyTypeLockoutPolicyResult result = PartyResultFactory.getEditPartyTypeLockoutPolicyResult();
        String partyTypeName = spec.getPartyTypeName();
        PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);
        
        if(partyType != null) {
            PartyTypeLockoutPolicy partyTypeLockoutPolicy = partyControl.getPartyTypeLockoutPolicy(partyType);
            
            if(partyTypeLockoutPolicy != null) {
                var uomControl = (UomControl)Session.getModelController(UomControl.class);
                UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                
                if(timeUnitOfMeasureKind != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyType));
                        
                        if(lockEntity(partyTypeLockoutPolicy)) {
                            PartyTypeLockoutPolicyEdit edit = PartyEditFactory.getPartyTypeLockoutPolicyEdit();
                            PartyTypeLockoutPolicyDetail partyTypeLockoutPolicyDetail = partyTypeLockoutPolicy.getLastDetail();
                            Integer lockoutFailureCount = partyTypeLockoutPolicyDetail.getLockoutFailureCount();
                            Long resetFailureCountTime = partyTypeLockoutPolicyDetail.getResetFailureCountTime();
                            Conversion resetFailureCountTimeConversion = resetFailureCountTime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, resetFailureCountTime).convertToHighestUnitOfMeasureType();
                            Boolean manualLockoutReset = partyTypeLockoutPolicyDetail.getManualLockoutReset();
                            Long lockoutInactiveTime = partyTypeLockoutPolicyDetail.getLockoutInactiveTime();
                            Conversion lockoutInactiveTimeConversion = lockoutInactiveTime == null? null: new Conversion(uomControl, timeUnitOfMeasureKind, lockoutInactiveTime).convertToHighestUnitOfMeasureType();
                            
                            result.setEdit(edit);
                            edit.setLockoutFailureCount(lockoutFailureCount == null? null: lockoutFailureCount.toString());
                            if(resetFailureCountTimeConversion != null) {
                                edit.setResetFailureCountTime(resetFailureCountTimeConversion.getQuantity().toString());
                                edit.setResetFailureCountTimeUnitOfMeasureTypeName(resetFailureCountTimeConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
                            }
                            edit.setManualLockoutReset(manualLockoutReset == null? null: manualLockoutReset.toString());
                            if(lockoutInactiveTimeConversion != null) {
                                edit.setLockoutInactiveTime(lockoutInactiveTimeConversion.getQuantity().toString());
                                edit.setLockoutInactiveTimeUnitOfMeasureTypeName(lockoutInactiveTimeConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(partyTypeLockoutPolicy));
                    } else if(editMode.equals(EditMode.ABANDON)) {
                        unlockEntity(partyTypeLockoutPolicy);
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        PartyTypeLockoutPolicyDetailValue partyTypeLockoutPolicyDetailValue = partyControl.getPartyTypeLockoutPolicyDetailValueForUpdate(partyTypeLockoutPolicy);
                        
                        if(partyTypeLockoutPolicyDetailValue != null) {
                            String rawResetFailureCountTime = edit.getResetFailureCountTime();
                            String resetFailureCountTimeUnitOfMeasureTypeName = edit.getResetFailureCountTimeUnitOfMeasureTypeName();
                            int resetFailureCountTimeParameterCount = (rawResetFailureCountTime == null? 0: 1) + (resetFailureCountTimeUnitOfMeasureTypeName == null? 0: 1);
                            String rawLockoutInactiveTime = edit.getLockoutInactiveTime();
                            String lockoutInactiveTimeUnitOfMeasureTypeName = edit.getLockoutInactiveTimeUnitOfMeasureTypeName();
                            int lockoutInactiveTimeParameterCount = (rawLockoutInactiveTime == null? 0: 1) + (lockoutInactiveTimeUnitOfMeasureTypeName == null? 0: 1);
                            
                            if((resetFailureCountTimeParameterCount == 0 || resetFailureCountTimeParameterCount == 2) &&
                                    (lockoutInactiveTimeParameterCount == 0 || lockoutInactiveTimeParameterCount == 2)) {
                                UnitOfMeasureType resetFailureCountTimeUnitOfMeasureType = resetFailureCountTimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                        resetFailureCountTimeUnitOfMeasureTypeName);
                                
                                if(resetFailureCountTimeUnitOfMeasureTypeName == null || resetFailureCountTimeUnitOfMeasureType != null) {
                                    UnitOfMeasureType lockoutInactiveTimeUnitOfMeasureType = lockoutInactiveTimeUnitOfMeasureTypeName == null? null: uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                            lockoutInactiveTimeUnitOfMeasureTypeName);
                                    
                                    if(lockoutInactiveTimeUnitOfMeasureTypeName == null || lockoutInactiveTimeUnitOfMeasureType != null) {
                                        if(lockEntityForUpdate(partyTypeLockoutPolicy)) {
                                            try {
                                                String rawLockoutFailureCount = edit.getLockoutFailureCount();
                                                Integer lockoutFailureCount = rawLockoutFailureCount == null? null: Integer.valueOf(rawLockoutFailureCount);
                                                Long resetFailureCountTime = rawResetFailureCountTime == null? null: Long.valueOf(rawResetFailureCountTime);
                                                Conversion resetFailureCountTimeConversion = resetFailureCountTime == null? null: new Conversion(uomControl, resetFailureCountTimeUnitOfMeasureType, resetFailureCountTime).convertToLowestUnitOfMeasureType();
                                                String rawManualLockoutReset = edit.getManualLockoutReset();
                                                Boolean manualLockoutReset = rawManualLockoutReset == null? null: Boolean.valueOf(rawManualLockoutReset);
                                                Long lockoutInactiveTime = rawLockoutInactiveTime == null? null: Long.valueOf(rawLockoutInactiveTime);
                                                Conversion lockoutInactiveTimeConversion = lockoutInactiveTime == null? null: new Conversion(uomControl, lockoutInactiveTimeUnitOfMeasureType, lockoutInactiveTime).convertToLowestUnitOfMeasureType();
                                                
                                                partyTypeLockoutPolicyDetailValue.setLockoutFailureCount(lockoutFailureCount);
                                                partyTypeLockoutPolicyDetailValue.setResetFailureCountTime(resetFailureCountTimeConversion == null? null: resetFailureCountTimeConversion.getQuantity());
                                                partyTypeLockoutPolicyDetailValue.setManualLockoutReset(manualLockoutReset);
                                                partyTypeLockoutPolicyDetailValue.setLockoutInactiveTime(lockoutInactiveTimeConversion == null? null: lockoutInactiveTimeConversion.getQuantity());
                                                
                                                partyControl.updatePartyTypeLockoutPolicyFromValue(partyTypeLockoutPolicyDetailValue, getPartyPK());
                                            } finally {
                                                unlockEntity(partyTypeLockoutPolicy);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownLockoutInactiveTimeUnitOfMeasureTypeName.name(), lockoutInactiveTimeUnitOfMeasureTypeName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownResetFailureCountTimeUnitOfMeasureTypeName.name(), resetFailureCountTimeUnitOfMeasureTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                            }
                            
                            if(hasExecutionErrors()) {
                                result.setPartyType(partyControl.getPartyTypeTransfer(getUserVisit(), partyType));
                                result.setEntityLock(getEntityLockTransfer(partyTypeLockoutPolicy));
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownPartyTypeLockoutPolicy.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTimeUnitOfMeasureKind.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyTypeLockoutPolicy.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }
        
        return result;
    }
    
}
