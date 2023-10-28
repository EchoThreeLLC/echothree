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

package com.echothree.model.control.payment.server.graphql;

import com.echothree.control.user.payment.server.command.GetPaymentProcessorActionTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorResultCodeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionCodesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionsCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCodeTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class PaymentSecurityUtils
        implements BaseGraphQl {

    private static class PaymentSecurityUtilsHolder {
        static PaymentSecurityUtils instance = new PaymentSecurityUtils();
    }
    
    public static PaymentSecurityUtils getInstance() {
        return PaymentSecurityUtilsHolder.instance;
    }

    public boolean getHasPaymentProcessorTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTypeCommand.class);
    }

    public boolean getHasPaymentProcessorTransactionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTransactionsCommand.class);
    }

    public boolean getHasPaymentProcessorTransactionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTransactionCommand.class);
    }

    public boolean getHasPaymentProcessorTransactionCodesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTransactionCodesCommand.class);
    }

    public boolean getHasPaymentProcessorAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorCommand.class);
    }

    public boolean getHasPaymentProcessorActionTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorActionTypeCommand.class);
    }

    public boolean getHasPaymentProcessorResultCodeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorResultCodeCommand.class);
    }

    public boolean getHasPaymentProcessorTypeCodeTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPaymentProcessorTypeCodeTypeCommand.class);
    }

}
