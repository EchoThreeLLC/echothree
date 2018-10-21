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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.remote.edit.GeoCodeScopeDescriptionEdit;
import com.echothree.control.user.geo.remote.edit.GeoEditFactory;
import com.echothree.control.user.geo.remote.form.EditGeoCodeScopeDescriptionForm;
import com.echothree.control.user.geo.remote.result.EditGeoCodeScopeDescriptionResult;
import com.echothree.control.user.geo.remote.result.GeoResultFactory;
import com.echothree.control.user.geo.remote.spec.GeoCodeScopeDescriptionSpec;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.entity.GeoCodeScopeDescription;
import com.echothree.model.data.geo.server.value.GeoCodeScopeDescriptionValue;
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

public class EditGeoCodeScopeDescriptionCommand
        extends BaseAbstractEditCommand<GeoCodeScopeDescriptionSpec, GeoCodeScopeDescriptionEdit, EditGeoCodeScopeDescriptionResult, GeoCodeScopeDescription, GeoCodeScope> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeScope.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditGeoCodeScopeDescriptionCommand */
    public EditGeoCodeScopeDescriptionCommand(UserVisitPK userVisitPK, EditGeoCodeScopeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeScopeDescriptionResult getResult() {
        return GeoResultFactory.getEditGeoCodeScopeDescriptionResult();
    }

    @Override
    public GeoCodeScopeDescriptionEdit getEdit() {
        return GeoEditFactory.getGeoCodeScopeDescriptionEdit();
    }

    @Override
    public GeoCodeScopeDescription getEntity(EditGeoCodeScopeDescriptionResult result) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GeoCodeScopeDescription geoCodeScopeDescription = null;
        String geoCodeScopeName = spec.getGeoCodeScopeName();
        GeoCodeScope geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);

        if(geoCodeScope != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    geoCodeScopeDescription = geoControl.getGeoCodeScopeDescription(geoCodeScope, language);
                } else { // EditMode.UPDATE
                    geoCodeScopeDescription = geoControl.getGeoCodeScopeDescriptionForUpdate(geoCodeScope, language);
                }

                if(geoCodeScopeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeScopeDescription.name(), geoCodeScopeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeScopeName.name(), geoCodeScopeName);
        }

        return geoCodeScopeDescription;
    }

    @Override
    public GeoCodeScope getLockEntity(GeoCodeScopeDescription geoCodeScopeDescription) {
        return geoCodeScopeDescription.getGeoCodeScope();
    }

    @Override
    public void fillInResult(EditGeoCodeScopeDescriptionResult result, GeoCodeScopeDescription geoCodeScopeDescription) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);

        result.setGeoCodeScopeDescription(geoControl.getGeoCodeScopeDescriptionTransfer(getUserVisit(), geoCodeScopeDescription));
    }

    @Override
    public void doLock(GeoCodeScopeDescriptionEdit edit, GeoCodeScopeDescription geoCodeScopeDescription) {
        edit.setDescription(geoCodeScopeDescription.getDescription());
    }

    @Override
    public void doUpdate(GeoCodeScopeDescription geoCodeScopeDescription) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GeoCodeScopeDescriptionValue geoCodeScopeDescriptionValue = geoControl.getGeoCodeScopeDescriptionValue(geoCodeScopeDescription);
        geoCodeScopeDescriptionValue.setDescription(edit.getDescription());

        geoControl.updateGeoCodeScopeDescriptionFromValue(geoCodeScopeDescriptionValue, getPartyPK());
    }

}
