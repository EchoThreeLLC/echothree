// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.search.server.transfer;

import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.common.transfer.SearchCheckSpellingActionTypeTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionType;
import com.echothree.model.data.search.server.entity.SearchCheckSpellingActionTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class SearchCheckSpellingActionTypeTransferCache
        extends BaseSearchTransferCache<SearchCheckSpellingActionType, SearchCheckSpellingActionTypeTransfer> {

    /** Creates a new instance of SearchCheckSpellingActionTypeTransferCache */
    public SearchCheckSpellingActionTypeTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(SearchOptions.SearchCheckSpellingActionTypeIncludeKey));
            setIncludeGuid(options.contains(SearchOptions.SearchCheckSpellingActionTypeIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public SearchCheckSpellingActionTypeTransfer getSearchCheckSpellingActionTypeTransfer(SearchCheckSpellingActionType searchCheckSpellingActionType) {
        SearchCheckSpellingActionTypeTransfer searchCheckSpellingActionTypeTransfer = get(searchCheckSpellingActionType);

        if(searchCheckSpellingActionTypeTransfer == null) {
            SearchCheckSpellingActionTypeDetail searchCheckSpellingActionTypeDetail = searchCheckSpellingActionType.getLastDetail();
            String searchCheckSpellingActionTypeName = searchCheckSpellingActionTypeDetail.getSearchCheckSpellingActionTypeName();
            Boolean isDefault = searchCheckSpellingActionTypeDetail.getIsDefault();
            Integer sortOrder = searchCheckSpellingActionTypeDetail.getSortOrder();
            String description = searchControl.getBestSearchCheckSpellingActionTypeDescription(searchCheckSpellingActionType, getLanguage());

            searchCheckSpellingActionTypeTransfer = new SearchCheckSpellingActionTypeTransfer(searchCheckSpellingActionTypeName, isDefault, sortOrder, description);
            put(searchCheckSpellingActionType, searchCheckSpellingActionTypeTransfer);
        }

        return searchCheckSpellingActionTypeTransfer;
    }

}
