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

package com.echothree.ui.web.main.action.inventory.inventoryconditionuse;

import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.form.GetInventoryConditionChoicesForm;
import com.echothree.control.user.inventory.common.result.GetInventoryConditionChoicesResult;
import com.echothree.model.control.inventory.common.choice.InventoryConditionChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="InventoryConditionUseAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private InventoryConditionChoicesBean inventoryConditionChoices;
    
    private String inventoryConditionChoice;
    private String inventoryConditionUseTypeName;
    private Boolean isDefault;
    
    private void setupInventoryConditionChoices() {
        if(inventoryConditionChoices == null) {
            try {
                GetInventoryConditionChoicesForm form = InventoryUtil.getHome().getGetInventoryConditionChoicesForm();
                
                form.setDefaultInventoryConditionChoice(inventoryConditionChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = InventoryUtil.getHome().getInventoryConditionChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetInventoryConditionChoicesResult getInventoryConditionChoicesResult = (GetInventoryConditionChoicesResult)executionResult.getResult();
                inventoryConditionChoices = getInventoryConditionChoicesResult.getInventoryConditionChoices();
                
                if(inventoryConditionChoice == null)
                    inventoryConditionChoice = inventoryConditionChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, inventoryConditionChoices remains null, no default
            }
        }
    }
    
    public String getInventoryConditionChoice() {
        setupInventoryConditionChoices();
        return inventoryConditionChoice;
    }
    
    public void setInventoryConditionChoice(String inventoryConditionChoice) {
        this.inventoryConditionChoice = inventoryConditionChoice;
    }
    
    public List<LabelValueBean> getInventoryConditionChoices() {
        List<LabelValueBean> choices = null;
        
        setupInventoryConditionChoices();
        if(inventoryConditionChoices != null)
            choices = convertChoices(inventoryConditionChoices);
        
        return choices;
    }
    
    public String getInventoryConditionUseTypeName() {
        return inventoryConditionUseTypeName;
    }
    
    public void setInventoryConditionUseTypeName(String inventoryConditionUseTypeName) {
        this.inventoryConditionUseTypeName = inventoryConditionUseTypeName;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = Boolean.FALSE;
    }
    
}
