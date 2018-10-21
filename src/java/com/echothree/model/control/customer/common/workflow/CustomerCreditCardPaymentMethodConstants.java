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

package com.echothree.model.control.customer.common.workflow;

public interface CustomerCreditCardPaymentMethodConstants {
    
    String Workflow_CUSTOMER_CREDIT_CARD_PAYMENT_METHOD = "CUSTOMER_CREDIT_CARD_PAYMENT_METHOD";
    
    String WorkflowStep_VALID = "VALID";
    String WorkflowStep_FRAUDULENT = "FRAUDULENT";
    
    String WorkflowDestination_VALID_TO_FRAUDULENT = "VALID_TO_FRAUDULENT";
    String WorkflowDestination_FRAUDULENT_TO_VALID = "FRAUDULENT_TO_VALID";
    
    String WorkflowEntrance_NEW_VALID = "NEW_VALID";
    
}
