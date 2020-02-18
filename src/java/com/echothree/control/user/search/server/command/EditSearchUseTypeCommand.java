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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.edit.SearchEditFactory;
import com.echothree.control.user.search.common.edit.SearchUseTypeEdit;
import com.echothree.control.user.search.common.form.EditSearchUseTypeForm;
import com.echothree.control.user.search.common.result.EditSearchUseTypeResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.control.user.search.common.spec.SearchUseTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.search.server.entity.SearchUseTypeDescription;
import com.echothree.model.data.search.server.entity.SearchUseTypeDetail;
import com.echothree.model.data.search.server.value.SearchUseTypeDescriptionValue;
import com.echothree.model.data.search.server.value.SearchUseTypeDetailValue;
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

public class EditSearchUseTypeCommand
        extends BaseAbstractEditCommand<SearchUseTypeSpec, SearchUseTypeEdit, EditSearchUseTypeResult, SearchUseType, SearchUseType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SearchUseType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditSearchUseTypeCommand */
    public EditSearchUseTypeCommand(UserVisitPK userVisitPK, EditSearchUseTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditSearchUseTypeResult getResult() {
        return SearchResultFactory.getEditSearchUseTypeResult();
    }

    @Override
    public SearchUseTypeEdit getEdit() {
        return SearchEditFactory.getSearchUseTypeEdit();
    }

    @Override
    public SearchUseType getEntity(EditSearchUseTypeResult result) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchUseType searchUseType;
        String searchUseTypeName = spec.getSearchUseTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            searchUseType = searchControl.getSearchUseTypeByName(searchUseTypeName);
        } else { // EditMode.UPDATE
            searchUseType = searchControl.getSearchUseTypeByNameForUpdate(searchUseTypeName);
        }

        if(searchUseType == null) {
            addExecutionError(ExecutionErrors.UnknownSearchUseTypeName.name(), searchUseTypeName);
        }

        return searchUseType;
    }

    @Override
    public SearchUseType getLockEntity(SearchUseType searchUseType) {
        return searchUseType;
    }

    @Override
    public void fillInResult(EditSearchUseTypeResult result, SearchUseType searchUseType) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);

        result.setSearchUseType(searchControl.getSearchUseTypeTransfer(getUserVisit(), searchUseType));
    }

    @Override
    public void doLock(SearchUseTypeEdit edit, SearchUseType searchUseType) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchUseTypeDescription searchUseTypeDescription = searchControl.getSearchUseTypeDescription(searchUseType, getPreferredLanguage());
        SearchUseTypeDetail searchUseTypeDetail = searchUseType.getLastDetail();

        edit.setSearchUseTypeName(searchUseTypeDetail.getSearchUseTypeName());
        edit.setIsDefault(searchUseTypeDetail.getIsDefault().toString());
        edit.setSortOrder(searchUseTypeDetail.getSortOrder().toString());

        if(searchUseTypeDescription != null) {
            edit.setDescription(searchUseTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SearchUseType searchUseType) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        String searchUseTypeName = edit.getSearchUseTypeName();
        SearchUseType duplicateSearchUseType = searchControl.getSearchUseTypeByName(searchUseTypeName);

        if(duplicateSearchUseType != null && !searchUseType.equals(duplicateSearchUseType)) {
            addExecutionError(ExecutionErrors.DuplicateSearchUseTypeName.name(), searchUseTypeName);
        }
    }

    @Override
    public void doUpdate(SearchUseType searchUseType) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        PartyPK partyPK = getPartyPK();
        SearchUseTypeDetailValue searchUseTypeDetailValue = searchControl.getSearchUseTypeDetailValueForUpdate(searchUseType);
        SearchUseTypeDescription searchUseTypeDescription = searchControl.getSearchUseTypeDescriptionForUpdate(searchUseType, getPreferredLanguage());
        String description = edit.getDescription();

        searchUseTypeDetailValue.setSearchUseTypeName(edit.getSearchUseTypeName());
        searchUseTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        searchUseTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        searchControl.updateSearchUseTypeFromValue(searchUseTypeDetailValue, partyPK);

        if(searchUseTypeDescription == null && description != null) {
            searchControl.createSearchUseTypeDescription(searchUseType, getPreferredLanguage(), description, partyPK);
        } else {
            if(searchUseTypeDescription != null && description == null) {
                searchControl.deleteSearchUseTypeDescription(searchUseTypeDescription, partyPK);
            } else {
                if(searchUseTypeDescription != null && description != null) {
                    SearchUseTypeDescriptionValue searchUseTypeDescriptionValue = searchControl.getSearchUseTypeDescriptionValue(searchUseTypeDescription);

                    searchUseTypeDescriptionValue.setDescription(description);
                    searchControl.updateSearchUseTypeDescriptionFromValue(searchUseTypeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
