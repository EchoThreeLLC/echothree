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

package com.echothree.ui.web.main.action.content.contentcategory;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="ContentCategoryDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String contentCollectionName;
    private String contentCatalogName;
    private String contentCategoryName;
    private String parentContentCategoryName;

    /**
     * Returns the contentCollectionName.
     * @return the contentCollectionName
     */
    public String getContentCollectionName() {
        return contentCollectionName;
    }

    /**
     * Sets the contentCollectionName.
     * @param contentCollectionName the contentCollectionName to set
     */
    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }

    /**
     * Returns the contentCatalogName.
     * @return the contentCatalogName
     */
    public String getContentCatalogName() {
        return contentCatalogName;
    }

    /**
     * Sets the contentCatalogName.
     * @param contentCatalogName the contentCatalogName to set
     */
    public void setContentCatalogName(String contentCatalogName) {
        this.contentCatalogName = contentCatalogName;
    }

    /**
     * Returns the contentCategoryName.
     * @return the contentCategoryName
     */
    public String getContentCategoryName() {
        return contentCategoryName;
    }

    /**
     * Sets the contentCategoryName.
     * @param contentCategoryName the contentCategoryName to set
     */
    public void setContentCategoryName(String contentCategoryName) {
        this.contentCategoryName = contentCategoryName;
    }

    /**
     * Returns the parentContentCategoryName.
     * @return the parentContentCategoryName
     */
    public String getParentContentCategoryName() {
        return parentContentCategoryName;
    }

    /**
     * Sets the parentContentCategoryName.
     * @param parentContentCategoryName the parentContentCategoryName to set
     */
    public void setParentContentCategoryName(String parentContentCategoryName) {
        this.parentContentCategoryName = parentContentCategoryName;
    }
    
}
