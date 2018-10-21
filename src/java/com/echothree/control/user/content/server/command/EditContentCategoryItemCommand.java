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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.remote.edit.ContentCategoryItemEdit;
import com.echothree.control.user.content.remote.edit.ContentEditFactory;
import com.echothree.control.user.content.remote.form.EditContentCategoryItemForm;
import com.echothree.control.user.content.remote.result.ContentResultFactory;
import com.echothree.control.user.content.remote.result.EditContentCategoryItemResult;
import com.echothree.control.user.content.remote.spec.ContentCategoryItemSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.value.ContentCategoryItemValue;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
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

public class EditContentCategoryItemCommand
        extends BaseAbstractEditCommand<ContentCategoryItemSpec, ContentCategoryItemEdit, EditContentCategoryItemResult, ContentCategoryItem, ContentCategory> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContentCategoryItem.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContentCategoryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditContentCategoryItemCommand */
    public EditContentCategoryItemCommand(UserVisitPK userVisitPK, EditContentCategoryItemForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditContentCategoryItemResult getResult() {
        return ContentResultFactory.getEditContentCategoryItemResult();
    }
    
    @Override
    public ContentCategoryItemEdit getEdit() {
        return ContentEditFactory.getContentCategoryItemEdit();
    }
    
    @Override
    public ContentCategoryItem getEntity(EditContentCategoryItemResult result) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        ContentCategoryItem contentCategoryItem = null;
        String contentCollectionName = spec.getContentCollectionName();
        ContentCollection contentCollection = contentControl.getContentCollectionByName(contentCollectionName);
        
        if(contentCollection != null) {
            String contentCatalogName = spec.getContentCatalogName();
            ContentCatalog contentCatalog = contentControl.getContentCatalogByName(contentCollection, contentCatalogName);
            
            if(contentCatalog != null) {
                String contentCategoryName = spec.getContentCategoryName();
                ContentCategory contentCategory = contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);
                
                if(contentCategory != null) {
                    ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
                    String itemName = spec.getItemName();
                    Item item = itemControl.getItemByName(itemName);
                    
                    if(item != null) {
                        InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
                        String inventoryConditionName = spec.getInventoryConditionName();
                        InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);

                        if(inventoryCondition != null) {
                            UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
                            String unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                            ItemDetail itemDetail = item.getLastDetail();
                            UnitOfMeasureKind unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
                            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

                            if(unitOfMeasureType != null) {
                                AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                                String currencyIsoName = spec.getCurrencyIsoName();
                                Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

                                if(currency != null) {
                                    ContentCatalogItem contentCatalogItem = contentControl.getContentCatalogItem(contentCatalog,
                                            item, inventoryCondition, unitOfMeasureType, currency);

                                    if(contentCatalogItem != null) {
                                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                            contentCategoryItem = contentControl.getContentCategoryItem(contentCategory, contentCatalogItem);
                                        } else { // EditMode.UPDATE
                                            contentCategoryItem = contentControl.getContentCategoryItemForUpdate(contentCategory, contentCatalogItem);
                                        }
                                        
                                        if(contentCategoryItem == null) {
                                            addExecutionError(ExecutionErrors.UnknownContentCategoryItem.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownContentCatalogItem.name(), contentCollectionName, contentCatalogName, itemName, inventoryConditionName,
                                                unitOfMeasureTypeName, currencyIsoName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentCategoryName.name(), contentCategoryName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(), contentCatalogName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
        }

        return contentCategoryItem;
    }
    
    @Override
    public ContentCategory getLockEntity(ContentCategoryItem contentCategoryItem) {
        return contentCategoryItem.getContentCategory();
    }
    
    @Override
    public void fillInResult(EditContentCategoryItemResult result, ContentCategoryItem contentCategoryItem) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        
        result.setContentCategoryItem(contentControl.getContentCategoryItemTransfer(getUserVisit(), contentCategoryItem));
    }
    
    @Override
    public void doLock(ContentCategoryItemEdit edit, ContentCategoryItem contentCategoryItem) {
        edit.setIsDefault(contentCategoryItem.getIsDefault().toString());
        edit.setSortOrder(contentCategoryItem.getSortOrder().toString());
    }
    
    @Override
    public void doUpdate(ContentCategoryItem contentCategoryItem) {
        ContentControl contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        ContentCategoryItemValue contentCategoryItemValue = contentControl.getContentCategoryItemValue(contentCategoryItem);

        contentCategoryItemValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contentCategoryItemValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        ContentLogic.getInstance().updateContentCategoryItemFromValue(contentCategoryItemValue, getPartyPK());
    }
    
}
