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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentCategoryItemForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.control.user.content.common.result.GetContentCategoryItemResult;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.associate.server.logic.AssociateReferralLogic;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetContentCategoryItemCommand
        extends BaseSingleEntityCommand<ContentCategoryItem, GetContentCategoryItemForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentWebAddressName", FieldType.HOST_NAME, false, null, null),
                new FieldDefinition("ContentCollectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContentCatalogName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContentCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AssociateProgramName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociateName", FieldType.STRING, false, null, null),
                new FieldDefinition("AssociatePartyContactMechanismName", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentCategoryItemCommand */
    public GetContentCategoryItemCommand(UserVisitPK userVisitPK, GetContentCategoryItemForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ContentCategoryItem getEntity() {
        String contentWebAddressName = form.getContentWebAddressName();
        String contentCollectionName = form.getContentCollectionName();
        var parameterCount = (contentWebAddressName == null ? 0 : 1) + (contentCollectionName == null ? 0 : 1);
        ContentCategoryItem contentCategoryItem = null;

        if(parameterCount == 1) {
            var contentControl = Session.getModelController(ContentControl.class);
            ContentCollection contentCollection = null;

            if(contentWebAddressName != null) {
                ContentWebAddress contentWebAddress = contentControl.getContentWebAddressByName(contentWebAddressName);

                if(contentWebAddress != null) {
                    contentCollection = contentWebAddress.getLastDetail().getContentCollection();
                } else {
                    addExecutionError(ExecutionErrors.UnknownContentWebAddressName.name(), contentWebAddressName);
                }
            } else {
                contentCollection = contentControl.getContentCollectionByName(contentCollectionName);

                if(contentCollection == null) {
                    addExecutionError(ExecutionErrors.UnknownContentCollectionName.name(), contentCollectionName);
                }
            }

            if(!hasExecutionErrors()) {
                var itemControl = Session.getModelController(ItemControl.class);
                String itemName = form.getItemName();
                Item item = itemControl.getItemByName(itemName);

                if(item != null) {
                    var inventoryControl = Session.getModelController(InventoryControl.class);
                    String inventoryConditionName = form.getInventoryConditionName();
                    InventoryCondition inventoryCondition = inventoryConditionName == null ? inventoryControl.getDefaultInventoryCondition()
                            : inventoryControl.getInventoryConditionByName(inventoryConditionName);

                    if(inventoryCondition != null) {
                        var uomControl = Session.getModelController(UomControl.class);
                        String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                        ItemDetail itemDetail = item.getLastDetail();
                        UnitOfMeasureKind unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
                        UnitOfMeasureType unitOfMeasureType = unitOfMeasureTypeName == null ? uomControl.getDefaultUnitOfMeasureType(unitOfMeasureKind)
                                : uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

                        if(unitOfMeasureType != null) {
                            var accountingControl = Session.getModelController(AccountingControl.class);
                            String currencyIsoName = form.getCurrencyIsoName();
                            Currency currency = currencyIsoName == null ? accountingControl.getDefaultCurrency()
                                    : accountingControl.getCurrencyByIsoName(currencyIsoName);

                            if(currency != null) {
                                String contentCatalogName = form.getContentCatalogName();
                                var partyPK = getPartyPK();
                                UserVisit userVisit = getUserVisitForUpdate();

                                ContentCatalog contentCatalog = contentCatalogName == null ? contentControl.getDefaultContentCatalog(contentCollection)
                                        : contentControl.getContentCatalogByName(contentCollection, contentCatalogName);

                                if(contentCatalog != null) {
                                    String contentCategoryName = form.getContentCategoryName();
                                    ContentCategory contentCategory = contentCategoryName == null ? null
                                            : contentControl.getContentCategoryByName(contentCatalog, contentCategoryName);

                                    if(contentCategoryName == null || contentCategory != null) {
                                        ContentCatalogItem contentCatalogItem = contentControl.getContentCatalogItem(contentCatalog, item,
                                                inventoryCondition, unitOfMeasureType, currency);

                                        if(contentCatalogItem != null) {
                                            // If contentCategory is null, we'll attempt to find the item in a default ContentCategory
                                            // that it had been placed in. Always direct the user to the item if possible in a category
                                            // if we can.
                                            contentCategoryItem = contentCategory == null ? contentControl.getDefaultContentCategoryItem(contentCatalogItem)
                                                    : contentControl.getContentCategoryItem(contentCategory, contentCatalogItem);

                                            if(contentCategoryItem != null) {
                                                if(contentCategory == null) {
                                                    contentCategory = contentCategoryItem.getContentCategory();
                                                }
                                                
                                                AssociateReferralLogic.getInstance().handleAssociateReferral(session, this, form, userVisit, contentCategory.getPrimaryKey(), partyPK);

                                                if(!hasExecutionErrors()) {
                                                    sendEvent(contentCategory.getPrimaryKey(), EventTypes.READ, null, null, partyPK);
                                                }
                                            } else {
                                                if(contentCategoryName == null) {
                                                    addExecutionError(ExecutionErrors.UnknownDefaultContentCategoryItem.name(), contentCollection.getLastDetail().getContentCollectionName(),
                                                            contentCatalogName, itemName, inventoryConditionName, unitOfMeasureTypeName, currencyIsoName);
                                                } else {
                                                    addExecutionError(ExecutionErrors.UnknownContentCategoryItem.name(), contentCollection.getLastDetail().getContentCollectionName(),
                                                            contentCatalogName, contentCategoryName, itemName, inventoryConditionName, unitOfMeasureTypeName,
                                                            currencyIsoName);
                                                }
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownContentCatalogItem.name(), contentCollection.getLastDetail().getContentCollectionName(),
                                                    contentCatalog.getLastDetail().getContentCatalogName(), itemName, inventoryConditionName, unitOfMeasureTypeName, currencyIsoName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownContentCategoryName.name(), contentCollection.getLastDetail().getContentCollectionName(),
                                                contentCatalog.getLastDetail().getContentCatalogName(), contentCategoryName);
                                    }
                                } else {
                                    if(contentCatalogName == null) {
                                        addExecutionError(ExecutionErrors.UnknownDefaultContentCatalog.name(), contentCollection.getLastDetail().getContentCollectionName());
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownContentCatalogName.name(), contentCollection.getLastDetail().getContentCollectionName(),
                                                contentCatalogName);
                                    }
                                }
                            } else {
                                if(currencyIsoName == null) {
                                    addExecutionError(ExecutionErrors.UnknownDefaultCurrency.name());
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                                }
                            }
                        } else {
                            if(unitOfMeasureTypeName == null) {
                                addExecutionError(ExecutionErrors.UnknownDefaultUnitOfMeasureType.name(),
                                        unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(),
                                        unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName(), unitOfMeasureTypeName);
                            }
                        }
                    } else {
                        if(inventoryConditionName == null) {
                            addExecutionError(ExecutionErrors.UnknownDefaultInventoryCondition.name());
                        } else {
                            addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return contentCategoryItem;
    }
    
    @Override
    protected BaseResult getTransfer(ContentCategoryItem contentCategoryItem) {
        GetContentCategoryItemResult result = ContentResultFactory.getGetContentCategoryItemResult();

        if (contentCategoryItem != null) {
            var contentControl = Session.getModelController(ContentControl.class);

            result.setContentCategoryItem(contentControl.getContentCategoryItemTransfer(getUserVisit(), contentCategoryItem));
        }

        return result;
    }
    
}
