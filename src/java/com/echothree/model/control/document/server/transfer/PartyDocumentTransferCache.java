// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.document.server.transfer;

import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.common.transfer.PartyDocumentTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.document.server.entity.PartyDocument;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class PartyDocumentTransferCache
        extends BaseDocumentTransferCache<PartyDocument, PartyDocumentTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of PartyDocumentTransferCache */
    public PartyDocumentTransferCache(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit, documentControl);
    }
    
    public PartyDocumentTransfer getPartyDocumentTransfer(PartyDocument partyDocument) {
        PartyDocumentTransfer partyDocumentTransfer = get(partyDocument);
        
        if(partyDocumentTransfer == null) {
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, partyDocument.getParty());
            DocumentTransfer documentTransfer = documentControl.getDocumentTransfer(userVisit, partyDocument.getDocument());
            Boolean isDefault = partyDocument.getIsDefault();
            Integer sortOrder = partyDocument.getSortOrder();
            
            partyDocumentTransfer = new PartyDocumentTransfer(partyTransfer, documentTransfer, isDefault, sortOrder);
            put(partyDocument, partyDocumentTransfer);
        }
        
        return partyDocumentTransfer;
    }
    
}
