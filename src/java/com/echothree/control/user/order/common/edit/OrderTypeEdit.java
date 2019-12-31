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

package com.echothree.control.user.order.common.edit;

import com.echothree.control.user.order.common.spec.OrderTypeSpec;

public interface OrderTypeEdit
        extends OrderTypeSpec, OrderTypeDescriptionEdit {
    
    String getParentOrderTypeName();
    void setParentOrderTypeName(String parentOrderTypeName);
    
    String getOrderSequenceTypeName();
    void setOrderSequenceTypeName(String orderSequenceTypeName);

    String getOrderWorkflowName();
    void setOrderWorkflowName(String orderWorkflowName);

    String getOrderWorkflowEntranceName();
    void setOrderWorkflowEntranceName(String orderWorkflowEntranceName);

    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
