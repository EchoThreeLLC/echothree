// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.item.itemprice;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetCurrencyChoicesResult;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.common.result.GetInventoryConditionChoicesResult;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.result.GetUnitOfMeasureTypeChoicesResult;
import com.echothree.model.control.accounting.common.choice.CurrencyChoicesBean;
import com.echothree.model.control.inventory.common.InventoryConstants;
import com.echothree.model.control.inventory.common.choice.InventoryConditionChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemPriceAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private InventoryConditionChoicesBean inventoryConditionChoices;
    private UnitOfMeasureTypeChoicesBean unitOfMeasureTypeChoices;
    private CurrencyChoicesBean currencyChoices;
    
    private String itemName;
    private String inventoryConditionChoice;
    private String unitOfMeasureTypeChoice;
    private String currencyChoice;
    private String unitPrice;
    private String maximumUnitPrice;
    private String minimumUnitPrice;
    private String unitPriceIncrement;
    
    private void setupInventoryConditionChoices() {
        if(inventoryConditionChoices == null) {
            try {
                var form = InventoryUtil.getHome().getGetInventoryConditionChoicesForm();
                
                form.setInventoryConditionUseTypeName(InventoryConstants.InventoryConditionUseType_PURCHASE_ORDER);
                form.setDefaultInventoryConditionChoice(inventoryConditionChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                var commandResult = InventoryUtil.getHome().getInventoryConditionChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetInventoryConditionChoicesResult)executionResult.getResult();
                inventoryConditionChoices = result.getInventoryConditionChoices();
                
                if(inventoryConditionChoice == null) {
                    inventoryConditionChoice = inventoryConditionChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, inventoryConditionChoices remains null, no default
            }
        }
    }
    
    private void setupUnitOfMeasureTypeChoices() {
        if(unitOfMeasureTypeChoices == null) {
            try {
                var form = UomUtil.getHome().getGetUnitOfMeasureTypeChoicesForm();
                
                form.setItemName(itemName);
                form.setDefaultUnitOfMeasureTypeChoice(unitOfMeasureTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                var commandResult = UomUtil.getHome().getUnitOfMeasureTypeChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetUnitOfMeasureTypeChoicesResult)executionResult.getResult();
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
    
    public void setupCurrencyChoices() {
        if(currencyChoices == null) {
            try {
                var form = AccountingUtil.getHome().getGetCurrencyChoicesForm();
                
                form.setDefaultCurrencyChoice(currencyChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());

                var commandResult = AccountingUtil.getHome().getCurrencyChoices(userVisitPK, form);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetCurrencyChoicesResult)executionResult.getResult();
                currencyChoices = result.getCurrencyChoices();
                
                if(currencyChoice == null) {
                    currencyChoice = currencyChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, currencyChoices remains null, no default
            }
        }
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
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
        if(inventoryConditionChoices != null) {
            choices = convertChoices(inventoryConditionChoices);
        }
        
        return choices;
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
    
    public void setCurrencyChoice(String currencyChoice) {
        this.currencyChoice = currencyChoice;
    }
    
    public String getCurrencyChoice() {
        setupCurrencyChoices();
        
        return currencyChoice;
    }
    
    public List<LabelValueBean> getCurrencyChoices() {
        List<LabelValueBean> choices = null;
        
        setupCurrencyChoices();
        if(currencyChoices != null) {
            choices = convertChoices(currencyChoices);
        }
        
        return choices;
    }
    
    public String getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public String getMaximumUnitPrice() {
        return maximumUnitPrice;
    }
    
    public void setMaximumUnitPrice(String maximumUnitPrice) {
        this.maximumUnitPrice = maximumUnitPrice;
    }
    
    public String getMinimumUnitPrice() {
        return minimumUnitPrice;
    }
    
    public void setMinimumUnitPrice(String minimumUnitPrice) {
        this.minimumUnitPrice = minimumUnitPrice;
    }
    
    public String getUnitPriceIncrement() {
        return unitPriceIncrement;
    }
    
    public void setUnitPriceIncrement(String unitPriceIncrement) {
        this.unitPriceIncrement = unitPriceIncrement;
    }
    
}
