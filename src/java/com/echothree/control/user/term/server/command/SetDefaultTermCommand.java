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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.form.SetDefaultTermForm;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.term.server.value.TermDetailValue;
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

public class SetDefaultTermCommand
        extends BaseSimpleCommand<SetDefaultTermForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultTermCommand */
    public SetDefaultTermCommand(UserVisitPK userVisitPK, SetDefaultTermForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var termControl = Session.getModelController(TermControl.class);
        String termName = form.getTermName();
        TermDetailValue termDetailValue = termControl.getTermDetailValueByNameForUpdate(termName);
        
        if(termDetailValue != null) {
            termDetailValue.setIsDefault(Boolean.TRUE);
            termControl.updateTermFromValue(termDetailValue, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownTermName.name(), termName);
        }
        
        return null;
    }
    
}
