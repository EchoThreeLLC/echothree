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

package com.echothree.control.user.wishlist.server.command;

import com.echothree.control.user.wishlist.remote.form.GetWishlistTypesForm;
import com.echothree.control.user.wishlist.remote.result.GetWishlistTypesResult;
import com.echothree.control.user.wishlist.remote.result.WishlistResultFactory;
import com.echothree.model.control.wishlist.server.WishlistControl;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetWishlistTypesCommand
        extends BaseSimpleCommand<GetWishlistTypesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                ));
    }
    
    /** Creates a new instance of GetWishlistTypesCommand */
    public GetWishlistTypesCommand(UserVisitPK userVisitPK, GetWishlistTypesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetWishlistTypesResult result = WishlistResultFactory.getGetWishlistTypesResult();
        WishlistControl wishlistControl = (WishlistControl)Session.getModelController(WishlistControl.class);
        
        result.setWishlistTypes(wishlistControl.getWishlistTypeTransfers(getUserVisit()));
        
        return result;
    }
    
}
