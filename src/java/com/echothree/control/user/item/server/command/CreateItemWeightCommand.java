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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemWeightForm;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.item.server.entity.ItemWeight;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
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

public class CreateItemWeightCommand
        extends BaseSimpleCommand<CreateItemWeightForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Weight", FieldType.UNSIGNED_LONG, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemWeightCommand */
    public CreateItemWeightCommand(UserVisitPK userVisitPK, CreateItemWeightForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var uomControl = Session.getModelController(UomControl.class);
            String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(item.getLastDetail().getUnitOfMeasureKind(),
                    unitOfMeasureTypeName);
            
            if(unitOfMeasureType != null) {
                ItemUnitOfMeasureType itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);
                
                if(itemUnitOfMeasureType != null) {
                    ItemWeight itemWeight = itemControl.getItemWeight(item, unitOfMeasureType);
                    
                    if(itemWeight == null) {
                        UnitOfMeasureKind weightUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_WEIGHT);
                        String weightUnitOfMeasureTypeName = form.getWeightUnitOfMeasureTypeName();
                        UnitOfMeasureType weightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(weightUnitOfMeasureKind,
                                weightUnitOfMeasureTypeName);
                        
                        if(weightUnitOfMeasureType != null) {
                            Long weight = Long.valueOf(form.getWeight());
                            
                            if(weight > 0) {
                                Conversion conversion = new Conversion(uomControl, weightUnitOfMeasureType, weight);
                                
                                conversion.convertToLowestUnitOfMeasureType();
                                itemControl.createItemWeight(item, unitOfMeasureType, conversion.getQuantity(), getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.InvalidWeight.name(), weight);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureTypeName.name(), weightUnitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateItemWeight.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemUnitOfMeasureType.name(), itemName, unitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return null;
    }
    
}
