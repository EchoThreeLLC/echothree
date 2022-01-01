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

package com.echothree.model.control.content.server.graphql;

import com.echothree.control.user.content.server.command.GetContentCatalogCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogItemCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogItemsCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogsCommand;
import com.echothree.control.user.content.server.command.GetContentCategoriesCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryItemCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryItemsCommand;
import com.echothree.control.user.content.server.command.GetContentCollectionCommand;
import com.echothree.control.user.content.server.command.GetContentPageCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutCommand;
import com.echothree.control.user.content.server.command.GetContentPagesCommand;
import com.echothree.control.user.content.server.command.GetContentSectionCommand;
import com.echothree.control.user.content.server.command.GetContentSectionsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class ContentSecurityUtils
        extends BaseGraphQl {

    private static class ContentSecurityUtilsHolder {
        static ContentSecurityUtils instance = new ContentSecurityUtils();
    }
    
    public static ContentSecurityUtils getInstance() {
        return ContentSecurityUtilsHolder.instance;
    }
    
    public boolean getHasContentCollectionAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentCollectionCommand.class);
    }
    
    public boolean getHasContentCatalogsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentCatalogsCommand.class);
    }
    
    public boolean getHasContentCatalogAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentCatalogCommand.class);
    }
    
    public boolean getHasContentCatalogItemsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentCatalogItemsCommand.class);
    }
    
    public boolean getHasContentCatalogItemAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentCatalogItemCommand.class);
    }

    public boolean getHasContentCategoriesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentCategoriesCommand.class);
    }
        
    public boolean getHasContentCategoryAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentCategoryCommand.class);
    }
        
     public boolean getHasContentCategoryItemsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentCategoryItemsCommand.class);
    }
    
    public boolean getHasContentCategoryItemAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentCategoryItemCommand.class);
    }

   public boolean getHasContentSectionsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentSectionsCommand.class);
    }

    public boolean getHasContentSectionAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentSectionCommand.class);
    }
    
    public boolean getHasContentPageLayoutAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentPageLayoutCommand.class);
    }
    
    public boolean getHasContentPagesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentPagesCommand.class);
    }
    
    public boolean getHasContentPageAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetContentPageCommand.class);
    }
    
}
