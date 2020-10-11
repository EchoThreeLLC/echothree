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

package com.echothree.model.control.wishlist.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeDescriptionTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDescription;

public class WishlistTypeDescriptionTransferCache
        extends BaseWishlistDescriptionTransferCache<WishlistTypeDescription, WishlistTypeDescriptionTransfer> {
    
    /** Creates a new instance of WishlistTypeDescriptionTransferCache */
    public WishlistTypeDescriptionTransferCache(UserVisit userVisit, WishlistControl wishlistControl) {
        super(userVisit, wishlistControl);
    }
    
    public WishlistTypeDescriptionTransfer getWishlistTypeDescriptionTransfer(WishlistTypeDescription wishlistTypeDescription) {
        WishlistTypeDescriptionTransfer wishlistTypeDescriptionTransfer = get(wishlistTypeDescription);
        
        if(wishlistTypeDescriptionTransfer == null) {
            WishlistTypeTransfer wishlistTypeTransfer = wishlistControl.getWishlistTypeTransfer(userVisit, wishlistTypeDescription.getWishlistType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, wishlistTypeDescription.getLanguage());
            
            wishlistTypeDescriptionTransfer = new WishlistTypeDescriptionTransfer(languageTransfer, wishlistTypeTransfer, wishlistTypeDescription.getDescription());
            put(wishlistTypeDescription, wishlistTypeDescriptionTransfer);
        }
        
        return wishlistTypeDescriptionTransfer;
    }
    
}
