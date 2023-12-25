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

package com.echothree.ui.web.main.action.content.contentcategoryitem;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="ContentCategoryItemDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String contentCollectionName;
    private String contentCatalogName;
    private String contentCategoryName;
    private String itemName;
    private String inventoryConditionName;
    private String unitOfMeasureTypeName;
    private String currencyIsoName;

    public String getContentCollectionName() {
        return contentCollectionName;
    }

    public void setContentCollectionName(String contentCollectionName) {
        this.contentCollectionName = contentCollectionName;
    }

    public String getContentCatalogName() {
        return contentCatalogName;
    }

    public void setContentCatalogName(String contentCatalogName) {
        this.contentCatalogName = contentCatalogName;
    }

    public String getContentCategoryName() {
        return contentCategoryName;
    }

    public void setContentCategoryName(String contentCategoryName) {
        this.contentCategoryName = contentCategoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getInventoryConditionName() {
        return inventoryConditionName;
    }

    public void setInventoryConditionName(String inventoryConditionName) {
        this.inventoryConditionName = inventoryConditionName;
    }

    public String getUnitOfMeasureTypeName() {
        return unitOfMeasureTypeName;
    }

    public void setUnitOfMeasureTypeName(String unitOfMeasureTypeName) {
        this.unitOfMeasureTypeName = unitOfMeasureTypeName;
    }

    public String getCurrencyIsoName() {
        return currencyIsoName;
    }

    public void setCurrencyIsoName(String currencyIsoName) {
        this.currencyIsoName = currencyIsoName;
    }
    
}
