// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.ui.web.main.action.content.contentpagearea;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="ContentPageAreaDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String contentCollectionName;
    private String contentSectionName;
    private String contentPageName;
    private String sortOrder;
    private String languageIsoName;
    private String parentContentSectionName;

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
     * Returns the contentSectionName.
     * @return the contentSectionName
     */
    public String getContentSectionName() {
        return contentSectionName;
    }

    /**
     * Sets the contentSectionName.
     * @param contentSectionName the contentSectionName to set
     */
    public void setContentSectionName(String contentSectionName) {
        this.contentSectionName = contentSectionName;
    }

    /**
     * Returns the contentPageName.
     * @return the contentPageName
     */
    public String getContentPageName() {
        return contentPageName;
    }

    /**
     * Sets the contentPageName.
     * @param contentPageName the contentPageName to set
     */
    public void setContentPageName(String contentPageName) {
        this.contentPageName = contentPageName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }

    public String getLanguageIsoName() {
        return languageIsoName;
    }

    /**
     * Returns the parentContentSectionName.
     * @return the parentContentSectionName
     */
    public String getParentContentSectionName() {
        return parentContentSectionName;
    }

    /**
     * Sets the parentContentSectionName.
     * @param parentContentSectionName the parentContentSectionName to set
     */
    public void setParentContentSectionName(String parentContentSectionName) {
        this.parentContentSectionName = parentContentSectionName;
    }
    
}
