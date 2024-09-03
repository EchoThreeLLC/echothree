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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.FontStyleDescriptionEdit;
import com.echothree.control.user.core.common.form.EditFontStyleDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditFontStyleDescriptionResult;
import com.echothree.control.user.core.common.spec.FontStyleDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontStyleDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditFontStyleDescriptionCommand
        extends BaseAbstractEditCommand<FontStyleDescriptionSpec, FontStyleDescriptionEdit, EditFontStyleDescriptionResult, FontStyleDescription, FontStyle> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.FontStyle.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FontStyleName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditFontStyleDescriptionCommand */
    public EditFontStyleDescriptionCommand(UserVisitPK userVisitPK, EditFontStyleDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditFontStyleDescriptionResult getResult() {
        return CoreResultFactory.getEditFontStyleDescriptionResult();
    }

    @Override
    public FontStyleDescriptionEdit getEdit() {
        return CoreEditFactory.getFontStyleDescriptionEdit();
    }

    @Override
    public FontStyleDescription getEntity(EditFontStyleDescriptionResult result) {
        var coreControl = getCoreControl();
        FontStyleDescription fontStyleDescription = null;
        var fontStyleName = spec.getFontStyleName();
        var fontStyle = coreControl.getFontStyleByName(fontStyleName);

        if(fontStyle != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    fontStyleDescription = coreControl.getFontStyleDescription(fontStyle, language);
                } else { // EditMode.UPDATE
                    fontStyleDescription = coreControl.getFontStyleDescriptionForUpdate(fontStyle, language);
                }

                if(fontStyleDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownFontStyleDescription.name(), fontStyleName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFontStyleName.name(), fontStyleName);
        }

        return fontStyleDescription;
    }

    @Override
    public FontStyle getLockEntity(FontStyleDescription fontStyleDescription) {
        return fontStyleDescription.getFontStyle();
    }

    @Override
    public void fillInResult(EditFontStyleDescriptionResult result, FontStyleDescription fontStyleDescription) {
        var coreControl = getCoreControl();

        result.setFontStyleDescription(coreControl.getFontStyleDescriptionTransfer(getUserVisit(), fontStyleDescription));
    }

    @Override
    public void doLock(FontStyleDescriptionEdit edit, FontStyleDescription fontStyleDescription) {
        edit.setDescription(fontStyleDescription.getDescription());
    }

    @Override
    public void doUpdate(FontStyleDescription fontStyleDescription) {
        var coreControl = getCoreControl();
        var fontStyleDescriptionValue = coreControl.getFontStyleDescriptionValue(fontStyleDescription);
        fontStyleDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateFontStyleDescriptionFromValue(fontStyleDescriptionValue, getPartyPK());
    }
    
}
