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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemDescriptionTypeDescriptionTransferCache
        extends BaseItemDescriptionTransferCache<ItemDescriptionTypeDescription, ItemDescriptionTypeDescriptionTransfer> {
    
    /** Creates a new instance of ItemDescriptionTypeDescriptionTransferCache */
    public ItemDescriptionTypeDescriptionTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public ItemDescriptionTypeDescriptionTransfer getTransfer(ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        ItemDescriptionTypeDescriptionTransfer itemDescriptionTypeDescriptionTransfer = get(itemDescriptionTypeDescription);
        
        if(itemDescriptionTypeDescriptionTransfer == null) {
            ItemDescriptionTypeTransfer itemDescriptionTypeTransfer = itemControl.getItemDescriptionTypeTransfer(userVisit, itemDescriptionTypeDescription.getItemDescriptionType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, itemDescriptionTypeDescription.getLanguage());
            
            itemDescriptionTypeDescriptionTransfer = new ItemDescriptionTypeDescriptionTransfer(languageTransfer, itemDescriptionTypeTransfer, itemDescriptionTypeDescription.getDescription());
            put(itemDescriptionTypeDescription, itemDescriptionTypeDescriptionTransfer);
        }
        
        return itemDescriptionTypeDescriptionTransfer;
    }
    
}
