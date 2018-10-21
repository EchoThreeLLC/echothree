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

package com.echothree.model.control.order.server.logic;

import com.echothree.model.control.order.common.exception.DuplicateOrderTimeException;
import com.echothree.model.control.order.common.exception.UnknownOrderTimeException;
import com.echothree.model.control.order.common.exception.UnknownOrderTimeTypeNameException;
import com.echothree.model.control.order.remote.transfer.OrderTimeTransfer;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderDetail;
import com.echothree.model.data.order.server.entity.OrderTime;
import com.echothree.model.data.order.server.entity.OrderTimeType;
import com.echothree.model.data.order.server.entity.OrderType;
import com.echothree.model.data.order.server.value.OrderTimeValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class OrderTimeLogic
        extends BaseLogic {

    private OrderTimeLogic() {
        super();
    }

    private static class OrderTimeLogicHolder {
        static OrderTimeLogic instance = new OrderTimeLogic();
    }

    public static OrderTimeLogic getInstance() {
        return OrderTimeLogicHolder.instance;
    }

    private String getOrderTypeName(OrderType orderType) {
        return orderType.getLastDetail().getOrderTypeName();
    }

    public OrderTimeType getOrderTimeTypeByName(final ExecutionErrorAccumulator eea, final OrderType orderType, final String orderTimeTypeName) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderTimeType orderTimeType = orderControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

        if(orderTimeType == null) {
            String orderTypeName = orderType.getLastDetail().getOrderTypeName();

            handleExecutionError(UnknownOrderTimeTypeNameException.class, eea, ExecutionErrors.UnknownOrderTimeTypeName.name(), orderTypeName, orderTimeTypeName);
        }

        return orderTimeType;
    }

    public void createOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time, final BasePK partyPK) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderDetail orderDetail = order.getLastDetail().getOrder().getLastDetail();
        OrderType orderType = orderDetail.getOrderType();
        OrderTimeType orderTimeType = orderControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            if(orderControl.orderTimeExists(order, orderTimeType)) {
                handleExecutionError(DuplicateOrderTimeException.class, eea, ExecutionErrors.DuplicateOrderTime.name(), getOrderTypeName(orderType),
                        orderDetail.getOrderName(), orderTimeTypeName);
            } else {
                orderControl.createOrderTime(order, orderTimeType, time, partyPK);
            }
        }
    }

    public void updateOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time, final BasePK partyPK) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderDetail orderDetail = order.getLastDetail().getOrder().getLastDetail();
        OrderType orderType = orderDetail.getOrderType();
        OrderTimeType orderTimeType = orderControl.getOrderTimeTypeByName(orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            OrderTimeValue orderTimeValue = orderControl.getOrderTimeValueForUpdate(order, orderTimeType);

            if(orderTimeValue == null) {
                handleExecutionError(UnknownOrderTimeException.class, eea, ExecutionErrors.UnknownOrderTime.name(), getOrderTypeName(orderType),
                        orderDetail.getOrderName(), orderTimeTypeName);
            } else {
                orderTimeValue.setTime(time);
                orderControl.updateOrderTimeFromValue(orderTimeValue, partyPK);
            }
        }
    }

    public void createOrUpdateOrderTimeIfNotNull(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time,
            final BasePK partyPK) {
        if(time != null) {
            createOrUpdateOrderTime(eea, order, orderTimeTypeName, time, partyPK);
        }
    }

    public void createOrUpdateOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final Long time,
            final BasePK partyPK) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderDetail orderDetail = order.getLastDetail();
        OrderType orderType = orderDetail.getOrderType();
        OrderTimeType orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            OrderTimeValue orderTimeValue = orderControl.getOrderTimeValueForUpdate(order, orderTimeType);

            if(orderTimeValue == null) {
                orderControl.createOrderTime(order, orderTimeType, time, partyPK);
            } else {
                orderTimeValue.setTime(time);
                orderControl.updateOrderTimeFromValue(orderTimeValue, partyPK);
            }
        }
    }

    private OrderTime getOrderTimeEntity(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderDetail orderDetail = order.getLastDetail();
        OrderType orderType = orderDetail.getOrderType();
        OrderTimeType orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName);
        OrderTime result = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            result = orderControl.getOrderTimeForUpdate(order, orderTimeType);

            if(result == null) {
                handleExecutionError(UnknownOrderTimeException.class, eea, ExecutionErrors.UnknownOrderTime.name(), getOrderTypeName(orderType), orderDetail.getOrderName(),
                        orderTimeTypeName);
            }
        }

        return result;
    }

    public Long getOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName) {
        OrderTime orderTime = getOrderTimeEntity(eea, order, orderTimeTypeName);
        
        return orderTime == null ? null : orderTime.getTime();
    }

    public OrderTimeTransfer getOrderTimeTransfer(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final Order order, final String orderTimeTypeName) {
        OrderTime orderTime = getOrderTimeEntity(eea, order, orderTimeTypeName);
        
        return orderTime == null ? null : ((OrderControl)Session.getModelController(OrderControl.class)).getOrderTimeTransfer(userVisit, orderTime);
    }

    public List<OrderTimeTransfer> getOrderTimeTransfersByOrder(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final Order order) {
        return ((OrderControl)Session.getModelController(OrderControl.class)).getOrderTimeTransfersByOrder(userVisit, order);
    }

    public void deleteOrderTime(final ExecutionErrorAccumulator eea, final Order order, final String orderTimeTypeName, final BasePK deletedBy) {
        OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
        OrderDetail orderDetail = order.getLastDetail();
        OrderType orderType = orderDetail.getOrderType();
        OrderTimeType orderTimeType = getOrderTimeTypeByName(eea, orderType, orderTimeTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            OrderTime orderTime = orderControl.getOrderTimeForUpdate(order, orderTimeType);

            if(orderTime == null) {
                handleExecutionError(UnknownOrderTimeException.class, eea, ExecutionErrors.UnknownOrderTime.name(), getOrderTypeName(orderType), orderDetail.getOrderName(), orderTimeTypeName);
            } else {
                orderControl.deleteOrderTime(orderTime, deletedBy);
            }
        }
    }

}
