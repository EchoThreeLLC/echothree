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

package com.echothree.control.user.offer.common.edit;

import com.echothree.util.common.form.BaseEdit;

public interface OfferItemPriceEdit
        extends BaseEdit {
    
    /** Required then fixed pricing is used */
    String getUnitPrice();
    /** Required then fixed pricing is used */
    void setUnitPrice(String unitPrice);
    
    /** Required when variable pricing is used */
    String getMinimumUnitPrice();
    /** Required when variable pricing is used */
    void setMinimumUnitPrice(String minimumUnitPrice);
    
    /** Required when variable pricing is used */
    String getMaximumUnitPrice();
    /** Required when variable pricing is used */
    void setMaximumUnitPrice(String maximumUnitPrice);
    
    /** Required when variable pricing is used */
    String getUnitPriceIncrement();
    /** Required when variable pricing is used */
    void setUnitPriceIncrement(String unitPriceIncrement);
    
}
