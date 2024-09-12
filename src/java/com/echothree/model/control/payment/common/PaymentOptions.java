// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.payment.common;

public interface PaymentOptions {

    String PaymentMethodTypeIncludePaymentMethodTypePartyTypes = "PaymentMethodTypeIncludePaymentMethodTypePartyTypes";

    String PartyPaymentMethodIncludeNumber = "PartyPaymentMethodIncludeNumber";
    String PartyPaymentMethodIncludeSecurityCode = "PartyPaymentMethodIncludeSecurityCode";
    String PartyPaymentMethodIncludePartyPaymentMethodContactMechanisms = "PartyPaymentMethodIncludePartyPaymentMethodContactMechanisms";
    
    String BillingAccountIncludeRoles = "BillingAccountIncludeRoles";

    String PaymentProcessorIncludeGuid = "PaymentProcessorIncludeGuid";
    String PaymentProcessorIncludeComments = "PaymentProcessorIncludeComments";
    String PaymentProcessorIncludePaymentProcessorTransactions = "PaymentProcessorIncludePaymentProcessorTransactions";

    String PaymentMethodIncludeGuid = "PaymentMethodIncludeGuid";
    String PaymentMethodIncludeComments = "PaymentMethodIncludeComments";
    String PaymentMethodIncludeEntityAttributeGroups = "PaymentMethodIncludeEntityAttributeGroups";

    String PartyPaymentMethodIncludeGuid = "PartyPaymentMethodIncludeGuid";
    String PartyPaymentMethodIncludeComments = "PartyPaymentMethodIncludeComments";

    String PaymentProcessorTransactionIncludePaymentProcessorTransactionCodes = "PaymentProcessorTransactionIncludePaymentProcessorTransactionCodes";

}
