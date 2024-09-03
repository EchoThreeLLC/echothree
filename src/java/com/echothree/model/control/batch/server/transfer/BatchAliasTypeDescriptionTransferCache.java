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

package com.echothree.model.control.batch.server.transfer;

import com.echothree.model.control.batch.common.transfer.BatchAliasTypeDescriptionTransfer;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.data.batch.server.entity.BatchAliasTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class BatchAliasTypeDescriptionTransferCache
        extends BaseBatchDescriptionTransferCache<BatchAliasTypeDescription, BatchAliasTypeDescriptionTransfer> {
    
    /** Creates a new instance of BatchAliasTypeDescriptionTransferCache */
    public BatchAliasTypeDescriptionTransferCache(UserVisit userVisit, BatchControl batchControl) {
        super(userVisit, batchControl);
    }
    
    @Override
    public BatchAliasTypeDescriptionTransfer getTransfer(BatchAliasTypeDescription batchAliasTypeDescription) {
        var batchAliasTypeDescriptionTransfer = get(batchAliasTypeDescription);
        
        if(batchAliasTypeDescriptionTransfer == null) {
            var batchAliasTypeTransfer = batchControl.getBatchAliasTypeTransfer(userVisit, batchAliasTypeDescription.getBatchAliasType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, batchAliasTypeDescription.getLanguage());
            
            batchAliasTypeDescriptionTransfer = new BatchAliasTypeDescriptionTransfer(languageTransfer, batchAliasTypeTransfer, batchAliasTypeDescription.getDescription());
            put(batchAliasTypeDescription, batchAliasTypeDescriptionTransfer);
        }
        
        return batchAliasTypeDescriptionTransfer;
    }
    
}
