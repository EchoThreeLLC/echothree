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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.CreateSelectorBooleanTypeDescriptionForm;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.SelectorBooleanType;
import com.echothree.model.data.selector.server.entity.SelectorBooleanTypeDescription;
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

public class CreateSelectorBooleanTypeDescriptionCommand
        extends BaseSimpleCommand<CreateSelectorBooleanTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SelectorBooleanTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of CreateSelectorBooleanTypeDescriptionCommand */
    public CreateSelectorBooleanTypeDescriptionCommand(UserVisitPK userVisitPK, CreateSelectorBooleanTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
        String selectorBooleanTypeName = form.getSelectorBooleanTypeName();
        SelectorBooleanType selectorBooleanType = selectorControl.getSelectorBooleanTypeByName(selectorBooleanTypeName);
        
        if(selectorBooleanType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                SelectorBooleanTypeDescription selectorBooleanTypeDescription = selectorControl.getSelectorBooleanTypeDescription(selectorBooleanType, language);
                
                if(selectorBooleanTypeDescription == null) {
                    var description = form.getDescription();
                    
                    selectorControl.createSelectorBooleanTypeDescription(selectorBooleanType, language, description);
                } else {
                    addExecutionError(ExecutionErrors.DuplicateSelectorBooleanTypeDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSelectorBooleanTypeName.name(), selectorBooleanTypeName);
        }
        
        return null;
    }
    
}
