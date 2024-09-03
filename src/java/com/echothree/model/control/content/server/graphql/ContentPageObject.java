// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;

@GraphQLDescription("content page object")
@GraphQLName("ContentPage")
public class ContentPageObject
        extends BaseEntityInstanceObject {
    
    private final ContentPage contentPage; // Always Present
    
    public ContentPageObject(ContentPage contentPage) {
        super(contentPage.getPrimaryKey());
        
        this.contentPage = contentPage;
    }

    private ContentPageDetail contentPageDetail; // Optional, use getContentPageDetail()
    
    private ContentPageDetail getContentPageDetail() {
        if(contentPageDetail == null) {
            contentPageDetail = contentPage.getLastDetail();
        }
        
        return contentPageDetail;
    }

    @GraphQLField
    @GraphQLDescription("content section")
    public ContentSectionObject getContentSection(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getHasContentSectionAccess(env) ? new ContentSectionObject(getContentPageDetail().getContentSection()) : null;
    }

    @GraphQLField
    @GraphQLDescription("content page name")
    @GraphQLNonNull
    public String getContentPageName() {
        return getContentPageDetail().getContentPageName();
    }

    @GraphQLField
    @GraphQLDescription("content page layout")
    public ContentPageLayoutObject getContentPageLayout(final DataFetchingEnvironment env) {
        return ContentSecurityUtils.getHasContentPageLayoutAccess(env) ? new ContentPageLayoutObject(getContentPageDetail().getContentPageLayout()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getContentPageDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getContentPageDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return contentControl.getBestContentPageDescription(contentPage, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
    @GraphQLField
    @GraphQLDescription("content page areas")
    @GraphQLNonNull
    public List<ContentPageAreaObject> getContentPageAreas(final DataFetchingEnvironment env) {
        var contentControl = Session.getModelController(ContentControl.class);
        var userControl = Session.getModelController(UserControl.class);
        var preferredLanguage = userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env));
        var entities = contentControl.getContentPageLayoutAreasByContentPageLayout(getContentPageDetail().getContentPageLayout());
        List<ContentPageAreaObject> contentPageAreas = new ArrayList<>(entities.size());
        
        entities.forEach((entity) -> {
            var contentPageArea = contentControl.getBestContentPageArea(contentPage, entity, preferredLanguage);

            if(contentPageArea != null) {
                contentPageAreas.add(new ContentPageAreaObject(contentPageArea));
            }
        });
        
        return contentPageAreas;
    }
    
}
