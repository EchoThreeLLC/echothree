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

package com.echothree.model.control.search.server.database;

import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EntityMultipleListItemAttributeFacetQuery
        extends BaseFacetQuery<EntityMultipleListItemAttributeFacetResult> {
    
    public EntityMultipleListItemAttributeFacetQuery(UserVisitSearch userVisitSearch) {
        super(userVisitSearch.getSearch().getCachedSearch() == null
                ? "SELECT eli_entitylistitemid AS EntityListItem, COUNT(*) AS Count "
                + "FROM searchresults, entitylistitems, entitylistitemdetails, entitymultiplelistitemattributes "
                + "WHERE srchr_srch_searchid = ? "
                + "AND eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ? "
                + "AND srchr_eni_entityinstanceid = emlia_eni_entityinstanceid AND emlia_eli_entitylistitemid = eli_entitylistitemid AND emlia_thrutime = ? "
                + "GROUP BY eli_entitylistitemid "
                + "ORDER BY Count DESC"
                : "SELECT eli_entitylistitemid AS EntityListItem, COUNT(*) AS Count "
                + "FROM cachedexecutedsearchresults, entitylistitems, entitylistitemdetails, entitymultiplelistitemattributes "
                + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? "
                + "AND eli_activedetailid = elidt_entitylistitemdetailid AND elidt_ena_entityattributeid = ? "
                + "AND cxsrchr_eni_entityinstanceid = emlia_eni_entityinstanceid AND emlia_eli_entitylistitemid = eli_entitylistitemid AND emlia_thrutime = ? "
                + "GROUP BY eli_entitylistitemid "
                + "ORDER BY Count DESC",
                userVisitSearch);
    }
    
    public List<EntityMultipleListItemAttributeFacetResult> execute(EntityAttribute entityAttribute) {
        return super.execute(cachedExecutedSearch == null ? search : cachedExecutedSearch, entityAttribute, Session.MAX_TIME_LONG);
    }
    
}
