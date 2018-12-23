// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.search.common.transfer.SearchResultActionTypeDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchResultActionTypeTransfer;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.data.search.server.entity.SearchResultActionTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SearchResultActionTypeDescriptionTransferCache
        extends BaseSearchDescriptionTransferCache<SearchResultActionTypeDescription, SearchResultActionTypeDescriptionTransfer> {
    
    /** Creates a new instance of SearchResultActionTypeDescriptionTransferCache */
    public SearchResultActionTypeDescriptionTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
    }
    
    public SearchResultActionTypeDescriptionTransfer getSearchResultActionTypeDescriptionTransfer(SearchResultActionTypeDescription searchResultActionTypeDescription) {
        SearchResultActionTypeDescriptionTransfer searchResultActionTypeDescriptionTransfer = get(searchResultActionTypeDescription);
        
        if(searchResultActionTypeDescriptionTransfer == null) {
            SearchResultActionTypeTransfer searchResultActionTypeTransfer = searchControl.getSearchResultActionTypeTransfer(userVisit, searchResultActionTypeDescription.getSearchResultActionType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, searchResultActionTypeDescription.getLanguage());
            
            searchResultActionTypeDescriptionTransfer = new SearchResultActionTypeDescriptionTransfer(languageTransfer, searchResultActionTypeTransfer, searchResultActionTypeDescription.getDescription());
            put(searchResultActionTypeDescription, searchResultActionTypeDescriptionTransfer);
        }
        return searchResultActionTypeDescriptionTransfer;
    }
    
}
