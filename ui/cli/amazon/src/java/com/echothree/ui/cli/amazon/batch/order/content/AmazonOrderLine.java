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

package com.echothree.ui.cli.amazon.batch.order.content;

import com.echothree.util.common.string.StringUtils;
import java.util.Map;

public class AmazonOrderLine
        implements AmazonOrderKey {

    private AmazonOrderShipmentGroup orderShipmentGroup;
    
    private String orderItemId;
    private String itemName;
    private String listingId;
    private String sku;
    private String price;
    private String shippingFee;
    private String quantityPurchased;
    private String totalPrice;
    private String upc;

    private String orderLineSequence;
    
    /** Creates a new instance of AmazonOrderLine */
    public AmazonOrderLine(AmazonOrderShipmentGroup orderShipmentGroup, Map<String, String> dataFieldMap) {
        this.orderShipmentGroup = orderShipmentGroup;
        orderItemId = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_OrderItemId));
        itemName = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ItemName));
        listingId = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ListingId));
        sku = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_Sku));
        price = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_Price));
        shippingFee = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_ShippingFee));
        quantityPurchased = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_QuantityPurchased));
        totalPrice = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_TotalPrice));
        upc = StringUtils.getInstance().trimToNull(dataFieldMap.get(AmazonOrderFields.Field_Upc));
    }

    @Override
    public String getKey() {
        return getOrderItemId();
    }

    /**
     * @return the orderShipmentGroup
     */
    public AmazonOrderShipmentGroup getOrderShipmentGroup() {
        return orderShipmentGroup;
    }

    /**
     * @param orderShipmentGroup the orderShipmentGroup to set
     */
    public void setOrderShipmentGroup(AmazonOrderShipmentGroup orderShipmentGroup) {
        this.orderShipmentGroup = orderShipmentGroup;
    }

    /**
     * @return the orderItemId
     */
    public String getOrderItemId() {
        return orderItemId;
    }

    /**
     * @param orderItemId the orderItemId to set
     */
    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the listingId
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * @param listingId the listingId to set
     */
    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    /**
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return the shippingFee
     */
    public String getShippingFee() {
        return shippingFee;
    }

    /**
     * @param shippingFee the shippingFee to set
     */
    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    /**
     * @return the quantityPurchased
     */
    public String getQuantityPurchased() {
        return quantityPurchased;
    }

    /**
     * @param quantityPurchased the quantityPurchased to set
     */
    public void setQuantityPurchased(String quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    /**
     * @return the totalPrice
     */
    public String getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the upc
     */
    public String getUpc() {
        return upc;
    }

    /**
     * @param upc the upc to set
     */
    public void setUpc(String upc) {
        this.upc = upc;
    }

    /**
     * @return the orderLineSequence
     */
    public String getOrderLineSequence() {
        return orderLineSequence;
    }

    /**
     * @param orderLineSequence the orderLineSequence to set
     */
    public void setOrderLineSequence(String orderLineSequence) {
        this.orderLineSequence = orderLineSequence;
    }

}
