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

package com.echothree.model.control.accounting.server.graphql;

import com.echothree.control.user.accounting.server.command.GetCurrenciesCommand;
import com.echothree.control.user.accounting.server.command.GetCurrencyCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountsCommand;
import com.echothree.control.user.accounting.server.command.GetItemAccountingCategoriesCommand;
import com.echothree.control.user.accounting.server.command.GetItemAccountingCategoryCommand;
import com.echothree.control.user.accounting.server.command.GetSymbolPositionCommand;
import com.echothree.control.user.accounting.server.command.GetSymbolPositionsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class AccountingSecurityUtils
        extends BaseGraphQl {

    private static class AccountingSecurityUtilsHolder {
        static AccountingSecurityUtils instance = new AccountingSecurityUtils();
    }
    
    public static AccountingSecurityUtils getInstance() {
        return AccountingSecurityUtilsHolder.instance;
    }

    public boolean getHasSymbolPositionAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetSymbolPositionCommand.class);
    }

    public boolean getHasSymbolPositionsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetSymbolPositionsCommand.class);
    }

    public boolean getHasCurrencyAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetCurrencyCommand.class);
    }

    public boolean getHasCurrenciesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetCurrenciesCommand.class);
    }

    public boolean getHasItemAccountingCategoryAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemAccountingCategoryCommand.class);
    }

    public boolean getHasItemAccountingCategoriesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemAccountingCategoriesCommand.class);
    }

    public boolean getHasGlAccountAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetGlAccountCommand.class);
    }

    public boolean getHasGlAccountsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetGlAccountsCommand.class);
    }

}
