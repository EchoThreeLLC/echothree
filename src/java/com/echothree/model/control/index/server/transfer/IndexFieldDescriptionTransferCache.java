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

package com.echothree.model.control.index.server.transfer;

import com.echothree.model.control.index.common.transfer.IndexFieldDescriptionTransfer;
import com.echothree.model.control.index.common.transfer.IndexFieldTransfer;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.index.server.entity.IndexFieldDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class IndexFieldDescriptionTransferCache
        extends BaseIndexDescriptionTransferCache<IndexFieldDescription, IndexFieldDescriptionTransfer> {
    
    /** Creates a new instance of IndexFieldDescriptionTransferCache */
    public IndexFieldDescriptionTransferCache(UserVisit userVisit, IndexControl indexControl) {
        super(userVisit, indexControl);
    }
    
    public IndexFieldDescriptionTransfer getIndexFieldDescriptionTransfer(IndexFieldDescription indexFieldDescription) {
        IndexFieldDescriptionTransfer indexFieldDescriptionTransfer = get(indexFieldDescription);
        
        if(indexFieldDescriptionTransfer == null) {
            IndexFieldTransfer indexFieldTransfer = indexControl.getIndexFieldTransfer(userVisit, indexFieldDescription.getIndexField());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, indexFieldDescription.getLanguage());
            
            indexFieldDescriptionTransfer = new IndexFieldDescriptionTransfer(languageTransfer, indexFieldTransfer, indexFieldDescription.getDescription());
            put(indexFieldDescription, indexFieldDescriptionTransfer);
        }
        
        return indexFieldDescriptionTransfer;
    }
    
}
