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

package com.echothree.model.control.sequence.server.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SequenceTypeTransferCache
        extends BaseSequenceTransferCache<SequenceType, SequenceTypeTransfer> {
    
    /** Creates a new instance of SequenceTypeTransferCache */
    public SequenceTypeTransferCache(UserVisit userVisit, SequenceControl sequenceControl) {
        super(userVisit, sequenceControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SequenceTypeTransfer getSequenceTypeTransfer(SequenceType sequenceType) {
        var sequenceTypeTransfer = get(sequenceType);
        
        if(sequenceTypeTransfer == null) {
            var sequenceTypeDetail = sequenceType.getLastDetail();
            var sequenceTypeName = sequenceTypeDetail.getSequenceTypeName();
            var prefix = sequenceTypeDetail.getPrefix();
            var suffix = sequenceTypeDetail.getSuffix();
            var sequenceEncoderTypeTransferCache = sequenceControl.getSequenceTransferCaches(userVisit).getSequenceEncoderTypeTransferCache();
            var sequenceEncoderType = sequenceEncoderTypeTransferCache.getSequenceEncoderTypeTransfer(sequenceTypeDetail.getSequenceEncoderType());
            var sequenceChecksumTypeTransferCache = sequenceControl.getSequenceTransferCaches(userVisit).getSequenceChecksumTypeTransferCache();
            var sequenceChecksumType = sequenceChecksumTypeTransferCache.getSequenceChecksumTypeTransfer(sequenceTypeDetail.getSequenceChecksumType());
            var chunkSize = sequenceTypeDetail.getChunkSize();
            var isDefault = sequenceTypeDetail.getIsDefault();
            var sortOrder = sequenceTypeDetail.getSortOrder();
            var description = sequenceControl.getBestSequenceTypeDescription(sequenceType, getLanguage());
            
            sequenceTypeTransfer = new SequenceTypeTransfer(sequenceTypeName, prefix, suffix, sequenceEncoderType,
                    sequenceChecksumType, chunkSize, isDefault, sortOrder, description);
            put(sequenceType, sequenceTypeTransfer);
        }
        return sequenceTypeTransfer;
    }
    
}
