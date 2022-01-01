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

package com.echothree.control.user.item.common.edit;

import com.echothree.control.user.accounting.common.spec.ItemAccountingCategorySpec;
import com.echothree.control.user.item.common.spec.ItemCategorySpec;
import com.echothree.control.user.item.common.spec.ItemSpec;
import com.echothree.control.user.vendor.common.spec.ItemPurchasingCategorySpec;
import com.echothree.util.common.form.BaseEdit;

public interface ItemEdit
        extends BaseEdit, ItemSpec, ItemCategorySpec, ItemAccountingCategorySpec, ItemPurchasingCategorySpec {
    
    String getShippingChargeExempt();
    void setShippingChargeExempt(String shippingChargeExempt);
    
    String getShippingStartTime();
    void setShippingStartTime(String shippingStartTime);
    
    String getShippingEndTime();
    void setShippingEndTime(String shippingEndTime);
    
    String getSalesOrderStartTime();
    void setSalesOrderStartTime(String salesOrderStartTime);
    
    String getSalesOrderEndTime();
    void setSalesOrderEndTime(String salesOrderEndTime);
    
    String getPurchaseOrderStartTime();
    void setPurchaseOrderStartTime(String purchaseOrderStartTime);
    
    String getPurchaseOrderEndTime();
    void setPurchaseOrderEndTime(String purchaseOrderEndTime);
    
    String getAllowClubDiscounts();
    void setAllowClubDiscounts(String allowClubDiscounts);
    
    String getAllowCouponDiscounts();
    void setAllowCouponDiscounts(String allowCouponDiscounts);
    
    String getAllowAssociatePayments();
    void setAllowAssociatePayments(String allowAssociatePayments);
    
    String getCancellationPolicyName();
    void setCancellationPolicyName(String cancellationPolicyName);
    
    String getReturnPolicyName();
    void setReturnPolicyName(String returnPolicyName);
    
}
