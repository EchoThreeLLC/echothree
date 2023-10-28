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

package com.echothree.model.control.shipment.server.graphql;

import com.echothree.control.user.shipment.server.command.GetFreeOnBoardCommand;
import com.echothree.control.user.shipment.server.command.GetFreeOnBoardsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class ShipmentSecurityUtils
        implements BaseGraphQl {

    private static class AccountingSecurityUtilsHolder {
        static ShipmentSecurityUtils instance = new ShipmentSecurityUtils();
    }
    
    public static ShipmentSecurityUtils getInstance() {
        return AccountingSecurityUtilsHolder.instance;
    }

    public boolean getHasFreeOnBoardAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFreeOnBoardCommand.class);
    }

    public boolean getHasFreeOnBoardsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFreeOnBoardsCommand.class);
    }

}
