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

package com.echothree.model.control.document.server.transfer;

import com.echothree.model.control.document.common.transfer.DocumentTypeDescriptionTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.document.server.entity.DocumentTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class DocumentTypeDescriptionTransferCache
        extends BaseDocumentDescriptionTransferCache<DocumentTypeDescription, DocumentTypeDescriptionTransfer> {
    
    /** Creates a new instance of DocumentTypeDescriptionTransferCache */
    public DocumentTypeDescriptionTransferCache(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit, documentControl);
    }
    
    public DocumentTypeDescriptionTransfer getDocumentTypeDescriptionTransfer(DocumentTypeDescription documentTypeDescription) {
        DocumentTypeDescriptionTransfer documentTypeDescriptionTransfer = get(documentTypeDescription);
        
        if(documentTypeDescriptionTransfer == null) {
            DocumentTypeTransfer documentTypeTransfer = documentControl.getDocumentTypeTransfer(userVisit, documentTypeDescription.getDocumentType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, documentTypeDescription.getLanguage());
            
            documentTypeDescriptionTransfer = new DocumentTypeDescriptionTransfer(languageTransfer, documentTypeTransfer, documentTypeDescription.getDescription());
            put(documentTypeDescription, documentTypeDescriptionTransfer);
        }
        
        return documentTypeDescriptionTransfer;
    }
    
}
