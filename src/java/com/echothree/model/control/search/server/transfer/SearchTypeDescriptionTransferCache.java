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

package com.echothree.model.control.search.server.transfer;

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.search.remote.transfer.SearchTypeDescriptionTransfer;
import com.echothree.model.control.search.remote.transfer.SearchTypeTransfer;
import com.echothree.model.control.search.server.SearchControl;
import com.echothree.model.data.search.server.entity.SearchTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SearchTypeDescriptionTransferCache
        extends BaseSearchDescriptionTransferCache<SearchTypeDescription, SearchTypeDescriptionTransfer> {
    
    /** Creates a new instance of SearchTypeDescriptionTransferCache */
    public SearchTypeDescriptionTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
    }
    
    public SearchTypeDescriptionTransfer getSearchTypeDescriptionTransfer(SearchTypeDescription searchTypeDescription) {
        SearchTypeDescriptionTransfer searchTypeDescriptionTransfer = get(searchTypeDescription);
        
        if(searchTypeDescriptionTransfer == null) {
            SearchTypeTransfer searchTypeTransfer = searchControl.getSearchTypeTransfer(userVisit, searchTypeDescription.getSearchType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, searchTypeDescription.getLanguage());
            
            searchTypeDescriptionTransfer = new SearchTypeDescriptionTransfer(languageTransfer, searchTypeTransfer, searchTypeDescription.getDescription());
            put(searchTypeDescription, searchTypeDescriptionTransfer);
        }
        
        return searchTypeDescriptionTransfer;
    }
    
}
