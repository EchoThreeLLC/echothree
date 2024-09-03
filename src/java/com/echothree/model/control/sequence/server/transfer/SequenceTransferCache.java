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

package com.echothree.model.control.sequence.server.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SequenceTransferCache
        extends BaseSequenceTransferCache<Sequence, SequenceTransfer> {
    
    /** Creates a new instance of SequenceTransferCache */
    public SequenceTransferCache(UserVisit userVisit, SequenceControl sequenceControl) {
        super(userVisit, sequenceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SequenceTransfer getSequenceTransfer(Sequence sequence) {
        var sequenceTransfer = get(sequence);
        
        if(sequenceTransfer == null) {
            var sequenceDetail = sequence.getLastDetail();
            var sequenceTypeTransferCache = sequenceControl.getSequenceTransferCaches(userVisit).getSequenceTypeTransferCache();
            var sequenceType = sequenceTypeTransferCache.getSequenceTypeTransfer(sequenceDetail.getSequenceType());
            var sequenceName = sequenceDetail.getSequenceName();
            var mask = sequenceDetail.getMask();
            var chunkSize = sequenceDetail.getChunkSize();
            var isDefault = sequenceDetail.getIsDefault();
            var sortOrder = sequenceDetail.getSortOrder();
            var value = sequenceControl.getSequenceValue(sequence).getValue();
            var description = sequenceControl.getBestSequenceDescription(sequence, getLanguage());
            
            sequenceTransfer = new SequenceTransfer(sequenceType, sequenceName, mask, chunkSize, isDefault, sortOrder, value, description);
            put(sequence, sequenceTransfer);
        }
        return sequenceTransfer;
    }
    
}
