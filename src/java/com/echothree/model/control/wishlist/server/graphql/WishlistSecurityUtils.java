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

package com.echothree.model.control.wishlist.server.graphql;

import com.echothree.control.user.wishlist.server.command.GetWishlistTypeCommand;
import com.echothree.control.user.wishlist.server.command.GetWishlistTypePrioritiesCommand;
import com.echothree.control.user.wishlist.server.command.GetWishlistTypePriorityCommand;
import com.echothree.control.user.wishlist.server.command.GetWishlistTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class WishlistSecurityUtils
        extends BaseGraphQl {

    private static class ItemSecurityUtilsHolder {
        static WishlistSecurityUtils instance = new WishlistSecurityUtils();
    }
    
    public static WishlistSecurityUtils getInstance() {
        return ItemSecurityUtilsHolder.instance;
    }

    public boolean getHasWishlistTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWishlistTypeCommand.class);
    }

    public boolean getHasWishlistTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWishlistTypesCommand.class);
    }

    public boolean getHasWishlistTypePriorityAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWishlistTypePriorityCommand.class);
    }

    public boolean getHasWishlistTypePrioritiesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetWishlistTypePrioritiesCommand.class);
    }

}
