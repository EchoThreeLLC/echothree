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

package com.echothree.ui.web.main.action.core.entitylistitem;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="EntityListItemDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String componentVendorName;
    private String entityTypeName;
    private String entityAttributeName;
    private String entityListItemName;
    
    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }
    
    public String getComponentVendorName() {
        return componentVendorName;
    }
    
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }
    
    public String getEntityTypeName() {
        return entityTypeName;
    }
    
    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }
    
    public String getEntityAttributeName() {
        return entityAttributeName;
    }
    
    public void setEntityListItemName(String entityListItemName) {
        this.entityListItemName = entityListItemName;
    }
    
    public String getEntityListItemName() {
        return entityListItemName;
    }
    
}
