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

package com.echothree.model.control.offer.server.graphql;

import com.echothree.model.control.accounting.server.graphql.AccountingSecurityUtils;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.inventory.server.graphql.InventoryConditionObject;
import com.echothree.model.control.inventory.server.graphql.InventorySecurityUtils;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("offer item price object")
@GraphQLName("OfferItemPrice")
public class OfferItemPriceObject
        extends BaseObject {
    
    private final OfferItemPrice offerItemPrice; // Always Present
    
    public OfferItemPriceObject(OfferItemPrice offerItemPrice) {
        super(offerItemPrice.getPrimaryKey());
        
        this.offerItemPrice = offerItemPrice;
    }

    String offerItemPriceTypeName;

    private String getOfferItemPriceTypeName() {
        if(offerItemPriceTypeName == null) {
            offerItemPriceTypeName = offerItemPrice.getOfferItem().getItem().getLastDetail().getItemPriceType().getItemPriceTypeName();
        }

        return offerItemPriceTypeName;
    }

    private OfferItemFixedPrice offerItemFixedPrice;

    private OfferItemFixedPrice getOfferItemFixedPrice() {
        if(offerItemFixedPrice == null) {
            if(getOfferItemPriceTypeName().equals(ItemPriceTypes.FIXED.name())) {
                var offerItemControl = Session.getModelController(OfferItemControl.class);

                offerItemFixedPrice = offerItemControl.getOfferItemFixedPrice(offerItemPrice);
            }
        }

        return offerItemFixedPrice;
    }

    private OfferItemVariablePrice offerItemVariablePrice;

    private OfferItemVariablePrice getOfferItemVariablePrice() {
        if(offerItemVariablePrice == null) {
            if(getOfferItemPriceTypeName().equals(ItemPriceTypes.VARIABLE.name())) {
                var offerItemControl = Session.getModelController(OfferItemControl.class);

                offerItemVariablePrice = offerItemControl.getOfferItemVariablePrice(offerItemPrice);
            }
        }

        return offerItemVariablePrice;
    }

    @GraphQLField
    @GraphQLDescription("offer item")
    public OfferItemObject getOfferItem(final DataFetchingEnvironment env) {
        return OfferSecurityUtils.getInstance().getHasOfferItemAccess(env) ? new OfferItemObject(offerItemPrice.getOfferItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory condition")
    public InventoryConditionObject getInventoryCondition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getInstance().getHasInventoryConditionAccess(env) ? new InventoryConditionObject(offerItemPrice.getInventoryCondition()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getInstance().getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(offerItemPrice.getUnitOfMeasureType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("currency")
    public CurrencyObject getCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getInstance().getHasCurrencyAccess(env) ? new CurrencyObject(offerItemPrice.getCurrency()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unformattedUnitPrice")
    public Long getUnformattedUnitPrice() {
        var itemFixedPrice = getOfferItemFixedPrice();

        return itemFixedPrice == null ? null : itemFixedPrice.getUnitPrice();
    }

    @GraphQLField
    @GraphQLDescription("unitPrice")
    public String getUnitPrice() {
        var itemFixedPrice = getOfferItemFixedPrice();

        return itemFixedPrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(offerItemPrice.getCurrency(), itemFixedPrice.getUnitPrice());
    }

    @GraphQLField
    @GraphQLDescription("unformattedMinimumUnitPrice")
    public Long getUnformattedMinimumUnitPrice() {
        var itemVariablePrice = getOfferItemVariablePrice();

        return itemVariablePrice == null ? null : itemVariablePrice.getMinimumUnitPrice();
    }

    @GraphQLField
    @GraphQLDescription("minimumUnitPrice")
    public String getMinimumUnitPrice() {
        var itemVariablePrice = getOfferItemVariablePrice();

        return itemVariablePrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(offerItemPrice.getCurrency(), itemVariablePrice.getMinimumUnitPrice());
    }

    @GraphQLField
    @GraphQLDescription("unformattedMaximumUnitPrice")
    public Long getUnformattedMaximumUnitPrice() {
        var itemVariablePrice = getOfferItemVariablePrice();

        return itemVariablePrice == null ? null : itemVariablePrice.getMaximumUnitPrice();
    }

    @GraphQLField
    @GraphQLDescription("maximumUnitPrice")
    public String getMaximumUnitPrice() {
        var itemVariablePrice = getOfferItemVariablePrice();

        return itemVariablePrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(offerItemPrice.getCurrency(), itemVariablePrice.getMaximumUnitPrice());
    }

    @GraphQLField
    @GraphQLDescription("unformattedUnitPriceIncrement")
    public Long getUnformattedUnitPriceIncrement() {
        var itemVariablePrice = getOfferItemVariablePrice();

        return itemVariablePrice == null ? null : itemVariablePrice.getUnitPriceIncrement();
    }

    @GraphQLField
    @GraphQLDescription("unitPriceIncrement")
    public String getUnitPriceIncrement() {
        var itemVariablePrice = getOfferItemVariablePrice();

        return itemVariablePrice == null ? null :
                AmountUtils.getInstance().formatPriceUnit(offerItemPrice.getCurrency(), itemVariablePrice.getUnitPriceIncrement());
    }

}
