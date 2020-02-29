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

package com.echothree.model.control.batch.server.transfer;

import com.echothree.model.control.batch.common.transfer.BatchTypeDescriptionTransfer;
import com.echothree.model.control.batch.common.transfer.BatchTypeTransfer;
import com.echothree.model.control.batch.server.BatchControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.batch.server.entity.BatchTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class BatchTypeDescriptionTransferCache
        extends BaseBatchDescriptionTransferCache<BatchTypeDescription, BatchTypeDescriptionTransfer> {
    
    /** Creates a new instance of BatchTypeDescriptionTransferCache */
    public BatchTypeDescriptionTransferCache(UserVisit userVisit, BatchControl batchControl) {
        super(userVisit, batchControl);
    }
    
    @Override
    public BatchTypeDescriptionTransfer getTransfer(BatchTypeDescription batchTypeDescription) {
        BatchTypeDescriptionTransfer batchTypeDescriptionTransfer = get(batchTypeDescription);
        
        if(batchTypeDescriptionTransfer == null) {
            BatchTypeTransfer batchTypeTransfer = batchControl.getBatchTypeTransfer(userVisit, batchTypeDescription.getBatchType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, batchTypeDescription.getLanguage());
            
            batchTypeDescriptionTransfer = new BatchTypeDescriptionTransfer(languageTransfer, batchTypeTransfer, batchTypeDescription.getDescription());
            put(batchTypeDescription, batchTypeDescriptionTransfer);
        }
        
        return batchTypeDescriptionTransfer;
    }
    
}
