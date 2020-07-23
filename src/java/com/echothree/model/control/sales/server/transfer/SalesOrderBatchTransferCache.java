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

package com.echothree.model.control.sales.server.transfer;

import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.batch.server.transfer.GenericBatchTransferCache;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.order.server.OrderControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.sales.common.transfer.SalesOrderBatchTransfer;
import com.echothree.model.control.sales.server.SalesOrderBatchControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class SalesOrderBatchTransferCache
        extends GenericBatchTransferCache<SalesOrderBatchTransfer> {
    
    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PaymentMethodControl paymentMethodControl = (PaymentMethodControl)Session.getModelController(PaymentMethodControl.class);
    OrderControl orderControl = (OrderControl)Session.getModelController(OrderControl.class);
    SalesOrderBatchControl salesOrderBatchControl = (SalesOrderBatchControl)Session.getModelController(SalesOrderBatchControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of SalesOrderBatchTransferCache */
    public SalesOrderBatchTransferCache(UserVisit userVisit) {
        super(userVisit, (BatchControl)Session.getModelController(BatchControl.class));

        setIncludeEntityInstance(true);
    }
    
    @Override
    public SalesOrderBatchTransfer getTransfer(Batch batch) {
        var salesOrderBatchTransfer = get(batch);
        
        if(salesOrderBatchTransfer == null) {
            var batchDetail = batch.getLastDetail();
            var orderBatch = orderControl.getOrderBatch(batch);
            var salesOrderBatch = salesOrderBatchControl.getSalesOrderBatch(batch);
            var batchTypeTransfer = batchControl.getBatchTypeTransfer(userVisit, batchDetail.getBatchType());
            var batchName = batchDetail.getBatchName();
            var currency = orderBatch.getCurrency();
            var currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            var count = orderBatch.getCount();
            var paymentMethodTransfer = paymentMethodControl.getPaymentMethodTransfer(userVisit, salesOrderBatch.getPaymentMethod());
            var amount = AmountUtils.getInstance().formatPriceLine(currency, orderBatch.getAmount());
            var entityInstance = coreControl.getEntityInstanceByBasePK(batch.getPrimaryKey());
            
            salesOrderBatchTransfer = new SalesOrderBatchTransfer(batchTypeTransfer, batchName, currencyTransfer, paymentMethodTransfer, count, amount,
                    getBatchStatus(batch, entityInstance));
            put(batch, salesOrderBatchTransfer, entityInstance);
            
            handleOptions(batch, salesOrderBatchTransfer);
        }
        
        return salesOrderBatchTransfer;
    }
    
}
