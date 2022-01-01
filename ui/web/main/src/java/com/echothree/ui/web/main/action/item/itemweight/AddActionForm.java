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

package com.echothree.ui.web.main.action.item.itemweight;

import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypeChoicesForm;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemWeightAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private UnitOfMeasureTypeChoicesBean unitOfMeasureTypeChoices;
    private UnitOfMeasureTypeChoicesBean weightUnitOfMeasureTypeChoices;
    
    private String itemName;
    private String unitOfMeasureTypeChoice;
    private String weight;
    private String weightUnitOfMeasureTypeChoice;
    
    private void setupUnitOfMeasureTypeChoices() {
        if(unitOfMeasureTypeChoices == null) {
            try {
                GetUnitOfMeasureTypeChoicesForm form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setItemName(itemName);
                form.setDefaultUnitOfMeasureTypeChoice(unitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUnitOfMeasureTypeChoicesResult result = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                unitOfMeasureTypeChoices = result.getUnitOfMeasureTypeChoices();
                
                if(unitOfMeasureTypeChoice == null) {
                    unitOfMeasureTypeChoice = unitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
    private void setupWeightUnitOfMeasureTypeChoices() {
        if(weightUnitOfMeasureTypeChoices == null) {
            try {
                GetUnitOfMeasureTypeChoicesForm form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setDefaultUnitOfMeasureTypeChoice(weightUnitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                form.setUnitOfMeasureKindUseTypeName(UomConstants.UnitOfMeasureKindUseType_WEIGHT);
                
                CommandResult commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetUnitOfMeasureTypeChoicesResult getUnitOfMeasureTypeChoicesResult = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
                weightUnitOfMeasureTypeChoices = getUnitOfMeasureTypeChoicesResult.getUnitOfMeasureTypeChoices();
                
                if(weightUnitOfMeasureTypeChoice == null) {
                    weightUnitOfMeasureTypeChoice = weightUnitOfMeasureTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, unitOfMeasureTypeChoices remains null, no default
            }
        }
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getUnitOfMeasureTypeChoice() {
        setupUnitOfMeasureTypeChoices();
        
        return unitOfMeasureTypeChoice;
    }
    
    public void setUnitOfMeasureTypeChoice(String unitOfMeasureTypeChoice) {
        this.unitOfMeasureTypeChoice = unitOfMeasureTypeChoice;
    }
    
    public List<LabelValueBean> getUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupUnitOfMeasureTypeChoices();
        if(unitOfMeasureTypeChoices != null) {
            choices = convertChoices(unitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getWeight() {
        return weight;
    }
    
    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    public List<LabelValueBean> getWeightUnitOfMeasureTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupWeightUnitOfMeasureTypeChoices();
        if(weightUnitOfMeasureTypeChoices != null) {
            choices = convertChoices(weightUnitOfMeasureTypeChoices);
        }
        
        return choices;
    }
    
    public String getWeightUnitOfMeasureTypeChoice() {
        setupWeightUnitOfMeasureTypeChoices();
        return weightUnitOfMeasureTypeChoice;
    }
    
    public void setWeightUnitOfMeasureTypeChoice(String weightUnitOfMeasureTypeChoice) {
        this.weightUnitOfMeasureTypeChoice = weightUnitOfMeasureTypeChoice;
    }
    
}
