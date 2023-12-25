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

package com.echothree.model.control.document.server.transfer;

import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class DocumentTransferCaches
        extends BaseTransferCaches {
    
    protected DocumentControl documentControl;
    
    protected DocumentTypeTransferCache documentTypeTransferCache;
    protected DocumentTypeDescriptionTransferCache documentTypeDescriptionTransferCache;
    protected DocumentTypeUsageTypeTransferCache documentTypeUsageTypeTransferCache;
    protected DocumentTypeUsageTypeDescriptionTransferCache documentTypeUsageTypeDescriptionTransferCache;
    protected DocumentTypeUsageTransferCache documentTypeUsageTransferCache;
    protected DocumentTransferCache documentTransferCache;
    protected DocumentDescriptionTransferCache documentDescriptionTransferCache;
    protected PartyTypeDocumentTypeUsageTypeTransferCache partyTypeDocumentTypeUsageTypeTransferCache;
    protected PartyDocumentTransferCache partyDocumentTransferCache;
    
    /** Creates a new instance of DocumentTransferCaches */
    public DocumentTransferCaches(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit);
        
        this.documentControl = documentControl;
    }
    
    public DocumentTypeTransferCache getDocumentTypeTransferCache() {
        if(documentTypeTransferCache == null)
            documentTypeTransferCache = new DocumentTypeTransferCache(userVisit, documentControl);

        return documentTypeTransferCache;
    }

    public DocumentTypeDescriptionTransferCache getDocumentTypeDescriptionTransferCache() {
        if(documentTypeDescriptionTransferCache == null)
            documentTypeDescriptionTransferCache = new DocumentTypeDescriptionTransferCache(userVisit, documentControl);

        return documentTypeDescriptionTransferCache;
    }

    public DocumentTypeUsageTypeTransferCache getDocumentTypeUsageTypeTransferCache() {
        if(documentTypeUsageTypeTransferCache == null)
            documentTypeUsageTypeTransferCache = new DocumentTypeUsageTypeTransferCache(userVisit, documentControl);

        return documentTypeUsageTypeTransferCache;
    }

    public DocumentTypeUsageTypeDescriptionTransferCache getDocumentTypeUsageTypeDescriptionTransferCache() {
        if(documentTypeUsageTypeDescriptionTransferCache == null)
            documentTypeUsageTypeDescriptionTransferCache = new DocumentTypeUsageTypeDescriptionTransferCache(userVisit, documentControl);

        return documentTypeUsageTypeDescriptionTransferCache;
    }

    public DocumentTypeUsageTransferCache getDocumentTypeUsageTransferCache() {
        if(documentTypeUsageTransferCache == null)
            documentTypeUsageTransferCache = new DocumentTypeUsageTransferCache(userVisit, documentControl);

        return documentTypeUsageTransferCache;
    }

    public DocumentTransferCache getDocumentTransferCache() {
        if(documentTransferCache == null)
            documentTransferCache = new DocumentTransferCache(userVisit, documentControl);
        
        return documentTransferCache;
    }
    
    public DocumentDescriptionTransferCache getDocumentDescriptionTransferCache() {
        if(documentDescriptionTransferCache == null)
            documentDescriptionTransferCache = new DocumentDescriptionTransferCache(userVisit, documentControl);

        return documentDescriptionTransferCache;
    }

    public PartyTypeDocumentTypeUsageTypeTransferCache getPartyTypeDocumentTypeUsageTypeTransferCache() {
        if(partyTypeDocumentTypeUsageTypeTransferCache == null)
            partyTypeDocumentTypeUsageTypeTransferCache = new PartyTypeDocumentTypeUsageTypeTransferCache(userVisit, documentControl);

        return partyTypeDocumentTypeUsageTypeTransferCache;
    }

    public PartyDocumentTransferCache getPartyDocumentTransferCache() {
        if(partyDocumentTransferCache == null)
            partyDocumentTransferCache = new PartyDocumentTransferCache(userVisit, documentControl);

        return partyDocumentTransferCache;
    }

}
