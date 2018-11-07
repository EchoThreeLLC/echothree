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

package com.echothree.model.control.order.common.transfer;

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.item.common.transfer.ItemDeliveryTypeTransfer;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class OrderShipmentGroupTransfer
        extends BaseTransfer {

    private Integer orderShipmentGroupSequence;
    private ItemDeliveryTypeTransfer itemDeliveryType;
    private Boolean isDefault;
    private PartyContactMechanismTransfer partyContactMechanism;
    private ShippingMethodTransfer shippingMethod;
    private Boolean holdUntilComplete;
    
    /** Creates a new instance of OrderShipmentGroupTransfer */
    public OrderShipmentGroupTransfer(Integer orderShipmentGroupSequence, ItemDeliveryTypeTransfer itemDeliveryType, Boolean isDefault,
            PartyContactMechanismTransfer partyContactMechanism, ShippingMethodTransfer shippingMethod, Boolean holdUntilComplete) {
        this.orderShipmentGroupSequence = orderShipmentGroupSequence;
        this.itemDeliveryType = itemDeliveryType;
        this.isDefault = isDefault;
        this.partyContactMechanism = partyContactMechanism;
        this.shippingMethod = shippingMethod;
        this.holdUntilComplete = holdUntilComplete;
    }

    /**
     * @return the orderShipmentGroupSequence
     */
    public Integer getOrderShipmentGroupSequence() {
        return orderShipmentGroupSequence;
    }

    /**
     * @param orderShipmentGroupSequence the orderShipmentGroupSequence to set
     */
    public void setOrderShipmentGroupSequence(Integer orderShipmentGroupSequence) {
        this.orderShipmentGroupSequence = orderShipmentGroupSequence;
    }

    /**
     * @return the itemDeliveryType
     */
    public ItemDeliveryTypeTransfer getItemDeliveryType() {
        return itemDeliveryType;
    }

    /**
     * @param itemDeliveryType the itemDeliveryType to set
     */
    public void setItemDeliveryType(ItemDeliveryTypeTransfer itemDeliveryType) {
        this.itemDeliveryType = itemDeliveryType;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the partyContactMechanism
     */
    public PartyContactMechanismTransfer getPartyContactMechanism() {
        return partyContactMechanism;
    }

    /**
     * @param partyContactMechanism the partyContactMechanism to set
     */
    public void setPartyContactMechanism(PartyContactMechanismTransfer partyContactMechanism) {
        this.partyContactMechanism = partyContactMechanism;
    }

    /**
     * @return the shippingMethod
     */
    public ShippingMethodTransfer getShippingMethod() {
        return shippingMethod;
    }

    /**
     * @param shippingMethod the shippingMethod to set
     */
    public void setShippingMethod(ShippingMethodTransfer shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    /**
     * @return the holdUntilComplete
     */
    public Boolean getHoldUntilComplete() {
        return holdUntilComplete;
    }

    /**
     * @param holdUntilComplete the holdUntilComplete to set
     */
    public void setHoldUntilComplete(Boolean holdUntilComplete) {
        this.holdUntilComplete = holdUntilComplete;
    }

}
