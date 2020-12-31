// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.search.server.graphql;

import com.echothree.control.user.item.server.command.GetItemCommand;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("item result object")
@GraphQLName("ItemResult")
public class ItemResultObject {
    
    private final Item item;
    
    public ItemResultObject(Item item) {
        this.item = item;
    }

    private Boolean hasItemAccess;

    private boolean getHasItemAccess(final DataFetchingEnvironment env) {
        if(hasItemAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetItemCommand(context.getUserVisitPK(), null);

            baseSingleEntityCommand.security();

            hasItemAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }

        return hasItemAccess;
    }

    @GraphQLField
    @GraphQLDescription("item")
    @GraphQLNonNull
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return getHasItemAccess(env) ? new ItemObject(item) : null;
    }
    
}
