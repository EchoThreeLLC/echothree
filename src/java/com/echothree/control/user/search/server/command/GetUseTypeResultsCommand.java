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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.GetUseTypeResultsForm;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
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

public class GetUseTypeResultsCommand
        extends BaseSimpleCommand<GetUseTypeResultsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of GetUseTypeResultsCommand */
    public GetUseTypeResultsCommand(UserVisitPK userVisitPK, GetUseTypeResultsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getGetUseTypeResultsResult();
        var searchControl = Session.getModelController(SearchControl.class);
        var searchKind = searchControl.getSearchKindByName(SearchKinds.USE_TYPE.name());
        
        if(searchKind != null) {
            var searchTypeName = form.getSearchTypeName();
            var searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);
            
            if(searchType != null) {
                var userVisit = getUserVisit();
                var userVisitSearch = searchControl.getUserVisitSearch(userVisit, searchType);
                
                if(userVisitSearch != null) {
                    var useTypeControl = Session.getModelController(UseTypeControl.class);

                    if(session.hasLimit(com.echothree.model.data.search.server.factory.SearchResultFactory.class)) {
                        result.setUseTypeResultCount(SearchLogic.getInstance().countSearchResults(userVisitSearch.getSearch()));
                    }

                    result.setUseTypeResults(useTypeControl.getUseTypeResultTransfers(userVisit, userVisitSearch));
                } else {
                    addExecutionError(ExecutionErrors.UnknownUserVisitSearch.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSearchTypeName.name(), SearchKinds.USE_TYPE.name(), searchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), SearchKinds.USE_TYPE.name());
        }
        
        return result;
    }
}
