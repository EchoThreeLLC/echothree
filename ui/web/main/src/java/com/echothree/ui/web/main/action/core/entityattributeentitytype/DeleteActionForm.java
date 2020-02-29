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

package com.echothree.ui.web.main.action.core.entityattributeentitytype;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="EntityAttributeEntityTypeDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String componentVendorName;
    private String entityTypeName;
    private String entityAttributeName;
    private String allowedComponentVendorName;
    private String allowedEntityTypeName;

    public String getComponentVendorName() {
        return componentVendorName;
    }

    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getEntityAttributeName() {
        return entityAttributeName;
    }

    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }

    public String getAllowedComponentVendorName() {
        return allowedComponentVendorName;
    }

    public void setAllowedComponentVendorName(String allowedComponentVendorName) {
        this.allowedComponentVendorName = allowedComponentVendorName;
    }

    public String getAllowedEntityTypeName() {
        return allowedEntityTypeName;
    }

    public void setAllowedEntityTypeName(String allowedEntityTypeName) {
        this.allowedEntityTypeName = allowedEntityTypeName;
    }
    
}
