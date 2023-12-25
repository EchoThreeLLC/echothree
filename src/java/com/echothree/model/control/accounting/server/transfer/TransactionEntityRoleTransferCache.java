// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTypeTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TransactionEntityRoleTransferCache
        extends BaseAccountingTransferCache<TransactionEntityRole, TransactionEntityRoleTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of TransactionEntityRoleTransferCache */
    public TransactionEntityRoleTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public TransactionEntityRoleTransfer getTransfer(TransactionEntityRole transactionEntityRole) {
        TransactionEntityRoleTransfer transactionEntityRoleTransfer = get(transactionEntityRole);
        
        if(transactionEntityRoleTransfer == null) {
            TransactionTransfer transaction = accountingControl.getTransactionTransfer(userVisit, transactionEntityRole.getTransaction());
            TransactionEntityRoleTypeTransfer transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeTransfer(userVisit, transactionEntityRole.getTransactionEntityRoleType());
            EntityInstanceTransfer entityInstance = coreControl.getEntityInstanceTransfer(userVisit, transactionEntityRole.getEntityInstance(), false, false, false, false, false, false);
            
            transactionEntityRoleTransfer = new TransactionEntityRoleTransfer(transaction, transactionEntityRoleType, entityInstance);
            put(transactionEntityRole, transactionEntityRoleTransfer);
        }
        
        return transactionEntityRoleTransfer;
    }
    
}
