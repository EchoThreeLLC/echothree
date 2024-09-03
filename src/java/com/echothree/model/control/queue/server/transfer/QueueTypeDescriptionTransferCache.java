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

package com.echothree.model.control.queue.server.transfer;

import com.echothree.model.control.queue.common.transfer.QueueTypeDescriptionTransfer;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.data.queue.server.entity.QueueTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class QueueTypeDescriptionTransferCache
        extends BaseQueueDescriptionTransferCache<QueueTypeDescription, QueueTypeDescriptionTransfer> {
    
    /** Creates a new instance of QueueTypeDescriptionTransferCache */
    public QueueTypeDescriptionTransferCache(UserVisit userVisit, QueueControl queueControl) {
        super(userVisit, queueControl);
    }
    
    public QueueTypeDescriptionTransfer getQueueTypeDescriptionTransfer(QueueTypeDescription queueTypeDescription) {
        var queueTypeDescriptionTransfer = get(queueTypeDescription);
        
        if(queueTypeDescriptionTransfer == null) {
            var queueTypeTransfer = queueControl.getQueueTypeTransfer(userVisit, queueTypeDescription.getQueueType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, queueTypeDescription.getLanguage());
            
            queueTypeDescriptionTransfer = new QueueTypeDescriptionTransfer(languageTransfer, queueTypeTransfer, queueTypeDescription.getDescription());
            put(queueTypeDescription, queueTypeDescriptionTransfer);
        }
        return queueTypeDescriptionTransfer;
    }
    
}
