// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.payment.common.exception;

import com.echothree.util.common.message.Message;

public class UnknownPaymentMethodNameException
        extends BasePaymentException {
    
    /** Creates a new instance of UnknownPaymentMethodNameException */
    public UnknownPaymentMethodNameException() {
        super();
    }
    
    /** Creates a new instance of UnknownPaymentMethodNameException */
    public UnknownPaymentMethodNameException(String message) {
        super(message);
    }
    
    /** Creates a new instance of UnknownPaymentMethodNameException */
    public UnknownPaymentMethodNameException(Throwable cause) {
        super(cause);
    }
    
    /** Creates a new instance of UnknownPaymentMethodNameException */
    public UnknownPaymentMethodNameException(Message message) {
        super(message);
    }

}
