// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.accounting.common.form.GetCurrencyChoicesForm;
import com.echothree.control.user.accounting.common.result.AccountingResultFactory;
import com.echothree.control.user.accounting.common.result.GetCurrencyChoicesResult;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCurrencyChoicesCommand
        extends BaseSimpleCommand<GetCurrencyChoicesForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DefaultCurrencyChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetCurrencyChoicesCommand */
    public GetCurrencyChoicesCommand(UserVisitPK userVisitPK, GetCurrencyChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = Session.getModelController(AccountingControl.class);
        GetCurrencyChoicesResult result = AccountingResultFactory.getGetCurrencyChoicesResult();
        String defaultCurrencyChoice = form.getDefaultCurrencyChoice();
        boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
        
        result.setCurrencyChoices(coreControl.getCurrencyChoices(defaultCurrencyChoice, getPreferredLanguage(), allowNullChoice));
        
        return result;
    }
    
}
