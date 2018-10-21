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

package com.echothree.ui.web.main.action.configuration.securityrole;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="SecurityRoleDescriptionEdit")
public class DescriptionEditActionForm
        extends BaseActionForm {
    
    private String securityRoleGroupName;
    private String securityRoleName;
    private String languageIsoName;
    private String description;
    
    public void setSecurityRoleGroupName(String securityRoleGroupName) {
        this.securityRoleGroupName = securityRoleGroupName;
    }
    
    public String getSecurityRoleGroupName() {
        return securityRoleGroupName;
    }
    
    public void setSecurityRoleName(String securityRoleName) {
        this.securityRoleName = securityRoleName;
    }
    
    public String getSecurityRoleName() {
        return securityRoleName;
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
