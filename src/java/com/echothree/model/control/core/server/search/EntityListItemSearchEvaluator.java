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

package com.echothree.model.control.core.server.search;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexTypes;
import com.echothree.model.control.index.server.analysis.EntityListItemAnalyzer;
import com.echothree.model.control.search.common.SearchSortOrders;
import com.echothree.model.control.search.common.SearchSortDirections;
import com.echothree.model.control.search.server.search.BaseSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.SortField;

public class EntityListItemSearchEvaluator
        extends BaseSearchEvaluator {
    
    /** Creates a new instance of EntityListItemSearchEvaluator */
    public EntityListItemSearchEvaluator(UserVisit userVisit, Language language, SearchType searchType, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection, SearchUseType searchUseType) {
        super(userVisit, searchDefaultOperator, searchType, searchSortOrder, searchSortDirection, searchUseType, ComponentVendors.ECHOTHREE.name(),
                EntityTypes.EntityListItem.name(), IndexTypes.ENTITY_LIST_ITEM.name(), language, null);
        
        setField(IndexConstants.IndexField_Description);
    }

    /** Determines if the result of the search may be cached. For Items, the only field that may be used is the description, "q." If any
     * others are utilized, the result may not be cached.
     */
    @Override
    protected boolean isResultCachable() {
        return countParameters() == 1 && q != null;
    }
    
    @Override
    public SortField[] getSortFields(String searchSortOrderName) {
        SortField[] sortFields = null;
        boolean reverse = searchSortDirection.getLastDetail().getSearchSortDirectionName().equals(SearchSortDirections.DESCENDING.name());
        
        if(searchSortOrderName.equals(SearchSortOrders.SCORE.name())) {
            sortFields = new SortField[]{
                new SortField(null, SortField.Type.SCORE, reverse),
                new SortField(IndexConstants.IndexField_Description + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse)
            };
        } else if(searchSortOrderName.equals(SearchSortOrders.DESCRIPTION.name())) {
            sortFields = new SortField[]{new SortField(IndexConstants.IndexField_Description + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.ENTITY_LIST_ITEM_NAME.name())) {
            sortFields = new SortField[]{
                new SortField(IndexConstants.IndexField_ComponentVendorName + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse),
                new SortField(IndexConstants.IndexField_EntityTypeName + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse),
                new SortField(IndexConstants.IndexField_EntityAttributeName + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse),
                new SortField(IndexConstants.IndexField_EntityListItemName + IndexConstants.IndexFieldVariation_Separator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse)
            };
        } else if(searchSortOrderName.equals(SearchSortOrders.CREATED_TIME.name())) {
            sortFields = new SortField[]{new SortField(IndexConstants.IndexField_CreatedTime, SortField.Type.LONG, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.MODIFIED_TIME.name())) {
            sortFields = new SortField[]{new SortField(IndexConstants.IndexField_ModifiedTime, SortField.Type.LONG, reverse)};
        }
        
        return sortFields;
    }
    
    @Override
    public Analyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new EntityListItemAnalyzer(eea, language, entityType);
    }
    
    protected EntityInstancePKHolder executeQSearch(final ExecutionErrorAccumulator eea, EntityInstancePKHolder resultSet) {
        if(resultSet == null || resultSet.size() > 0) {
            if(q != null) {
                if(resultSet == null || resultSet.size() > 0) {
                    EntityInstancePKHolder entityInstancePKHolder = executeQuery(eea);

                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
        }
        
        return resultSet;
    }
    
    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = super.executeSearch(eea);
        
        resultSet = executeQSearch(eea, resultSet);
        
        return resultSet;
    }

}
