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

package com.echothree.ui.web.main.action.content.contentpage;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="ContentPageDescriptionEdit")
public class DescriptionEditActionForm
        extends BaseActionForm {
    
    private String contentCollectionName;
    private String contentSectionName;
    private String contentPageName;
    private String languageIsoName;
    private String parentContentSectionName;
    private String description;
    
    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }
    
    public String getContentCollectionName() {
        return contentCollectionName;
    }
    
    public void setContentSectionName(String contentSectionName) {
        this.contentSectionName = contentSectionName;
    }
    
    public String getContentSectionName() {
        return contentSectionName;
    }
    
    public void setContentPageName(String contentPageName) {
        this.contentPageName = contentPageName;
    }
    
    public String getContentPageName() {
        return contentPageName;
    }
    
    public String getLanguageIsoName() {
        return languageIsoName;
    }
    
    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }
    
    public void setParentContentSectionName(String parentContentSectionName) {
        this.parentContentSectionName = parentContentSectionName;
    }
    
    public String getParentContentSectionName() {
        return parentContentSectionName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
