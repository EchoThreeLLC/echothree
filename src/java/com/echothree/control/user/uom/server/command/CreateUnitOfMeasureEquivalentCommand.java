// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.common.form.CreateUnitOfMeasureEquivalentForm;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateUnitOfMeasureEquivalentCommand
        extends BaseSimpleCommand<CreateUnitOfMeasureEquivalentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FromUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ToUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ToQuantity", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateUnitOfMeasureEquivalentCommand */
    public CreateUnitOfMeasureEquivalentCommand(UserVisitPK userVisitPK, CreateUnitOfMeasureEquivalentForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            var fromUnitOfMeasureTypeName = form.getFromUnitOfMeasureTypeName();
            var fromUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind,
                    fromUnitOfMeasureTypeName);
            
            if(fromUnitOfMeasureType != null) {
                var toUnitOfMeasureTypeName = form.getToUnitOfMeasureTypeName();
                var toUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind,
                        toUnitOfMeasureTypeName);
                
                if(toUnitOfMeasureType != null) {
                    var unitOfMeasureEquivalent = uomControl.getUnitOfMeasureEquivalent(fromUnitOfMeasureType,
                            toUnitOfMeasureType);
                    
                    if(unitOfMeasureEquivalent == null) {
                        var toQuantity = Long.valueOf(form.getToQuantity());
                        
                        uomControl.createUnitOfMeasureEquivalent(fromUnitOfMeasureType, toUnitOfMeasureType, toQuantity, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureEquivalent.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownToUnitOfMeasureTypeName.name(), toUnitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFromUnitOfMeasureTypeName.name(), fromUnitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return null;
    }
    
}
