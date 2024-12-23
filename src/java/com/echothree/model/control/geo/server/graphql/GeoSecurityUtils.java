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

package com.echothree.model.control.geo.server.graphql;

import com.echothree.control.user.geo.server.command.GetGeoCodeCommand;
import com.echothree.control.user.geo.server.command.GetGeoCodeScopeCommand;
import com.echothree.control.user.geo.server.command.GetGeoCodeScopesCommand;
import com.echothree.control.user.geo.server.command.GetGeoCodeTypeCommand;
import com.echothree.control.user.geo.server.command.GetGeoCodeTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface GeoSecurityUtils {

    static boolean getHasGeoCodeTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGeoCodeTypeCommand.class);
    }

    static boolean getHasGeoCodeTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGeoCodeTypesCommand.class);
    }

    static boolean getHasGeoCodeScopeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGeoCodeScopeCommand.class);
    }

    static boolean getHasGeoCodeScopesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGeoCodeScopesCommand.class);
    }

    static boolean getHasGeoCodeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGeoCodeCommand.class);
    }

}
