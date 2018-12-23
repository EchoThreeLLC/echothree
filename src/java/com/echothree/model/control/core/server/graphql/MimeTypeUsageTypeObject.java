// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("mime type usage type object")
@GraphQLName("MimeTypeUsageType")
public class MimeTypeUsageTypeObject  {
    
    private final MimeTypeUsageType mimeTypeUsageType; // Always Present
    
    public MimeTypeUsageTypeObject(MimeTypeUsageType mimeTypeUsageType) {
        this.mimeTypeUsageType = mimeTypeUsageType;
    }

    @GraphQLField
    @GraphQLDescription("mime type usage type name")
    @GraphQLNonNull
    public String getMimeTypeUsageTypeName() {
        return mimeTypeUsageType.getMimeTypeUsageTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return mimeTypeUsageType.getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return mimeTypeUsageType.getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        UserControl userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return coreControl.getBestMimeTypeUsageTypeDescription(mimeTypeUsageType, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
