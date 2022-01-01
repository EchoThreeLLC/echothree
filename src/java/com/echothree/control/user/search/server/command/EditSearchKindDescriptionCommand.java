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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.edit.SearchEditFactory;
import com.echothree.control.user.search.common.edit.SearchKindDescriptionEdit;
import com.echothree.control.user.search.common.form.EditSearchKindDescriptionForm;
import com.echothree.control.user.search.common.result.EditSearchKindDescriptionResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchKindDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchKindDescription;
import com.echothree.model.data.search.server.value.SearchKindDescriptionValue;
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

public class EditSearchKindDescriptionCommand
        extends BaseAbstractEditCommand<SearchKindDescriptionSpec, SearchKindDescriptionEdit, EditSearchKindDescriptionResult, SearchKindDescription, SearchKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchKind.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditSearchKindDescriptionCommand */
    public EditSearchKindDescriptionCommand(UserVisitPK userVisitPK, EditSearchKindDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSearchKindDescriptionResult getResult() {
        return SearchResultFactory.getEditSearchKindDescriptionResult();
    }

    @Override
    public SearchKindDescriptionEdit getEdit() {
        return SearchEditFactory.getSearchKindDescriptionEdit();
    }

    @Override
    public SearchKindDescription getEntity(EditSearchKindDescriptionResult result) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchKindDescription searchKindDescription = null;
        String searchKindName = spec.getSearchKindName();
        SearchKind searchKind = searchControl.getSearchKindByName(searchKindName);

        if(searchKind != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    searchKindDescription = searchControl.getSearchKindDescription(searchKind, language);
                } else { // EditMode.UPDATE
                    searchKindDescription = searchControl.getSearchKindDescriptionForUpdate(searchKind, language);
                }

                if(searchKindDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownSearchKindDescription.name(), searchKindName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), searchKindName);
        }

        return searchKindDescription;
    }

    @Override
    public SearchKind getLockEntity(SearchKindDescription searchKindDescription) {
        return searchKindDescription.getSearchKind();
    }

    @Override
    public void fillInResult(EditSearchKindDescriptionResult result, SearchKindDescription searchKindDescription) {
        var searchControl = Session.getModelController(SearchControl.class);

        result.setSearchKindDescription(searchControl.getSearchKindDescriptionTransfer(getUserVisit(), searchKindDescription));
    }

    @Override
    public void doLock(SearchKindDescriptionEdit edit, SearchKindDescription searchKindDescription) {
        edit.setDescription(searchKindDescription.getDescription());
    }

    @Override
    public void doUpdate(SearchKindDescription searchKindDescription) {
        var searchControl = Session.getModelController(SearchControl.class);
        SearchKindDescriptionValue searchKindDescriptionValue = searchControl.getSearchKindDescriptionValue(searchKindDescription);

        searchKindDescriptionValue.setDescription(edit.getDescription());

        searchControl.updateSearchKindDescriptionFromValue(searchKindDescriptionValue, getPartyPK());
    }

}
