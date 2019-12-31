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

package com.echothree.model.control.order.common.transfer;

import com.echothree.model.control.payment.common.transfer.PartyPaymentMethodTransfer;
import com.echothree.model.control.payment.common.transfer.PaymentMethodTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class OrderPaymentPreferenceTransfer
        extends BaseTransfer {

    private Integer orderPaymentPreferenceSequence;
    private PaymentMethodTransfer paymentMethod;
    private PartyPaymentMethodTransfer partyPaymentMethod;
    private Boolean wasPresent;
    private Long unformattedMaximumAmount;
    private String maximumAmount;
    private Integer sortOrder;
    
    /** Creates a new instance of OrderPaymentPreferenceTransfer */
    public OrderPaymentPreferenceTransfer(Integer orderPaymentPreferenceSequence, PaymentMethodTransfer paymentMethod,
            PartyPaymentMethodTransfer partyPaymentMethod, Boolean wasPresent, Long unformattedMaximumAmount, String maximumAmount, Integer sortOrder) {
        this.orderPaymentPreferenceSequence = orderPaymentPreferenceSequence;
        this.paymentMethod = paymentMethod;
        this.partyPaymentMethod = partyPaymentMethod;
        this.wasPresent = wasPresent;
        this.unformattedMaximumAmount = unformattedMaximumAmount;
        this.maximumAmount = maximumAmount;
        this.sortOrder = sortOrder;
    }

    /**
     * @return the orderPaymentPreferenceSequence
     */
    public Integer getOrderPaymentPreferenceSequence() {
        return orderPaymentPreferenceSequence;
    }

    /**
     * @param orderPaymentPreferenceSequence the orderPaymentPreferenceSequence to set
     */
    public void setOrderPaymentPreferenceSequence(Integer orderPaymentPreferenceSequence) {
        this.orderPaymentPreferenceSequence = orderPaymentPreferenceSequence;
    }

    /**
     * @return the paymentMethod
     */
    public PaymentMethodTransfer getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(PaymentMethodTransfer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return the partyPaymentMethod
     */
    public PartyPaymentMethodTransfer getPartyPaymentMethod() {
        return partyPaymentMethod;
    }

    /**
     * @param partyPaymentMethod the partyPaymentMethod to set
     */
    public void setPartyPaymentMethod(PartyPaymentMethodTransfer partyPaymentMethod) {
        this.partyPaymentMethod = partyPaymentMethod;
    }

    /**
     * @return the wasPresent
     */
    public Boolean getWasPresent() {
        return wasPresent;
    }

    /**
     * @param wasPresent the wasPresent to set
     */
    public void setWasPresent(Boolean wasPresent) {
        this.wasPresent = wasPresent;
    }

    /**
     * @return the unformattedMaximumAmount
     */
    public Long getUnformattedMaximumAmount() {
        return unformattedMaximumAmount;
    }

    /**
     * @param unformattedMaximumAmount the unformattedMaximumAmount to set
     */
    public void setUnformattedMaximumAmount(Long unformattedMaximumAmount) {
        this.unformattedMaximumAmount = unformattedMaximumAmount;
    }

    /**
     * @return the maximumAmount
     */
    public String getMaximumAmount() {
        return maximumAmount;
    }

    /**
     * @param maximumAmount the maximumAmount to set
     */
    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}
