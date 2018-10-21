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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.remote.edit.SearchCheckSpellingActionTypeEdit;
import com.echothree.control.user.search.remote.edit.SearchEditFactory;
import com.echothree.control.user.search.remote.form.EditSearchCheckSpellingActionTypeForm;
import com.echothree.control.user.search.remote.result.EditSearchCheckSpellingActionTypeResult;
import com.echothree.control.user.search.remote.result.SearchResultFactory;
import com.echothree.control.user.search.remote.spec.SearchCheckSpellingActionTypeSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionTypeDescription;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionTypeDetail;
import com.echothree.model.data.search.server.value.SearchCheckSpellingActionTypeDescriptionValue;
import com.echothree.model.data.search.server.value.SearchCheckSpellingActionTypeDetailValue;
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

public class EditSearchCheckSpellingActionTypeCommand
        extends BaseAbstractEditCommand<SearchCheckSpellingActionTypeSpec, SearchCheckSpellingActionTypeEdit, EditSearchCheckSpellingActionTypeResult, SearchCheckSpellingActionType, SearchCheckSpellingActionType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchCheckSpellingActionType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchCheckSpellingActionTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchCheckSpellingActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditSearchCheckSpellingActionTypeCommand */
    public EditSearchCheckSpellingActionTypeCommand(UserVisitPK userVisitPK, EditSearchCheckSpellingActionTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSearchCheckSpellingActionTypeResult getResult() {
        return SearchResultFactory.getEditSearchCheckSpellingActionTypeResult();
    }

    @Override
    public SearchCheckSpellingActionTypeEdit getEdit() {
        return SearchEditFactory.getSearchCheckSpellingActionTypeEdit();
    }

    @Override
    public SearchCheckSpellingActionType getEntity(EditSearchCheckSpellingActionTypeResult result) {
        SearchControl searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchCheckSpellingActionType searchCheckSpellingActionType;
        String searchCheckSpellingActionTypeName = spec.getSearchCheckSpellingActionTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            searchCheckSpellingActionType = searchControl.getSearchCheckSpellingActionTypeByName(searchCheckSpellingActionTypeName);
        } else { // EditMode.UPDATE
            searchCheckSpellingActionType = searchControl.getSearchCheckSpellingActionTypeByNameForUpdate(searchCheckSpellingActionTypeName);
        }

        if(searchCheckSpellingActionType == null) {
            addExecutionError(ExecutionErrors.UnknownSearchCheckSpellingActionTypeName.name(), searchCheckSpellingActionTypeName);
        }

        return searchCheckSpellingActionType;
    }

    @Override
    public SearchCheckSpellingActionType getLockEntity(SearchCheckSpellingActionType searchCheckSpellingActionType) {
        return searchCheckSpellingActionType;
    }

    @Override
    public void fillInResult(EditSearchCheckSpellingActionTypeResult result, SearchCheckSpellingActionType searchCheckSpellingActionType) {
        SearchControl searchControl = (SearchControl)Session.getModelController(SearchControl.class);

        result.setSearchCheckSpellingActionType(searchControl.getSearchCheckSpellingActionTypeTransfer(getUserVisit(), searchCheckSpellingActionType));
    }

    @Override
    public void doLock(SearchCheckSpellingActionTypeEdit edit, SearchCheckSpellingActionType searchCheckSpellingActionType) {
        SearchControl searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription = searchControl.getSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionType, getPreferredLanguage());
        SearchCheckSpellingActionTypeDetail searchCheckSpellingActionTypeDetail = searchCheckSpellingActionType.getLastDetail();

        edit.setSearchCheckSpellingActionTypeName(searchCheckSpellingActionTypeDetail.getSearchCheckSpellingActionTypeName());
        edit.setIsDefault(searchCheckSpellingActionTypeDetail.getIsDefault().toString());
        edit.setSortOrder(searchCheckSpellingActionTypeDetail.getSortOrder().toString());

        if(searchCheckSpellingActionTypeDescription != null) {
            edit.setDescription(searchCheckSpellingActionTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SearchCheckSpellingActionType searchCheckSpellingActionType) {
        SearchControl searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        String searchCheckSpellingActionTypeName = edit.getSearchCheckSpellingActionTypeName();
        SearchCheckSpellingActionType duplicateSearchCheckSpellingActionType = searchControl.getSearchCheckSpellingActionTypeByName(searchCheckSpellingActionTypeName);

        if(duplicateSearchCheckSpellingActionType != null && !searchCheckSpellingActionType.equals(duplicateSearchCheckSpellingActionType)) {
            addExecutionError(ExecutionErrors.DuplicateSearchCheckSpellingActionTypeName.name(), searchCheckSpellingActionTypeName);
        }
    }

    @Override
    public void doUpdate(SearchCheckSpellingActionType searchCheckSpellingActionType) {
        SearchControl searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        PartyPK partyPK = getPartyPK();
        SearchCheckSpellingActionTypeDetailValue searchCheckSpellingActionTypeDetailValue = searchControl.getSearchCheckSpellingActionTypeDetailValueForUpdate(searchCheckSpellingActionType);
        SearchCheckSpellingActionTypeDescription searchCheckSpellingActionTypeDescription = searchControl.getSearchCheckSpellingActionTypeDescriptionForUpdate(searchCheckSpellingActionType, getPreferredLanguage());
        String description = edit.getDescription();

        searchCheckSpellingActionTypeDetailValue.setSearchCheckSpellingActionTypeName(edit.getSearchCheckSpellingActionTypeName());
        searchCheckSpellingActionTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        searchCheckSpellingActionTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        searchControl.updateSearchCheckSpellingActionTypeFromValue(searchCheckSpellingActionTypeDetailValue, partyPK);

        if(searchCheckSpellingActionTypeDescription == null && description != null) {
            searchControl.createSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionType, getPreferredLanguage(), description, partyPK);
        } else {
            if(searchCheckSpellingActionTypeDescription != null && description == null) {
                searchControl.deleteSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionTypeDescription, partyPK);
            } else {
                if(searchCheckSpellingActionTypeDescription != null && description != null) {
                    SearchCheckSpellingActionTypeDescriptionValue searchCheckSpellingActionTypeDescriptionValue = searchControl.getSearchCheckSpellingActionTypeDescriptionValue(searchCheckSpellingActionTypeDescription);

                    searchCheckSpellingActionTypeDescriptionValue.setDescription(description);
                    searchControl.updateSearchCheckSpellingActionTypeDescriptionFromValue(searchCheckSpellingActionTypeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
