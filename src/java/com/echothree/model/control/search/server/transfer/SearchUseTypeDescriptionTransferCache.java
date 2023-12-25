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

package com.echothree.model.control.search.server.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.search.common.transfer.SearchUseTypeDescriptionTransfer;
import com.echothree.model.control.search.common.transfer.SearchUseTypeTransfer;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.search.server.entity.SearchUseTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SearchUseTypeDescriptionTransferCache
        extends BaseSearchDescriptionTransferCache<SearchUseTypeDescription, SearchUseTypeDescriptionTransfer> {
    
    /** Creates a new instance of SearchUseTypeDescriptionTransferCache */
    public SearchUseTypeDescriptionTransferCache(UserVisit userVisit, SearchControl searchControl) {
        super(userVisit, searchControl);
    }
    
    public SearchUseTypeDescriptionTransfer getSearchUseTypeDescriptionTransfer(SearchUseTypeDescription searchUseTypeDescription) {
        SearchUseTypeDescriptionTransfer searchUseTypeDescriptionTransfer = get(searchUseTypeDescription);
        
        if(searchUseTypeDescriptionTransfer == null) {
            SearchUseTypeTransfer searchUseTypeTransfer = searchControl.getSearchUseTypeTransfer(userVisit, searchUseTypeDescription.getSearchUseType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, searchUseTypeDescription.getLanguage());
            
            searchUseTypeDescriptionTransfer = new SearchUseTypeDescriptionTransfer(languageTransfer, searchUseTypeTransfer, searchUseTypeDescription.getDescription());
            put(searchUseTypeDescription, searchUseTypeDescriptionTransfer);
        }
        return searchUseTypeDescriptionTransfer;
    }
    
}
