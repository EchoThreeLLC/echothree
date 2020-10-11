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

package com.echothree.model.control.content.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.inventory.server.graphql.InventoryConditionObject;
import com.echothree.model.control.inventory.server.graphql.InventorySecurityUtils;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCatalogItemFixedPrice;
import com.echothree.model.data.content.server.entity.ContentCatalogItemVariablePrice;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("content catalog item object")
@GraphQLName("ContentCatalogItem")
public class ContentCatalogItemObject
        extends BaseEntityInstanceObject {
    
    private final ContentCatalogItem contentCatalogItem; // Always Present
    
    public ContentCatalogItemObject(ContentCatalogItem contentCatalogItem) {
        super(contentCatalogItem.getPrimaryKey());
        
        this.contentCatalogItem = contentCatalogItem;
    }

    String itemPriceTypeName;

    private String getItemPriceTypeName() {
        if(itemPriceTypeName == null) {
            itemPriceTypeName = contentCatalogItem.getItem().getLastDetail().getItemPriceType().getItemPriceTypeName();
        }

        return itemPriceTypeName;
    }

    ContentCatalogItemFixedPrice contentCatalogItemFixedPrice;

    private ContentCatalogItemFixedPrice getContentCatalogItemFixedPrice() {
        if(contentCatalogItemFixedPrice == null) {
            if(getItemPriceTypeName().equals(ItemPriceTypes.FIXED.name())) {
                var contentControl = (ContentControl)Session.getModelController(ContentControl.class);

                contentCatalogItemFixedPrice = contentControl.getContentCatalogItemFixedPrice(contentCatalogItem);
            }
        }

        return contentCatalogItemFixedPrice;
    }

    ContentCatalogItemVariablePrice contentCatalogItemVariablePrice;

    private ContentCatalogItemVariablePrice getContentCatalogItemVariablePrice() {
        if(contentCatalogItemVariablePrice == null) {
            if(getItemPriceTypeName().equals(ItemPriceTypes.VARIABLE.name())) {
                var contentControl = (ContentControl)Session.getModelController(ContentControl.class);

                contentCatalogItemVariablePrice = contentControl.getContentCatalogItemVariablePrice(contentCatalogItem);
            }
        }

        return contentCatalogItemVariablePrice;
    }

    @GraphQLField
    @GraphQLDescription("content catalog")
    public ContentCatalogObject getContentCatalog(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getInstance().getHasContentCatalogAccess(env) ? new ContentCatalogObject(contentCatalogItem.getContentCatalog()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemAccess(env) ? new ItemObject(contentCatalogItem.getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory condition")
    public InventoryConditionObject getInventoryCondition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getInstance().getHasInventoryConditionAccess(env) ? new InventoryConditionObject(contentCatalogItem.getInventoryCondition()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getInstance().getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(contentCatalogItem.getUnitOfMeasureType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("currency")
    public CurrencyObject getCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getInstance().getHasCurrencyAccess(env) ? new CurrencyObject(contentCatalogItem.getCurrency()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unformattedUnitPrice")
    public Long getUnformattedUnitPrice(final DataFetchingEnvironment env) {
        ContentCatalogItemFixedPrice contentCatalogItemFixedPrice = getContentCatalogItemFixedPrice();

        return contentCatalogItemFixedPrice == null ? null : contentCatalogItemFixedPrice.getUnitPrice();
    }

    @GraphQLField
    @GraphQLDescription("unitPrice")
    public String getUnitPrice(final DataFetchingEnvironment env) {
        ContentCatalogItemFixedPrice contentCatalogItemFixedPrice = getContentCatalogItemFixedPrice();

        return contentCatalogItemFixedPrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(contentCatalogItem.getCurrency(), contentCatalogItemFixedPrice.getUnitPrice());
    }

    @GraphQLField
    @GraphQLDescription("unformattedMinimumUnitPrice")
    public Long getUnformattedMinimumUnitPrice(final DataFetchingEnvironment env) {
        ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = getContentCatalogItemVariablePrice();

        return contentCatalogItemVariablePrice == null ? null : contentCatalogItemVariablePrice.getMinimumUnitPrice();
    }

    @GraphQLField
    @GraphQLDescription("minimumUnitPrice")
    public String getMinimumUnitPrice(final DataFetchingEnvironment env) {
        ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = getContentCatalogItemVariablePrice();

        return contentCatalogItemVariablePrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(contentCatalogItem.getCurrency(), contentCatalogItemVariablePrice.getMinimumUnitPrice());
    }

    @GraphQLField
    @GraphQLDescription("unformattedMaximumUnitPrice")
    public Long getUnformattedMaximumUnitPrice(final DataFetchingEnvironment env) {
        ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = getContentCatalogItemVariablePrice();

        return contentCatalogItemVariablePrice == null ? null : contentCatalogItemVariablePrice.getMaximumUnitPrice();
    }

    @GraphQLField
    @GraphQLDescription("maximumUnitPrice")
    public String getMaximumUnitPrice(final DataFetchingEnvironment env) {
        ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = getContentCatalogItemVariablePrice();

        return contentCatalogItemVariablePrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(contentCatalogItem.getCurrency(), contentCatalogItemVariablePrice.getMaximumUnitPrice());
    }

    @GraphQLField
    @GraphQLDescription("unformattedUnitPriceIncrement")
    public Long getUnformattedUnitPriceIncrement(final DataFetchingEnvironment env) {
        ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = getContentCatalogItemVariablePrice();

        return contentCatalogItemVariablePrice == null ? null : contentCatalogItemVariablePrice.getUnitPriceIncrement();
    }

    @GraphQLField
    @GraphQLDescription("unitPriceIncrement")
    public String getUnitPriceIncrement(final DataFetchingEnvironment env) {
        ContentCatalogItemVariablePrice contentCatalogItemVariablePrice = getContentCatalogItemVariablePrice();

        return contentCatalogItemVariablePrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(contentCatalogItem.getCurrency(), contentCatalogItemVariablePrice.getUnitPriceIncrement());
    }

}
