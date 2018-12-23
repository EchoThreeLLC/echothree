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

package com.echothree.ui.web.main.action.chain.chain;

import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="ChainDescriptionAdd")
public class DescriptionAddActionForm
        extends BaseLanguageActionForm {
    
    private String chainKindName;
    private String chainTypeName;
    private String chainName;
    private String description;
    
    public void setChainKindName(String chainKindName) {
        this.chainKindName = chainKindName;
    }
    
    public String getChainKindName() {
        return chainKindName;
    }
    
    public void setChainTypeName(String chainTypeName) {
        this.chainTypeName = chainTypeName;
    }
    
    public String getChainTypeName() {
        return chainTypeName;
    }
    
    public void setChainName(String chainName) {
        this.chainName = chainName;
    }
    
    public String getChainName() {
        return chainName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
}
