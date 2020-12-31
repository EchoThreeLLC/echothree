// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.selector.selectornode;

import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="SelectorNodeDescriptionAdd")
public class DescriptionAddActionForm
        extends BaseLanguageActionForm {
    
    private String selectorKindName;
    private String selectorTypeName;
    private String selectorName;
    private String selectorNodeName;
    private String description;
    
    public void setSelectorKindName(String selectorKindName) {
        this.selectorKindName = selectorKindName;
    }
    
    public String getSelectorKindName() {
        return selectorKindName;
    }
    
    public void setSelectorTypeName(String selectorTypeName) {
        this.selectorTypeName = selectorTypeName;
    }
    
    public String getSelectorTypeName() {
        return selectorTypeName;
    }
    
    public void setSelectorName(String selectorName) {
        this.selectorName = selectorName;
    }
    
    public String getSelectorName() {
        return selectorName;
    }
    
    public void setSelectorNodeName(String selectorNodeName) {
        this.selectorNodeName = selectorNodeName;
    }
    
    public String getSelectorNodeName() {
        return selectorNodeName;
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
        
        selectorKindName = request.getParameter(ParameterConstants.SELECTOR_KIND_NAME);
        selectorTypeName = request.getParameter(ParameterConstants.SELECTOR_TYPE_NAME);
        selectorName = request.getParameter(ParameterConstants.SELECTOR_NAME);
        selectorNodeName = request.getParameter(ParameterConstants.SELECTOR_NODE_NAME);
    }
    
}
