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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountCategoryTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccount;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TransactionGlAccountTransferCache
        extends BaseAccountingTransferCache<TransactionGlAccount, TransactionGlAccountTransfer> {
    
    /** Creates a new instance of TransactionGlAccountTransferCache */
    public TransactionGlAccountTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public TransactionGlAccountTransfer getTransfer(TransactionGlAccount transactionGlAccount) {
        TransactionGlAccountTransfer transactionGlAccountTransfer = get(transactionGlAccount);
        
        if(transactionGlAccountTransfer == null) {
            TransactionGlAccountCategoryTransfer transactionGlAccountCategory = accountingControl.getTransactionGlAccountCategoryTransfer(userVisit,
                    transactionGlAccount.getTransactionGlAccountCategory());
            GlAccountTransfer glAccount = accountingControl.getGlAccountTransfer(userVisit,
                    transactionGlAccount.getGlAccount());
            
            transactionGlAccountTransfer = new TransactionGlAccountTransfer(transactionGlAccountCategory, glAccount);
            put(transactionGlAccount, transactionGlAccountTransfer);
        }
        
        return transactionGlAccountTransfer;
    }
    
}
