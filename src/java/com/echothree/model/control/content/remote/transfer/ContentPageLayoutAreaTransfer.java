// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.content.remote.transfer;

import com.echothree.util.remote.transfer.BaseTransfer;

public class ContentPageLayoutAreaTransfer
        extends BaseTransfer {

    private ContentPageLayoutTransfer contentPageLayout;
    private ContentPageAreaTypeTransfer contentPageAreaType;
    private Boolean showDescriptionField;
    private Integer sortOrder;
    private String description;

    /** Creates a new instance of ContentPageLayoutAreaTransfer */
    public ContentPageLayoutAreaTransfer(ContentPageLayoutTransfer contentPageLayout, ContentPageAreaTypeTransfer contentPageAreaType,
            Boolean showDescriptionField, Integer sortOrder, String description) {
        this.contentPageLayout = contentPageLayout;
        this.contentPageAreaType = contentPageAreaType;
        this.showDescriptionField = showDescriptionField;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the contentPageLayout
     */
    public ContentPageLayoutTransfer getContentPageLayout() {
        return contentPageLayout;
    }

    /**
     * @param contentPageLayout the contentPageLayout to set
     */
    public void setContentPageLayout(ContentPageLayoutTransfer contentPageLayout) {
        this.contentPageLayout = contentPageLayout;
    }

    /**
     * @return the contentPageAreaType
     */
    public ContentPageAreaTypeTransfer getContentPageAreaType() {
        return contentPageAreaType;
    }

    /**
     * @param contentPageAreaType the contentPageAreaType to set
     */
    public void setContentPageAreaType(ContentPageAreaTypeTransfer contentPageAreaType) {
        this.contentPageAreaType = contentPageAreaType;
    }

    /**
     * @return the showDescriptionField
     */
    public Boolean getShowDescriptionField() {
        return showDescriptionField;
    }

    /**
     * @param showDescriptionField the showDescriptionField to set
     */
    public void setShowDescriptionField(Boolean showDescriptionField) {
        this.showDescriptionField = showDescriptionField;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
