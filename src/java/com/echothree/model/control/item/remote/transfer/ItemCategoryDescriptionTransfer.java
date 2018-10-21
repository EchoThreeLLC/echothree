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

package com.echothree.model.control.item.remote.transfer;

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class ItemCategoryDescriptionTransfer
        extends BaseTransfer {
    
    private LanguageTransfer language;
    private ItemCategoryTransfer itemCategory;
    private String description;
    
    /** Creates a new instance of ItemCategoryDescriptionTransfer */
    public ItemCategoryDescriptionTransfer(LanguageTransfer language, ItemCategoryTransfer itemCategory, String description) {
        this.language = language;
        this.itemCategory = itemCategory;
        this.description = description;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public ItemCategoryTransfer getItemCategory() {
        return itemCategory;
    }
    
    public void setItemCategory(ItemCategoryTransfer itemCategory) {
        this.itemCategory = itemCategory;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
