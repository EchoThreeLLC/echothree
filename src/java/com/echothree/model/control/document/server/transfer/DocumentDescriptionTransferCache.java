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

package com.echothree.model.control.document.server.transfer;

import com.echothree.model.control.document.common.transfer.DocumentDescriptionTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.document.server.entity.DocumentDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class DocumentDescriptionTransferCache
        extends BaseDocumentDescriptionTransferCache<DocumentDescription, DocumentDescriptionTransfer> {
    
    /** Creates a new instance of DocumentDescriptionTransferCache */
    public DocumentDescriptionTransferCache(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit, documentControl);
    }
    
    public DocumentDescriptionTransfer getDocumentDescriptionTransfer(DocumentDescription documentDescription) {
        DocumentDescriptionTransfer documentDescriptionTransfer = get(documentDescription);
        
        if(documentDescriptionTransfer == null) {
            DocumentTransferCache documentTransferCache = documentControl.getDocumentTransferCaches(userVisit).getDocumentTransferCache();
            DocumentTransfer documentTransfer = documentTransferCache.getDocumentTransfer(documentDescription.getDocument());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, documentDescription.getLanguage());
            
            documentDescriptionTransfer = new DocumentDescriptionTransfer(languageTransfer, documentTransfer, documentDescription.getDescription());
            put(documentDescription, documentDescriptionTransfer);
        }
        
        return documentDescriptionTransfer;
    }
    
}
