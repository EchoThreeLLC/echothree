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

package com.echothree.model.control.offer.server.logic;

import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemException;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemPriceException;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.model.data.offer.server.value.OfferItemFixedPriceValue;
import com.echothree.model.data.offer.server.value.OfferItemVariablePriceValue;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class OfferItemLogic
        extends BaseLogic {

    private OfferItemLogic() {
        super();
    }

    private static class OfferItemLogicHolder {
        static OfferItemLogic instance = new OfferItemLogic();
    }

    public static OfferItemLogic getInstance() {
        return OfferItemLogicHolder.instance;
    }

    public OfferItem createOfferItem(final Offer offer, final Item item, final BasePK createdBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        return offerItemControl.createOfferItem(offer, item, createdBy);
    }

    public OfferItem getOfferItem(final ExecutionErrorAccumulator eea, final Offer offer, final Item item) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);
        OfferItem offerItem = offerItemControl.getOfferItem(offer, item);

        if(offerItem == null) {
            handleExecutionError(UnknownOfferItemException.class, eea, ExecutionErrors.UnknownOfferItem.name(),
                    offer.getLastDetail().getOfferName(), item.getLastDetail().getItemName());
        }

        return offerItem;
    }

    public void deleteOfferItem(final OfferItem offerItem, final BasePK deletedBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        offerItemControl.deleteOfferItem(offerItem, deletedBy);
    }

    public void deleteOfferItems(List<OfferItem> offerItems, BasePK deletedBy) {
        offerItems.stream().forEach((offerItem) -> {
            deleteOfferItem(offerItem, deletedBy);
        });
    }

    public void deleteOfferItemsByOffer(Offer offer, BasePK deletedBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        deleteOfferItems(offerItemControl.getOfferItemsByOfferForUpdate(offer), deletedBy);
    }

    public void deleteOfferItemsByOffers(List<Offer> offers, BasePK deletedBy) {
        offers.stream().forEach((offer) -> {
            deleteOfferItemsByOffer(offer, deletedBy);
        });
    }

    public OfferItemPrice createOfferItemPrice(final OfferItem offerItem, final InventoryCondition inventoryCondition,
            final UnitOfMeasureType unitOfMeasureType, final Currency currency, final BasePK createdBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        return offerItemControl.createOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency, createdBy);
    }

    public OfferItemPrice getOfferItemPrice(final ExecutionErrorAccumulator eea, final Offer offer, final Item item,
            final InventoryCondition inventoryCondition, final UnitOfMeasureType unitOfMeasureType, final Currency currency) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);
        OfferItemPrice offerItemPrice = null;
        OfferItem offerItem = offerItemControl.getOfferItem(offer, item);

        if(offerItem != null) {
            offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency);

            if(offerItemPrice == null) {
                handleExecutionError(UnknownOfferItemPriceException.class, eea, ExecutionErrors.UnknownOfferItemPrice.name(), offer.getLastDetail().getOfferName(),
                        item.getLastDetail().getItemName(), inventoryCondition.getLastDetail().getInventoryConditionName(),
                        unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(), currency.getCurrencyIsoName());
            }
        } else {
            handleExecutionError(UnknownOfferItemException.class, eea, ExecutionErrors.UnknownOfferItem.name(),
                    offer.getLastDetail().getOfferName(), item.getLastDetail().getItemName());
        }

        return offerItemPrice;
    }

    public void deleteOfferItemPrice(final OfferItemPrice offerItemPrice, final BasePK deletedBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        ContentLogic.getInstance().deleteContentCategoryItemByOfferItemPrice(offerItemPrice, deletedBy);

        var offerItem = offerItemPrice.getOfferItemForUpdate();
        var item = offerItem.getItem();
        var itemPriceType = item.getLastDetail().getItemPriceType();
        var itemPriceTypeName = itemPriceType.getItemPriceTypeName();

        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            var offerItemFixedPrice = offerItemControl.getOfferItemFixedPriceForUpdate(offerItemPrice);

            deleteOfferItemFixedPrice(offerItemFixedPrice, deletedBy);
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            var offerItemVariablePrice = offerItemControl.getOfferItemVariablePriceForUpdate(offerItemPrice);

            deleteOfferItemVariablePrice(offerItemVariablePrice, deletedBy);
        }

        offerItemControl.deleteOfferItemPrice(offerItemPrice, deletedBy);

        if(offerItemControl.countOfferItemPricesByItem(item) == 0) {
            deleteOfferItem(offerItem, deletedBy);
        }
    }

    public void deleteOfferItemPrices(List<OfferItemPrice> offerItemPrices, BasePK deletedBy) {
        offerItemPrices.stream().forEach((offerItemPrice) -> {
            deleteOfferItemPrice(offerItemPrice, deletedBy);
        });
    }

    public void deleteOfferItemPricesByOfferItem(OfferItem offerItem, BasePK deletedBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        deleteOfferItemPrices(offerItemControl.getOfferItemPricesByOfferItemForUpdate(offerItem), deletedBy);
    }

    public void deleteOfferItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        deleteOfferItemPrices(offerItemControl.getOfferItemPricesByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }

    public OfferItemFixedPrice createOfferItemFixedPrice(final OfferItemPrice offerItemPrice, final Long unitPrice, final BasePK createdBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        return offerItemControl.createOfferItemFixedPrice(offerItemPrice, unitPrice, createdBy);
    }

    public void updateOfferItemFixedPriceFromValue(final OfferItemFixedPriceValue offerItemFixedPriceValue, final BasePK updatedBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        OfferItemFixedPrice offerItemFixedPrice = offerItemControl.updateOfferItemFixedPriceFromValue(offerItemFixedPriceValue, updatedBy);

        if(offerItemFixedPrice != null) {
            ContentLogic.getInstance().updateContentCatalogItemPricesByOfferItemPrice(offerItemFixedPrice.getOfferItemPrice(), updatedBy);
        }
    }

    public void deleteOfferItemFixedPrice(final OfferItemFixedPrice offerItemFixedPrice, final BasePK deletedBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        offerItemControl.deleteOfferItemFixedPrice(offerItemFixedPrice, deletedBy);
    }

    public OfferItemVariablePrice createOfferItemVariablePrice(final OfferItemPrice offerItemPrice, final Long minimumUnitPrice,
            final Long maximumUnitPrice, final Long unitPriceIncrement, final BasePK createdBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        return offerItemControl.createOfferItemVariablePrice(offerItemPrice, minimumUnitPrice, maximumUnitPrice, unitPriceIncrement, createdBy);
    }

    public void updateOfferItemVariablePriceFromValue(final OfferItemVariablePriceValue offerItemVariablePriceValue, final BasePK updatedBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        OfferItemVariablePrice offerItemVariablePrice = offerItemControl.updateOfferItemVariablePriceFromValue(offerItemVariablePriceValue, updatedBy);

        if(offerItemVariablePrice != null) {
            ContentLogic.getInstance().updateContentCatalogItemPricesByOfferItemPrice(offerItemVariablePrice.getOfferItemPrice(), updatedBy);
        }
    }

    public void deleteOfferItemVariablePrice(final OfferItemVariablePrice offerItemVariablePrice, final BasePK deletedBy) {
        var offerItemControl = (OfferItemControl)Session.getModelController(OfferItemControl.class);

        offerItemControl.deleteOfferItemVariablePrice(offerItemVariablePrice, deletedBy);
    }

}
