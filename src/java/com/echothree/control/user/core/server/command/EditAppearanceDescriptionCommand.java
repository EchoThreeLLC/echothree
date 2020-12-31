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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.AppearanceDescriptionEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditAppearanceDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditAppearanceDescriptionResult;
import com.echothree.control.user.core.common.spec.AppearanceDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.AppearanceDescription;
import com.echothree.model.data.core.server.value.AppearanceDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditAppearanceDescriptionCommand
        extends BaseAbstractEditCommand<AppearanceDescriptionSpec, AppearanceDescriptionEdit, EditAppearanceDescriptionResult, AppearanceDescription, Appearance> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Appearance.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AppearanceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditAppearanceDescriptionCommand */
    public EditAppearanceDescriptionCommand(UserVisitPK userVisitPK, EditAppearanceDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditAppearanceDescriptionResult getResult() {
        return CoreResultFactory.getEditAppearanceDescriptionResult();
    }

    @Override
    public AppearanceDescriptionEdit getEdit() {
        return CoreEditFactory.getAppearanceDescriptionEdit();
    }

    @Override
    public AppearanceDescription getEntity(EditAppearanceDescriptionResult result) {
        var coreControl = getCoreControl();
        AppearanceDescription appearanceDescription = null;
        String appearanceName = spec.getAppearanceName();
        Appearance appearance = coreControl.getAppearanceByName(appearanceName);

        if(appearance != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    appearanceDescription = coreControl.getAppearanceDescription(appearance, language);
                } else { // EditMode.UPDATE
                    appearanceDescription = coreControl.getAppearanceDescriptionForUpdate(appearance, language);
                }

                if(appearanceDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownAppearanceDescription.name(), appearanceName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownAppearanceName.name(), appearanceName);
        }

        return appearanceDescription;
    }

    @Override
    public Appearance getLockEntity(AppearanceDescription appearanceDescription) {
        return appearanceDescription.getAppearance();
    }

    @Override
    public void fillInResult(EditAppearanceDescriptionResult result, AppearanceDescription appearanceDescription) {
        var coreControl = getCoreControl();

        result.setAppearanceDescription(coreControl.getAppearanceDescriptionTransfer(getUserVisit(), appearanceDescription));
    }

    @Override
    public void doLock(AppearanceDescriptionEdit edit, AppearanceDescription appearanceDescription) {
        edit.setDescription(appearanceDescription.getDescription());
    }

    @Override
    public void doUpdate(AppearanceDescription appearanceDescription) {
        var coreControl = getCoreControl();
        AppearanceDescriptionValue appearanceDescriptionValue = coreControl.getAppearanceDescriptionValue(appearanceDescription);
        appearanceDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateAppearanceDescriptionFromValue(appearanceDescriptionValue, getPartyPK());
    }
    
}
