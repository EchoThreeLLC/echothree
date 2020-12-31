// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.GetTransactionEntityRoleTypeForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.GetTransactionEntityRoleTypeResult;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTransactionEntityRoleTypeCommand
        extends BaseSimpleCommand<GetTransactionEntityRoleTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TransactionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TransactionEntityRoleTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetTransactionEntityRoleTypeCommand */
    public GetTransactionEntityRoleTypeCommand(UserVisitPK userVisitPK, GetTransactionEntityRoleTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = Session.getModelController(AccountingControl.class);
        GetTransactionEntityRoleTypeResult result = AccountingResultFactory.getGetTransactionEntityRoleTypeResult();
        String transactionTypeName = form.getTransactionTypeName();
        TransactionType transactionType = accountingControl.getTransactionTypeByName(transactionTypeName);
        
        if(transactionType != null) {
            String transactionEntityRoleTypeName = form.getTransactionEntityRoleTypeName();
            TransactionEntityRoleType transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transactionType, transactionEntityRoleTypeName);
            
            if(transactionEntityRoleType != null) {
                result.setTransactionEntityRoleType(accountingControl.getTransactionEntityRoleTypeTransfer(getUserVisit(), transactionEntityRoleType));
                
                sendEventUsingNames(transactionEntityRoleType.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownTransactionEntityRoleTypeName.name(), transactionEntityRoleTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTransactionTypeName.name(), transactionTypeName);
        }
        
        return result;
    }
    
}
