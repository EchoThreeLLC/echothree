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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.GetGlAccountsForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.GetGlAccountsResult;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetGlAccountsCommand
        extends BaseSimpleCommand<GetGlAccountsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.GlAccount.name(), SecurityRoles.List.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetGlAccountsCommand */
    public GetGlAccountsCommand(UserVisitPK userVisitPK, GetGlAccountsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        GetGlAccountsResult result = AccountingResultFactory.getGetGlAccountsResult();
        String glAccountCategoryName = form.getGlAccountCategoryName();
        GlAccountCategory glAccountCategory = glAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(glAccountCategoryName);
        
        if(glAccountCategoryName == null || glAccountCategory != null) {
            if(glAccountCategory == null) {
                result.setGlAccounts(accountingControl.getGlAccountTransfers(getUserVisit()));
            } else {
                UserVisit userVisit = getUserVisit();
                
                result.setGlAccountCategory(accountingControl.getGlAccountCategoryTransfer(userVisit, glAccountCategory));
                result.setGlAccounts(accountingControl.getGlAccountTransfersByGlAccountCategory(userVisit, glAccountCategory));
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGlAccountCategoryName.name(), glAccountCategoryName);
        }
        
        return result;
    }
    
}
