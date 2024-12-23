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

package com.echothree.model.control.accounting.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class TransactionEntityRoleTransfer
        extends BaseTransfer {
    
    private TransactionTransfer transaction;
    private TransactionEntityRoleTypeTransfer transactionEntityRoleType;
    private EntityInstanceTransfer entityInstance;
    
    /** Creates a new instance of TransactionEntityRoleTransfer */
    public TransactionEntityRoleTransfer(TransactionTransfer transaction, TransactionEntityRoleTypeTransfer transactionEntityRoleType, EntityInstanceTransfer entityInstance) {
        this.transaction = transaction;
        this.transactionEntityRoleType = transactionEntityRoleType;
        this.entityInstance = entityInstance;
    }
    
    public TransactionTransfer getTransaction() {
        return transaction;
    }
    
    public void setTransaction(TransactionTransfer transaction) {
        this.transaction = transaction;
    }
    
    public TransactionEntityRoleTypeTransfer getTransactionEntityRoleType() {
        return transactionEntityRoleType;
    }
    
    public void setTransactionEntityRoleType(TransactionEntityRoleTypeTransfer transactionEntityRoleType) {
        this.transactionEntityRoleType = transactionEntityRoleType;
    }
    
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }
    
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }
    
}
