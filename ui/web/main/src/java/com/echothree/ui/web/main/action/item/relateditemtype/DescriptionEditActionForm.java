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

package com.echothree.ui.web.main.action.item.relateditemtype;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="RelatedItemTypeDescriptionEdit")
public class DescriptionEditActionForm
        extends BaseActionForm {
    
    private String relatedItemTypeName;
    private String languageIsoName;
    private String description;
    
    public void setRelatedItemTypeName(String relatedItemTypeName) {
        this.relatedItemTypeName = relatedItemTypeName;
    }
    
    public String getRelatedItemTypeName() {
        return relatedItemTypeName;
    }
    
    public String getLanguageIsoName() {
        return languageIsoName;
    }
    
    public void setLanguageIsoName(String languageIsoName) {
        this.languageIsoName = languageIsoName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
}
