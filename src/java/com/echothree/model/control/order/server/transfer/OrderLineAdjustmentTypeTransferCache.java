// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.order.server.transfer;

import com.echothree.model.control.order.common.transfer.OrderLineAdjustmentTypeTransfer;
import com.echothree.model.control.order.server.control.OrderLineAdjustmentControl;
import com.echothree.model.data.order.server.entity.OrderLineAdjustmentType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OrderLineAdjustmentTypeTransferCache
        extends BaseOrderTransferCache<OrderLineAdjustmentType, OrderLineAdjustmentTypeTransfer> {

    OrderLineAdjustmentControl orderLineAdjustmentControl = Session.getModelController(OrderLineAdjustmentControl.class);

    /** Creates a new instance of OrderLineAdjustmentTypeTransferCache */
    public OrderLineAdjustmentTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    public OrderLineAdjustmentTypeTransfer getOrderLineAdjustmentTypeTransfer(OrderLineAdjustmentType orderLineAdjustmentType) {
        var orderLineAdjustmentTypeTransfer = get(orderLineAdjustmentType);
        
        if(orderLineAdjustmentTypeTransfer == null) {
            var orderLineAdjustmentTypeDetail = orderLineAdjustmentType.getLastDetail();
            var orderLineAdjustmentTypeName = orderLineAdjustmentTypeDetail.getOrderLineAdjustmentTypeName();
            var isDefault = orderLineAdjustmentTypeDetail.getIsDefault();
            var sortOrder = orderLineAdjustmentTypeDetail.getSortOrder();
            var description = orderLineAdjustmentControl.getBestOrderLineAdjustmentTypeDescription(orderLineAdjustmentType, getLanguage());
            
            orderLineAdjustmentTypeTransfer = new OrderLineAdjustmentTypeTransfer(orderLineAdjustmentTypeName, isDefault, sortOrder, description);
            put(orderLineAdjustmentType, orderLineAdjustmentTypeTransfer);
        }
        
        return orderLineAdjustmentTypeTransfer;
    }
    
}
