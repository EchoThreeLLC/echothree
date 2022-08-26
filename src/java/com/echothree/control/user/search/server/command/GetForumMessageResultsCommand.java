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

import com.echothree.control.user.search.common.form.GetForumMessageResultsForm;
import com.echothree.control.user.search.common.result.GetForumMessageResultsResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.forum.server.control.ForumMessageControl;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetForumMessageResultsCommand
        extends BaseSimpleCommand<GetForumMessageResultsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of GetForumMessageResultsCommand */
    public GetForumMessageResultsCommand(UserVisitPK userVisitPK, GetForumMessageResultsForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GetForumMessageResultsResult result = SearchResultFactory.getGetForumMessageResultsResult();
        
        setupPreferredClobMimeType();
        
        if(!hasExecutionErrors()) {
            var searchControl = Session.getModelController(SearchControl.class);
            SearchKind searchKind = searchControl.getSearchKindByName(SearchKinds.FORUM_MESSAGE.name());

            if(searchKind != null) {
                String searchTypeName = form.getSearchTypeName();
                SearchType searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);

                if(searchType != null) {
                    UserVisit userVisit = getUserVisit();
                    UserVisitSearch userVisitSearch = searchControl.getUserVisitSearch(userVisit, searchType);

                    if(userVisitSearch != null) {
                        var forumMessageControl = Session.getModelController(ForumMessageControl.class);

                        if(session.hasLimit(com.echothree.model.data.search.server.factory.SearchResultFactory.class)) {
                            result.setForumMessageResultCount(SearchLogic.getInstance().countSearchResults(userVisitSearch.getSearch()));
                        }

                        result.setForumMessageResults(forumMessageControl.getForumMessageResultTransfers(userVisit, userVisitSearch));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUserVisitSearch.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSearchTypeName.name(), SearchKinds.FORUM_MESSAGE.name(), searchTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSearchKindName.name(), SearchKinds.FORUM_MESSAGE.name());
            }
        }
        
        return result;
    }
}
