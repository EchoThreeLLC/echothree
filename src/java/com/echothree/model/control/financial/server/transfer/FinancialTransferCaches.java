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

package com.echothree.model.control.financial.server.transfer;

import com.echothree.model.control.financial.server.FinancialControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class FinancialTransferCaches
        extends BaseTransferCaches {
    
    protected FinancialControl financialControl;
    
    protected FinancialAccountRoleTypeTransferCache financialAccountRoleTypeTransferCache;
    protected FinancialAccountTypeTransferCache financialAccountTypeTransferCache;
    protected FinancialAccountTypeDescriptionTransferCache financialAccountTypeDescriptionTransferCache;
    protected FinancialAccountTransactionTypeTransferCache financialAccountTransactionTypeTransferCache;
    protected FinancialAccountTransactionTypeDescriptionTransferCache financialAccountTransactionTypeDescriptionTransferCache;
    protected FinancialAccountAliasTypeTransferCache financialAccountAliasTypeTransferCache;
    protected FinancialAccountAliasTypeDescriptionTransferCache financialAccountAliasTypeDescriptionTransferCache;
    protected FinancialAccountRoleTransferCache financialAccountRoleTransferCache;
    protected FinancialAccountTransferCache financialAccountTransferCache;
    protected FinancialAccountAliasTransferCache financialAccountAliasTransferCache;
    protected FinancialAccountTransactionTransferCache financialAccountTransactionTransferCache;
    
    /** Creates a new instance of FinancialTransferCaches */
    public FinancialTransferCaches(UserVisit userVisit, FinancialControl financialControl) {
        super(userVisit);
        
        this.financialControl = financialControl;
    }
    
    public FinancialAccountRoleTypeTransferCache getFinancialAccountRoleTypeTransferCache() {
        if(financialAccountRoleTypeTransferCache == null)
            financialAccountRoleTypeTransferCache = new FinancialAccountRoleTypeTransferCache(userVisit, financialControl);
        
        return financialAccountRoleTypeTransferCache;
    }
    
    public FinancialAccountTypeTransferCache getFinancialAccountTypeTransferCache() {
        if(financialAccountTypeTransferCache == null)
            financialAccountTypeTransferCache = new FinancialAccountTypeTransferCache(userVisit, financialControl);
        
        return financialAccountTypeTransferCache;
    }
    
    public FinancialAccountTypeDescriptionTransferCache getFinancialAccountTypeDescriptionTransferCache() {
        if(financialAccountTypeDescriptionTransferCache == null)
            financialAccountTypeDescriptionTransferCache = new FinancialAccountTypeDescriptionTransferCache(userVisit, financialControl);
        
        return financialAccountTypeDescriptionTransferCache;
    }
    
    public FinancialAccountTransactionTypeTransferCache getFinancialAccountTransactionTypeTransferCache() {
        if(financialAccountTransactionTypeTransferCache == null)
            financialAccountTransactionTypeTransferCache = new FinancialAccountTransactionTypeTransferCache(userVisit, financialControl);
        
        return financialAccountTransactionTypeTransferCache;
    }
    
    public FinancialAccountTransactionTypeDescriptionTransferCache getFinancialAccountTransactionTypeDescriptionTransferCache() {
        if(financialAccountTransactionTypeDescriptionTransferCache == null)
            financialAccountTransactionTypeDescriptionTransferCache = new FinancialAccountTransactionTypeDescriptionTransferCache(userVisit, financialControl);
        
        return financialAccountTransactionTypeDescriptionTransferCache;
    }
    
    public FinancialAccountAliasTypeTransferCache getFinancialAccountAliasTypeTransferCache() {
        if(financialAccountAliasTypeTransferCache == null)
            financialAccountAliasTypeTransferCache = new FinancialAccountAliasTypeTransferCache(userVisit, financialControl);
        
        return financialAccountAliasTypeTransferCache;
    }
    
    public FinancialAccountAliasTypeDescriptionTransferCache getFinancialAccountAliasTypeDescriptionTransferCache() {
        if(financialAccountAliasTypeDescriptionTransferCache == null)
            financialAccountAliasTypeDescriptionTransferCache = new FinancialAccountAliasTypeDescriptionTransferCache(userVisit, financialControl);
        
        return financialAccountAliasTypeDescriptionTransferCache;
    }
    
    public FinancialAccountRoleTransferCache getFinancialAccountRoleTransferCache() {
        if(financialAccountRoleTransferCache == null)
            financialAccountRoleTransferCache = new FinancialAccountRoleTransferCache(userVisit, financialControl);
        
        return financialAccountRoleTransferCache;
    }
    
    public FinancialAccountTransferCache getFinancialAccountTransferCache() {
        if(financialAccountTransferCache == null)
            financialAccountTransferCache = new FinancialAccountTransferCache(userVisit, financialControl);
        
        return financialAccountTransferCache;
    }
    
    public FinancialAccountAliasTransferCache getFinancialAccountAliasTransferCache() {
        if(financialAccountAliasTransferCache == null)
            financialAccountAliasTransferCache = new FinancialAccountAliasTransferCache(userVisit, financialControl);
        
        return financialAccountAliasTransferCache;
    }
    
    public FinancialAccountTransactionTransferCache getFinancialAccountTransactionTransferCache() {
        if(financialAccountTransactionTransferCache == null)
            financialAccountTransactionTransferCache = new FinancialAccountTransactionTransferCache(userVisit, financialControl);
        
        return financialAccountTransactionTransferCache;
    }
    
}
