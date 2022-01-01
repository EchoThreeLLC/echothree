// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceDescriptionTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.SequenceDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SequenceDescriptionTransferCache
        extends BaseSequenceDescriptionTransferCache<SequenceDescription, SequenceDescriptionTransfer> {
    
    /** Creates a new instance of SequenceDescriptionTransferCache */
    public SequenceDescriptionTransferCache(UserVisit userVisit, SequenceControl sequenceControl) {
        super(userVisit, sequenceControl);
    }
    
    public SequenceDescriptionTransfer getSequenceDescriptionTransfer(SequenceDescription sequenceDescription) {
        SequenceDescriptionTransfer sequenceDescriptionTransfer = get(sequenceDescription);
        
        if(sequenceDescriptionTransfer == null) {
            SequenceTransferCache sequenceTransferCache = sequenceControl.getSequenceTransferCaches(userVisit).getSequenceTransferCache();
            SequenceTransfer sequenceTransfer = sequenceTransferCache.getSequenceTransfer(sequenceDescription.getSequence());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, sequenceDescription.getLanguage());
            
            sequenceDescriptionTransfer = new SequenceDescriptionTransfer(languageTransfer, sequenceTransfer, sequenceDescription.getDescription());
            put(sequenceDescription, sequenceDescriptionTransfer);
        }
        
        return sequenceDescriptionTransfer;
    }
    
}
