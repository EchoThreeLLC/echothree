// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.content.server.entity.ContentPageAreaType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("content page area type")
@GraphQLName("ContentPageAreaType")
public class ContentPageAreaTypeObject
        extends BaseEntityInstanceObject {
    
    private final ContentPageAreaType contentPageAreaType; // Always Present
    
    public ContentPageAreaTypeObject(ContentPageAreaType contentPageAreaType) {
        super(contentPageAreaType.getPrimaryKey());
        
        this.contentPageAreaType = contentPageAreaType;
    }

    @GraphQLField
    @GraphQLDescription("content page area type name")
    @GraphQLNonNull
    public String getContentPageAreaTypeName() {
        return contentPageAreaType.getContentPageAreaTypeName();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contentControl = (ContentControl)Session.getModelController(ContentControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return contentControl.getBestContentPageAreaTypeDescription(contentPageAreaType, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
