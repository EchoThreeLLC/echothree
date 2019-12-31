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

package com.echothree.model.control.search.server.logic;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.search.common.exception.DuplicateSearchResultActionException;
import com.echothree.model.control.search.common.exception.UnknownCachedExecutedSearchResultException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchDefaultOperatorException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchSortDirectionException;
import com.echothree.model.control.search.common.exception.UnknownDefaultSearchSortOrderException;
import com.echothree.model.control.search.common.exception.UnknownSearchCheckSpellingActionTypeNameException;
import com.echothree.model.control.search.common.exception.UnknownSearchDefaultOperatorNameException;
import com.echothree.model.control.search.common.exception.UnknownSearchKindNameException;
import com.echothree.model.control.search.common.exception.UnknownSearchResultActionTypeNameException;
import com.echothree.model.control.search.common.exception.UnknownSearchResultException;
import com.echothree.model.control.search.common.exception.UnknownSearchSortDirectionNameException;
import com.echothree.model.control.search.common.exception.UnknownSearchSortOrderNameException;
import com.echothree.model.control.search.common.exception.UnknownSearchTypeNameException;
import com.echothree.model.control.search.common.exception.UnknownSearchUseTypeNameException;
import com.echothree.model.control.search.common.exception.UnknownUserVisitSearchException;
import com.echothree.model.control.search.common.transfer.SearchCheckSpellingActionTypeTransfer;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.control.search.server.database.CachedSearchToInvalidateQuery;
import com.echothree.model.control.search.server.database.CachedSearchToInvalidateResult;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.search.server.entity.CachedExecutedSearch;
import com.echothree.model.data.search.server.entity.CachedSearch;
import com.echothree.model.data.search.server.entity.Search;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchResultAction;
import com.echothree.model.data.search.server.entity.SearchResultActionType;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.Session;

public class SearchLogic
        extends BaseLogic {

    private SearchLogic() {
        super();
    }

    private static class SearchLogicHolder {
        static SearchLogic instance = new SearchLogic();
    }

    public static SearchLogic getInstance() {
        return SearchLogicHolder.instance;
    }
    
    public SearchDefaultOperator getSearchDefaultOperatorByName(final ExecutionErrorAccumulator eea, final String searchDefaultOperatorName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchDefaultOperator searchDefaultOperator = searchControl.getSearchDefaultOperatorByName(searchDefaultOperatorName);

        if(searchDefaultOperator == null) {
            handleExecutionError(UnknownSearchDefaultOperatorNameException.class, eea, ExecutionErrors.UnknownSearchDefaultOperatorName.name(), searchDefaultOperatorName);
        }

        return searchDefaultOperator;
    }
    
    public SearchDefaultOperator getDefaultSearchDefaultOperator(final ExecutionErrorAccumulator eea) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchDefaultOperator searchDefaultOperator = searchControl.getDefaultSearchDefaultOperator();

        if(searchDefaultOperator == null) {
            handleExecutionError(UnknownDefaultSearchDefaultOperatorException.class, eea, ExecutionErrors.UnknownDefaultSearchDefaultOperator.name());
        }

        return searchDefaultOperator;
    }

    public SearchSortDirection getSearchSortDirectionByName(final ExecutionErrorAccumulator eea, final String searchSortDirectionName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchSortDirection searchSortDirection = searchControl.getSearchSortDirectionByName(searchSortDirectionName);

        if(searchSortDirection == null) {
            handleExecutionError(UnknownSearchSortDirectionNameException.class, eea, ExecutionErrors.UnknownSearchSortDirectionName.name(), searchSortDirectionName);
        }

        return searchSortDirection;
    }
    
    public SearchSortDirection getDefaultSearchSortDirection(final ExecutionErrorAccumulator eea) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchSortDirection searchSortDirection = searchControl.getDefaultSearchSortDirection();

        if(searchSortDirection == null) {
            handleExecutionError(UnknownDefaultSearchSortDirectionException.class, eea, ExecutionErrors.UnknownDefaultSearchSortDirection.name());
        }

        return searchSortDirection;
    }

    public SearchUseType getSearchUseTypeByName(final ExecutionErrorAccumulator eea, final String searchUseTypeName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchUseType searchUseType = searchControl.getSearchUseTypeByName(searchUseTypeName);

        if(searchUseType == null) {
            handleExecutionError(UnknownSearchUseTypeNameException.class, eea, ExecutionErrors.UnknownSearchUseTypeName.name(), searchUseTypeName);
        }

        return searchUseType;
    }
    
    public SearchResultActionType getSearchResultActionTypeByName(final ExecutionErrorAccumulator eea, final String searchResultActionTypeName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchResultActionType searchResultActionType = searchControl.getSearchResultActionTypeByName(searchResultActionTypeName);

        if(searchResultActionType == null) {
            handleExecutionError(UnknownSearchResultActionTypeNameException.class, eea, ExecutionErrors.UnknownSearchResultActionTypeName.name(), searchResultActionTypeName);
        }

        return searchResultActionType;
    }
    
    public SearchCheckSpellingActionType getSearchCheckSpellingActionTypeByName(final ExecutionErrorAccumulator eea, final String searchCheckSpellingActionTypeName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchCheckSpellingActionType searchCheckSpellingActionType = searchControl.getSearchCheckSpellingActionTypeByName(searchCheckSpellingActionTypeName);

        if(searchCheckSpellingActionType == null) {
            handleExecutionError(UnknownSearchCheckSpellingActionTypeNameException.class, eea, ExecutionErrors.UnknownSearchCheckSpellingActionTypeName.name(), searchCheckSpellingActionTypeName);
        }

        return searchCheckSpellingActionType;
    }
    
    public SearchCheckSpellingActionTypeTransfer getSearchCheckSpellingActionTypeTransferByName(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String searchCheckSpellingActionTypeName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        
        return searchControl.getSearchCheckSpellingActionTypeTransfer(userVisit, getSearchCheckSpellingActionTypeByName(eea, searchCheckSpellingActionTypeName));
    }
    
    public SearchKind getSearchKindByName(final ExecutionErrorAccumulator eea, final String searchKindName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchKind searchKind = searchControl.getSearchKindByName(searchKindName);

        if(searchKind == null) {
            handleExecutionError(UnknownSearchKindNameException.class, eea, ExecutionErrors.UnknownSearchKindName.name(), searchKindName);
        }

        return searchKind;
    }

    public SearchType getSearchTypeByName(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchTypeName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchType searchType = searchControl.getSearchTypeByName(searchKind, searchTypeName);

        if(searchType == null) {
            handleExecutionError(UnknownSearchTypeNameException.class, eea, ExecutionErrors.UnknownSearchTypeName.name(),
                    searchKind.getLastDetail().getSearchKindName(), searchTypeName);
        }

        return searchType;
    }

    public SearchType getSearchTypeByName(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchTypeName) {
        SearchKind searchKind = getSearchKindByName(eea, searchKindName);
        
        return hasExecutionErrors(eea) ? null : getSearchTypeByName(eea, searchKind, searchTypeName);
    }
    
    public SearchSortOrder getSearchSortOrderByName(final ExecutionErrorAccumulator eea, final SearchKind searchKind, final String searchSortOrderName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchSortOrder searchSortOrder = searchControl.getSearchSortOrderByName(searchKind, searchSortOrderName);

        if(searchSortOrder == null) {
            handleExecutionError(UnknownSearchSortOrderNameException.class, eea, ExecutionErrors.UnknownSearchSortOrderName.name(),
                    searchKind.getLastDetail().getSearchKindName(), searchSortOrderName);
        }

        return searchSortOrder;
    }

    public SearchSortOrder getSearchSortOrderByName(final ExecutionErrorAccumulator eea, final String searchKindName, final String searchSortOrderName) {
        SearchKind searchKind = getSearchKindByName(eea, searchKindName);
        
        return hasExecutionErrors(eea) ? null : getSearchSortOrderByName(eea, searchKind, searchSortOrderName);
    }
    
    public SearchSortOrder getDefaultSearchSortOrder(final ExecutionErrorAccumulator eea, final SearchKind searchKind) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        SearchSortOrder searchSortOrder = searchControl.getDefaultSearchSortOrder(searchKind);

        if(searchSortOrder == null) {
            handleExecutionError(UnknownDefaultSearchSortOrderException.class, eea, ExecutionErrors.UnknownDefaultSearchSortOrder.name(),
                    searchKind.getLastDetail().getSearchKindName());
        }

        return searchSortOrder;
    }

    public SearchSortOrder getDefaultSearchSortOrder(final ExecutionErrorAccumulator eea, final String searchKindName) {
        SearchKind searchKind = getSearchKindByName(eea, searchKindName);
        
        return hasExecutionErrors(eea) ? null : getDefaultSearchSortOrder(eea, searchKind);
    }
    
    public UserVisitSearch getUserVisitSearch(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final SearchType searchType) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        UserVisitSearch userVisitSearch = searchControl.getUserVisitSearch(userVisit, searchType);
        
        if(userVisitSearch == null) {
            handleExecutionError(UnknownUserVisitSearchException.class, eea, ExecutionErrors.UnknownUserVisitSearch.name());
        }
        
        return userVisitSearch;
    }
    
    public UserVisitSearch getUserVisitSearchByName(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String searchKindName,
            final String searchTypeName) {
        SearchType searchType = getSearchTypeByName(eea, searchKindName, searchTypeName);
        UserVisitSearch userVisitSearch = hasExecutionErrors(eea) ? null : getUserVisitSearch(eea, userVisit, searchType);
        
        return userVisitSearch;
    }
    
    public void removeUserVisitSearch(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String searchKindName,
            final String searchTypeName) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        UserVisitSearch userVisitSearch = getUserVisitSearchByName(eea, userVisit, searchKindName, searchTypeName);

        if(!hasExecutionErrors(eea)) {
            searchControl.removeUserVisitSearch(userVisitSearch);
        }
    }
    
    public Integer countSearchResults(final Search search) {
        var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
        CachedSearch cachedSearch = search.getCachedSearch();
        int count;
        
        if(cachedSearch == null) {
            count = searchControl.countSearchResults(search);
        } else {
            CachedExecutedSearch cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);
            
            count = searchControl.countCachedExecutedSearchResults(cachedExecutedSearch);
        }
        
        return count;
    }
    
    public Integer countUserVisitSearchResults(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String searchKindName,
            final String searchTypeName) {
        UserVisitSearch userVisitSearch = getUserVisitSearchByName(eea, userVisit, searchKindName, searchTypeName);
        Integer count = hasExecutionErrors(eea) ? null : countSearchResults(userVisitSearch.getSearch());

        return count;
    }
    
    public SearchResultAction createSearchResultAction(final ExecutionErrorAccumulator eea, final UserVisit userVisit, final String searchKindName,
            final String searchTypeName, final String searchResultActionTypeName, final BaseEntity baseEntity, final BasePK createdBy) {
        SearchResultAction itemSearchResultAction = null;
        UserVisitSearch userVisitSearch = getUserVisitSearchByName(eea, userVisit, searchKindName, searchTypeName);

        if(eea == null || !eea.hasExecutionErrors()) {
            SearchResultActionType searchResultActionType = getSearchResultActionTypeByName(eea, searchResultActionTypeName);
            
            if(eea == null || !eea.hasExecutionErrors()) {
                var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
                var searchControl = (SearchControl)Session.getModelController(SearchControl.class);
                Search search = userVisitSearch.getSearch();
                CachedSearch cachedSearch = search.getCachedSearch();
                BasePK basePK = baseEntity.getPrimaryKey();
                EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(basePK);

                if(cachedSearch == null) {
                    if(!searchControl.searchResultExists(search, entityInstance)) {
                        handleExecutionError(UnknownSearchResultException.class, eea,
                                ExecutionErrors.UnknownSearchResult.name(), searchKindName, searchTypeName, basePK.getEntityRef());
                    }
                } else {
                    CachedExecutedSearch cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

                    if(!searchControl.cachedExecutedSearchResultExists(cachedExecutedSearch, entityInstance)) {
                        handleExecutionError(UnknownCachedExecutedSearchResultException.class, eea,
                                ExecutionErrors.UnknownCachedExecutedSearchResult.name(), searchKindName, searchTypeName, basePK.getEntityRef());
                    }
                }

                if(eea == null || !eea.hasExecutionErrors()) {
                    if(searchControl.searchResultActionExists(search, searchResultActionType, entityInstance)) {
                        handleExecutionError(DuplicateSearchResultActionException.class, eea, ExecutionErrors.DuplicateSearchResultAction.name(),
                                searchKindName, searchTypeName, searchResultActionTypeName, basePK.getEntityRef());
                    } else {
                        searchControl.createSearchResultAction(search, searchResultActionType, getSession().START_TIME_LONG, entityInstance, createdBy);
                    }
                }
            }
        }
        
        return itemSearchResultAction;
    }
    
    public void invalidateCachedSearchesByIndex(Index index) {
        for(CachedSearchToInvalidateResult cachedSearchToInvalidateResult : new CachedSearchToInvalidateQuery().execute(index)) {
            cachedSearchToInvalidateResult.getCachedSearchStatus().setIsConsistent(Boolean.FALSE);
        }
    }
    
}
