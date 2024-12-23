// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.content.server.control;

import com.echothree.model.control.content.common.transfer.ContentCatalogResultTransfer;
import com.echothree.model.control.content.server.graphql.ContentCatalogObject;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.data.content.common.pk.ContentCatalogPK;
import com.echothree.model.data.content.server.factory.ContentCatalogFactory;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContentCatalogControl
        extends BaseModelControl {

    /** Creates a new instance of ContentCatalogControl */
    public ContentCatalogControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Content Catalog Searches
    // --------------------------------------------------------------------------------

    public List<ContentCatalogResultTransfer> getContentCatalogResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var contentCatalogResultTransfers = new ArrayList<ContentCatalogResultTransfer>();
        var includeContentCatalog = false;

        var options = session.getOptions();
        if(options != null) {
            includeContentCatalog = options.contains(SearchOptions.ContentCatalogResultIncludeContentCatalog);
        }

        try {
            var contentControl = Session.getModelController(ContentControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var contentCatalog = ContentCatalogFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ContentCatalogPK(rs.getLong(1)));
                    var contentCatalogDetail = contentCatalog.getLastDetail();

                    contentCatalogResultTransfers.add(new ContentCatalogResultTransfer(contentCatalogDetail.getContentCollection().getLastDetail().getContentCollectionName(),
                            contentCatalogDetail.getContentCatalogName(),
                            includeContentCatalog ? contentControl.getContentCatalogTransfer(userVisit, contentCatalog) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCatalogResultTransfers;
    }


    public List<ContentCatalogObject> getContentCatalogObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var contentControl = Session.getModelController(ContentControl.class);
        var searchControl = Session.getModelController(SearchControl.class);
        var contentCatalogObjects = new ArrayList<ContentCatalogObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var contentCatalog = contentControl.getContentCatalogByPK(new ContentCatalogPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                contentCatalogObjects.add(new ContentCatalogObject(contentCatalog));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return contentCatalogObjects;
    }
    
}
