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

import com.echothree.control.user.item.common.edit.ItemAliasEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditItemAliasForm;
import com.echothree.control.user.item.common.result.EditItemAliasResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemAliasSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemAliasChecksumTypeLogic;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemAlias;
import com.echothree.model.data.item.server.entity.ItemAliasType;
import com.echothree.model.data.item.server.entity.ItemAliasTypeDetail;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.value.ItemAliasValue;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.value.VendorItemDetailValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditItemAliasCommand
        extends BaseAbstractEditCommand<ItemAliasSpec, ItemAliasEdit, EditItemAliasResult, ItemAlias, Item> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemAliasCommand */
    public EditItemAliasCommand(UserVisitPK userVisitPK, EditItemAliasForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemAliasResult getResult() {
        return ItemResultFactory.getEditItemAliasResult();
    }

    @Override
    public ItemAliasEdit getEdit() {
        return ItemEditFactory.getItemAliasEdit();
    }

    @Override
    public ItemAlias getEntity(EditItemAliasResult result) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String alias = spec.getAlias();
        ItemAlias itemAlias = itemControl.getItemAliasByAliasForUpdate(alias);

        if(itemAlias == null) {
            addExecutionError(ExecutionErrors.UnknownItemAlias.name(), alias);
        }

        return itemAlias;
    }

    @Override
    public Item getLockEntity(ItemAlias itemAlias) {
        return itemAlias.getItem();
    }

    @Override
    public void fillInResult(EditItemAliasResult result, ItemAlias itemAlias) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        result.setItemAlias(itemControl.getItemAliasTransfer(getUserVisit(), itemAlias));
    }

    @Override
    public void doLock(ItemAliasEdit edit, ItemAlias itemAlias) {
        edit.setUnitOfMeasureTypeName(itemAlias.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        edit.setItemAliasTypeName(itemAlias.getItemAliasType().getLastDetail().getItemAliasTypeName());
        edit.setAlias(itemAlias.getAlias());
    }

    UnitOfMeasureType unitOfMeasureType;
    ItemAliasType itemAliasType;

    @Override
    public void canUpdate(ItemAlias itemAlias) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String alias = edit.getAlias();
        Item duplicateItem = itemControl.getItemByName(alias);

        if(duplicateItem == null) {
            ItemAlias duplicateItemAlias = itemControl.getItemAliasByAlias(alias);

            if(duplicateItemAlias == null || duplicateItemAlias.getPrimaryKey().equals(itemAlias.getPrimaryKey())) {
                var uomControl = (UomControl)Session.getModelController(UomControl.class);
                String unitOfMeasureTypeName = edit.getUnitOfMeasureTypeName();
                ItemDetail itemDetail = itemAlias.getItem().getLastDetail();
                UnitOfMeasureKind unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();

                unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

                if(unitOfMeasureType != null) {
                    String itemAliasTypeName = edit.getItemAliasTypeName();

                    itemAliasType = itemControl.getItemAliasTypeByName(itemAliasTypeName);

                    if(itemAliasType != null) {
                        ItemAliasTypeDetail itemAliasTypeDetail = itemAliasType.getLastDetail();
                        String validationPattern = itemAliasTypeDetail.getValidationPattern();

                        if(validationPattern != null) {
                            Pattern pattern = Pattern.compile(validationPattern);
                            Matcher m = pattern.matcher(alias);

                            if(!m.matches()) {
                                addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                            }
                        }

                        if(!hasExecutionErrors()) {
                            ItemAliasChecksumTypeLogic.getInstance().checkItemAliasChecksum(this, itemAliasType, alias);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemAliasType.name(), itemAliasTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateItemAlias.name(), itemAlias.getItem().getLastDetail().getItemName(),
                        itemAlias.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName(), itemAlias.getAlias());
            }
        } else {
            addExecutionError(ExecutionErrors.AliasDuplicatesItemName.name(), duplicateItem.getLastDetail().getItemName());
        }
    }

    @Override
    public void doUpdate(ItemAlias itemAlias) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemAliasValue itemAliasValue = itemControl.getItemAliasValue(itemAlias);
        String alias = edit.getAlias();
        String originalAlias = itemAliasValue.getAlias();
        BasePK updatedBy = getPartyPK();

        itemAliasValue.setUnitOfMeasureTypePK(unitOfMeasureType.getPrimaryKey());
        itemAliasValue.setItemAliasTypePK(itemAliasType.getPrimaryKey());
        itemAliasValue.setAlias(alias);

        itemControl.updateItemAliasFromValue(itemAliasValue, updatedBy);

        if(itemAliasValue.getAliasHasBeenModified()) {
            var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
            List<Vendor> vendors = vendorControl.getVendorsByDefaultItemAliasType(itemAliasType);

            for(Vendor vendor : vendors) {
                Party vendorParty = vendor.getParty();
                VendorItem vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemNameForUpdate(vendorParty, originalAlias);

                if(vendorItem != null) {
                    try {
                        if(lockEntity(vendorItem) && lockEntityForUpdate(vendorItem)) {
                            VendorItem duplicateVendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorParty, alias);

                            if(duplicateVendorItem == null) {
                                VendorItemDetailValue vendorItemDetailValue = vendorControl.getVendorItemDetailValueForUpdate(vendorItem);

                                vendorItemDetailValue.setVendorItemName(alias);

                                vendorControl.updateVendorItemFromValue(vendorItemDetailValue, updatedBy);
                            }
                        }
                    } finally {
                        unlockEntity(vendorItem);
                    }
                }
            }
        }
    }

}
