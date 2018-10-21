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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.remote.edit.CoreEditFactory;
import com.echothree.control.user.core.remote.edit.TextTransformationDescriptionEdit;
import com.echothree.control.user.core.remote.form.EditTextTransformationDescriptionForm;
import com.echothree.control.user.core.remote.result.CoreResultFactory;
import com.echothree.control.user.core.remote.result.EditTextTransformationDescriptionResult;
import com.echothree.control.user.core.remote.spec.TextTransformationDescriptionSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.core.server.entity.TextTransformationDescription;
import com.echothree.model.data.core.server.value.TextTransformationDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTextTransformationDescriptionCommand
        extends BaseAbstractEditCommand<TextTransformationDescriptionSpec, TextTransformationDescriptionEdit, EditTextTransformationDescriptionResult, TextTransformationDescription, TextTransformation> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TextTransformation.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TextTransformationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditTextTransformationDescriptionCommand */
    public EditTextTransformationDescriptionCommand(UserVisitPK userVisitPK, EditTextTransformationDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTextTransformationDescriptionResult getResult() {
        return CoreResultFactory.getEditTextTransformationDescriptionResult();
    }

    @Override
    public TextTransformationDescriptionEdit getEdit() {
        return CoreEditFactory.getTextTransformationDescriptionEdit();
    }

    @Override
    public TextTransformationDescription getEntity(EditTextTransformationDescriptionResult result) {
        CoreControl coreControl = getCoreControl();
        TextTransformationDescription textTransformationDescription = null;
        String textTransformationName = spec.getTextTransformationName();
        TextTransformation textTransformation = coreControl.getTextTransformationByName(textTransformationName);

        if(textTransformation != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    textTransformationDescription = coreControl.getTextTransformationDescription(textTransformation, language);
                } else { // EditMode.UPDATE
                    textTransformationDescription = coreControl.getTextTransformationDescriptionForUpdate(textTransformation, language);
                }

                if(textTransformationDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownTextTransformationDescription.name(), textTransformationName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTextTransformationName.name(), textTransformationName);
        }

        return textTransformationDescription;
    }

    @Override
    public TextTransformation getLockEntity(TextTransformationDescription textTransformationDescription) {
        return textTransformationDescription.getTextTransformation();
    }

    @Override
    public void fillInResult(EditTextTransformationDescriptionResult result, TextTransformationDescription textTransformationDescription) {
        CoreControl coreControl = getCoreControl();

        result.setTextTransformationDescription(coreControl.getTextTransformationDescriptionTransfer(getUserVisit(), textTransformationDescription));
    }

    @Override
    public void doLock(TextTransformationDescriptionEdit edit, TextTransformationDescription textTransformationDescription) {
        edit.setDescription(textTransformationDescription.getDescription());
    }

    @Override
    public void doUpdate(TextTransformationDescription textTransformationDescription) {
        CoreControl coreControl = getCoreControl();
        TextTransformationDescriptionValue textTransformationDescriptionValue = coreControl.getTextTransformationDescriptionValue(textTransformationDescription);
        textTransformationDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateTextTransformationDescriptionFromValue(textTransformationDescriptionValue, getPartyPK());
    }
    
}
