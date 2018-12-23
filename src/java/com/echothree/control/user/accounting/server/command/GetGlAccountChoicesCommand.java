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

package com.echothree.control.user.accounting.server.command;

import com.echothree.control.user.accounting.common.form.GetGlAccountChoicesForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.GetGlAccountChoicesResult;
import com.echothree.model.control.accounting.common.choice.GlAccountChoicesBean;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class GetGlAccountChoicesCommand
        extends BaseSimpleCommand<GetGlAccountChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GlAccount.name(), SecurityRoles.Choices.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GlAccountCategoryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultGlAccountChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetGlAccountChoicesCommand */
    public GetGlAccountChoicesCommand(UserVisitPK userVisitPK, GetGlAccountChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        GetGlAccountChoicesResult result = AccountingResultFactory.getGetGlAccountChoicesResult();
        String glAccountCategoryName = form.getGlAccountCategoryName();
        GlAccountCategory glAccountCategory = glAccountCategoryName == null? null: accountingControl.getGlAccountCategoryByName(glAccountCategoryName);
        
        if(glAccountCategoryName == null || glAccountCategory != null) {
            String defaultGlAccountChoice = form.getDefaultGlAccountChoice();
            boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
            GlAccountChoicesBean glAccountChoicesBean;
            
            if(glAccountCategory != null) {
                glAccountChoicesBean = accountingControl.getGlAccountChoicesByGlAccountCategory(defaultGlAccountChoice,
                        getPreferredLanguage(), glAccountCategory, allowNullChoice);
            } else {
                glAccountChoicesBean = accountingControl.getGlAccountChoices(defaultGlAccountChoice, getPreferredLanguage(),
                        allowNullChoice);
            }
            
            result.setGlAccountChoices(glAccountChoicesBean);
        } else {
            addExecutionError(ExecutionErrors.UnknownGlAccountCategoryName.name(), glAccountCategoryName);
        }
        
        return result;
    }
    
}
