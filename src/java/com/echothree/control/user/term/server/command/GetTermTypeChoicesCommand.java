// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.term.remote.form.GetTermTypeChoicesForm;
import com.echothree.control.user.term.remote.result.GetTermTypeChoicesResult;
import com.echothree.control.user.term.remote.result.TermResultFactory;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTermTypeChoicesCommand
        extends BaseSimpleCommand<GetTermTypeChoicesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DefaultTermTypeChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetTermTypeChoicesCommand */
    public GetTermTypeChoicesCommand(UserVisitPK userVisitPK, GetTermTypeChoicesForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        TermControl termControl = (TermControl)Session.getModelController(TermControl.class);
        GetTermTypeChoicesResult result = TermResultFactory.getGetTermTypeChoicesResult();
        String defaultTermTypeChoice = form.getDefaultTermTypeChoice();
        boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());
        
        result.setTermTypeChoices(termControl.getTermTypeChoices(defaultTermTypeChoice, getPreferredLanguage(),
                allowNullChoice));
        
        return result;
    }
    
}
