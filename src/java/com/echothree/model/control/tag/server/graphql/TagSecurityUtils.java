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

package com.echothree.model.control.tag.server.graphql;

import com.echothree.control.user.tag.server.command.GetTagCommand;
import com.echothree.control.user.tag.server.command.GetTagScopeCommand;
import com.echothree.control.user.tag.server.command.GetTagScopeEntityTypeCommand;
import com.echothree.control.user.tag.server.command.GetTagScopeEntityTypesCommand;
import com.echothree.control.user.tag.server.command.GetTagScopesCommand;
import com.echothree.control.user.tag.server.command.GetTagsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class TagSecurityUtils
        extends BaseGraphQl {

    private static class InventorySecurityUtilsHolder {
        static TagSecurityUtils instance = new TagSecurityUtils();
    }
    
    public static TagSecurityUtils getInstance() {
        return InventorySecurityUtilsHolder.instance;
    }

    public boolean getHasTagScopeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetTagScopeCommand.class);
    }

    public boolean getHasTagScopesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetTagScopesCommand.class);
    }

    public boolean getHasTagScopeEntityTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetTagScopeEntityTypeCommand.class);
    }

    public boolean getHasTagScopeEntityTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetTagScopeEntityTypesCommand.class);
    }

    public boolean getHasTagAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetTagCommand.class);
    }

    public boolean getHasTagsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetTagsCommand.class);
    }

}
