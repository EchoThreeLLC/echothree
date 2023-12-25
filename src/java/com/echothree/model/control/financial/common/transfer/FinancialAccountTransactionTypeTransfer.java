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

package com.echothree.model.control.financial.common.transfer;

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class FinancialAccountTransactionTypeTransfer
        extends BaseTransfer {

    private FinancialAccountTypeTransfer financialAccountType;
    private String financialAccountTransactionTypeName;
    private FinancialAccountTransactionTypeTransfer parentFinancialAccountTransactionType;
    private GlAccountTransfer glAccount;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;

    /** Creates a new instance of FinancialAccountTransactionTypeTransfer */
    public FinancialAccountTransactionTypeTransfer(FinancialAccountTypeTransfer financialAccountType, String financialAccountTransactionTypeName,
            FinancialAccountTransactionTypeTransfer parentFinancialAccountTransactionType, GlAccountTransfer glAccount, Boolean isDefault, Integer sortOrder,
            String description) {
        this.financialAccountType = financialAccountType;
        this.financialAccountTransactionTypeName = financialAccountTransactionTypeName;
        this.parentFinancialAccountTransactionType = parentFinancialAccountTransactionType;
        this.glAccount = glAccount;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public FinancialAccountTypeTransfer getFinancialAccountType() {
        return financialAccountType;
    }

    public void setFinancialAccountType(FinancialAccountTypeTransfer financialAccountType) {
        this.financialAccountType = financialAccountType;
    }
    
    public String getFinancialAccountTransactionTypeName() {
        return financialAccountTransactionTypeName;
    }

    public void setFinancialAccountTransactionTypeName(String financialAccountTransactionTypeName) {
        this.financialAccountTransactionTypeName = financialAccountTransactionTypeName;
    }

    public FinancialAccountTransactionTypeTransfer getParentFinancialAccountTransactionType() {
        return parentFinancialAccountTransactionType;
    }

    public void setParentFinancialAccountTransactionType(FinancialAccountTransactionTypeTransfer parentFinancialAccountTransactionType) {
        this.parentFinancialAccountTransactionType = parentFinancialAccountTransactionType;
    }

    public GlAccountTransfer getGlAccount() {
        return glAccount;
    }

    public void setGlAccount(GlAccountTransfer glAccount) {
        this.glAccount = glAccount;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
