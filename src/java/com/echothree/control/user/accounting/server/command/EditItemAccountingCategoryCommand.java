// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.edit.AccountingEditFactory;
import com.echothree.control.user.accounting.common.edit.ItemAccountingCategoryEdit;
import com.echothree.control.user.accounting.common.form.EditItemAccountingCategoryForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.EditItemAccountingCategoryResult;
import com.echothree.control.user.accounting.common.spec.ItemAccountingCategoryUniversalSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.logic.ItemAccountingCategoryLogic;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDescription;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDetail;
import com.echothree.model.data.accounting.server.value.ItemAccountingCategoryDescriptionValue;
import com.echothree.model.data.accounting.server.value.ItemAccountingCategoryDetailValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemAccountingCategoryCommand
        extends BaseAbstractEditCommand<ItemAccountingCategoryUniversalSpec, ItemAccountingCategoryEdit, EditItemAccountingCategoryResult, ItemAccountingCategory, ItemAccountingCategory> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ItemAccountingCategory.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemAccountingCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentItemAccountingCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditItemAccountingCategoryCommand */
    public EditItemAccountingCategoryCommand(UserVisitPK userVisitPK, EditItemAccountingCategoryForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditItemAccountingCategoryResult getResult() {
        return AccountingResultFactory.getEditItemAccountingCategoryResult();
    }

    @Override
    public ItemAccountingCategoryEdit getEdit() {
        return AccountingEditFactory.getItemAccountingCategoryEdit();
    }

    @Override
    public ItemAccountingCategory getEntity(EditItemAccountingCategoryResult result) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        ItemAccountingCategory itemAccountingCategory = null;
        String itemAccountingCategoryName = spec.getItemAccountingCategoryName();
        var parameterCount = (itemAccountingCategoryName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        if(parameterCount == 1) {
            if(itemAccountingCategoryName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec, ComponentVendors.ECHOTHREE.name(),
                        EntityTypes.ItemAccountingCategory.name());

                if(!hasExecutionErrors()) {
                    itemAccountingCategory = accountingControl.getItemAccountingCategoryByEntityInstance(entityInstance, editModeToEntityPermission(editMode));
                }
            } else {
                itemAccountingCategory = ItemAccountingCategoryLogic.getInstance().getItemAccountingCategoryByName(this, itemAccountingCategoryName, editModeToEntityPermission(editMode));
            }

            if(itemAccountingCategory != null) {
                result.setItemAccountingCategory(accountingControl.getItemAccountingCategoryTransfer(getUserVisit(), itemAccountingCategory));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return itemAccountingCategory;
    }

    @Override
    public ItemAccountingCategory getLockEntity(ItemAccountingCategory itemAccountingCategory) {
        return itemAccountingCategory;
    }

    @Override
    public void fillInResult(EditItemAccountingCategoryResult result, ItemAccountingCategory itemAccountingCategory) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        result.setItemAccountingCategory(accountingControl.getItemAccountingCategoryTransfer(getUserVisit(), itemAccountingCategory));
    }

    ItemAccountingCategory parentItemAccountingCategory = null;

    @Override
    public void doLock(ItemAccountingCategoryEdit edit, ItemAccountingCategory itemAccountingCategory) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        ItemAccountingCategoryDescription itemAccountingCategoryDescription = accountingControl.getItemAccountingCategoryDescription(itemAccountingCategory, getPreferredLanguage());
        ItemAccountingCategoryDetail itemAccountingCategoryDetail = itemAccountingCategory.getLastDetail();

        parentItemAccountingCategory = itemAccountingCategoryDetail.getParentItemAccountingCategory();

        edit.setItemAccountingCategoryName(itemAccountingCategoryDetail.getItemAccountingCategoryName());
        edit.setParentItemAccountingCategoryName(parentItemAccountingCategory == null? null: parentItemAccountingCategory.getLastDetail().getItemAccountingCategoryName());
        edit.setIsDefault(itemAccountingCategoryDetail.getIsDefault().toString());
        edit.setSortOrder(itemAccountingCategoryDetail.getSortOrder().toString());

        if(itemAccountingCategoryDescription != null) {
            edit.setDescription(itemAccountingCategoryDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ItemAccountingCategory itemAccountingCategory) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        String itemAccountingCategoryName = edit.getItemAccountingCategoryName();
        ItemAccountingCategory duplicateItemAccountingCategory = accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);

        if(duplicateItemAccountingCategory == null || itemAccountingCategory.equals(duplicateItemAccountingCategory)) {
            String parentItemAccountingCategoryName = edit.getParentItemAccountingCategoryName();

            parentItemAccountingCategory = parentItemAccountingCategoryName == null? null: accountingControl.getItemAccountingCategoryByName(parentItemAccountingCategoryName);

            if(parentItemAccountingCategoryName == null || parentItemAccountingCategory != null) {
                if(parentItemAccountingCategory != null) {
                    if(!accountingControl.isParentItemAccountingCategorySafe(itemAccountingCategory, parentItemAccountingCategory)) {
                        addExecutionError(ExecutionErrors.InvalidParentItemAccountingCategory.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentItemAccountingCategoryName.name(), parentItemAccountingCategoryName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateItemAccountingCategoryName.name(), itemAccountingCategoryName);
        }
    }

    @Override
    public void doUpdate(ItemAccountingCategory itemAccountingCategory) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var partyPK = getPartyPK();
        ItemAccountingCategoryDetailValue itemAccountingCategoryDetailValue = accountingControl.getItemAccountingCategoryDetailValueForUpdate(itemAccountingCategory);
        ItemAccountingCategoryDescription itemAccountingCategoryDescription = accountingControl.getItemAccountingCategoryDescriptionForUpdate(itemAccountingCategory, getPreferredLanguage());
        String description = edit.getDescription();

        itemAccountingCategoryDetailValue.setItemAccountingCategoryName(edit.getItemAccountingCategoryName());
        itemAccountingCategoryDetailValue.setParentItemAccountingCategoryPK(parentItemAccountingCategory == null? null: parentItemAccountingCategory.getPrimaryKey());
        itemAccountingCategoryDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        itemAccountingCategoryDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        accountingControl.updateItemAccountingCategoryFromValue(itemAccountingCategoryDetailValue, partyPK);

        if(itemAccountingCategoryDescription == null && description != null) {
            accountingControl.createItemAccountingCategoryDescription(itemAccountingCategory, getPreferredLanguage(), description, partyPK);
        } else if(itemAccountingCategoryDescription != null && description == null) {
            accountingControl.deleteItemAccountingCategoryDescription(itemAccountingCategoryDescription, partyPK);
        } else if(itemAccountingCategoryDescription != null && description != null) {
            ItemAccountingCategoryDescriptionValue itemAccountingCategoryDescriptionValue = accountingControl.getItemAccountingCategoryDescriptionValue(itemAccountingCategoryDescription);

            itemAccountingCategoryDescriptionValue.setDescription(description);
            accountingControl.updateItemAccountingCategoryDescriptionFromValue(itemAccountingCategoryDescriptionValue, partyPK);
        }
    }
    
}
