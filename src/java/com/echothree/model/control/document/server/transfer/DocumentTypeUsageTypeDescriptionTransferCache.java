// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.document.common.transfer.DocumentTypeUsageTypeDescriptionTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeUsageTypeTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class DocumentTypeUsageTypeDescriptionTransferCache
        extends BaseDocumentDescriptionTransferCache<DocumentTypeUsageTypeDescription, DocumentTypeUsageTypeDescriptionTransfer> {
    
    /** Creates a new instance of DocumentTypeUsageTypeDescriptionTransferCache */
    public DocumentTypeUsageTypeDescriptionTransferCache(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit, documentControl);
    }
    
    public DocumentTypeUsageTypeDescriptionTransfer getDocumentTypeUsageTypeDescriptionTransfer(DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription) {
        DocumentTypeUsageTypeDescriptionTransfer documentTypeUsageTypeDescriptionTransfer = get(documentTypeUsageTypeDescription);
        
        if(documentTypeUsageTypeDescriptionTransfer == null) {
            DocumentTypeUsageTypeTransfer documentTypeUsageTypeTransfer = documentControl.getDocumentTypeUsageTypeTransfer(userVisit, documentTypeUsageTypeDescription.getDocumentTypeUsageType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, documentTypeUsageTypeDescription.getLanguage());
            
            documentTypeUsageTypeDescriptionTransfer = new DocumentTypeUsageTypeDescriptionTransfer(languageTransfer, documentTypeUsageTypeTransfer, documentTypeUsageTypeDescription.getDescription());
            put(documentTypeUsageTypeDescription, documentTypeUsageTypeDescriptionTransfer);
        }
        
        return documentTypeUsageTypeDescriptionTransfer;
    }
    
}
