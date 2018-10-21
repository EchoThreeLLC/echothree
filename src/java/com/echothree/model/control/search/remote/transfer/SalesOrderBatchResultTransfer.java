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

package com.echothree.model.control.search.remote.transfer;

import com.echothree.model.control.sales.remote.transfer.SalesOrderBatchTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class SalesOrderBatchResultTransfer
        extends BaseTransfer {
    
    private String batchName;
    private SalesOrderBatchTransfer salesOrderBatch;
    
    /** Creates a new instance of SalesOrderBatchResultTransfer */
    public SalesOrderBatchResultTransfer(String batchName, SalesOrderBatchTransfer salesOrderBatch) {
        this.batchName = batchName;
        this.salesOrderBatch = salesOrderBatch;
    }

    /**
     * @return the batchName
     */
    public String getBatchName() {
        return batchName;
    }

    /**
     * @param batchName the batchName to set
     */
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    /**
     * @return the salesOrderBatch
     */
    public SalesOrderBatchTransfer getSalesOrderBatch() {
        return salesOrderBatch;
    }

    /**
     * @param salesOrderBatch the salesOrderBatch to set
     */
    public void setSalesOrderBatch(SalesOrderBatchTransfer salesOrderBatch) {
        this.salesOrderBatch = salesOrderBatch;
    }
    
}
