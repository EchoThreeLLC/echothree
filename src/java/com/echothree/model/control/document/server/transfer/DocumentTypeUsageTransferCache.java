// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.document.common.transfer.DocumentTypeTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeUsageTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeUsageTypeTransfer;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.data.document.server.entity.DocumentTypeUsage;
import com.echothree.model.data.user.server.entity.UserVisit;

public class DocumentTypeUsageTransferCache
        extends BaseDocumentTransferCache<DocumentTypeUsage, DocumentTypeUsageTransfer> {
    
    /** Creates a new instance of DocumentTypeUsageTransferCache */
    public DocumentTypeUsageTransferCache(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit, documentControl);
    }
    
    public DocumentTypeUsageTransfer getDocumentTypeUsageTransfer(DocumentTypeUsage documentTypeUsage) {
        DocumentTypeUsageTransfer documentTypeUsageTransfer = get(documentTypeUsage);
        
        if(documentTypeUsageTransfer == null) {
            DocumentTypeUsageTypeTransfer documentTypeUsageTypeTransfer = documentControl.getDocumentTypeUsageTypeTransfer(userVisit, documentTypeUsage.getDocumentTypeUsageType());
            DocumentTypeTransfer documentTypeTransfer = documentControl.getDocumentTypeTransfer(userVisit, documentTypeUsage.getDocumentType());
            Boolean isDefault = documentTypeUsage.getIsDefault();
            Integer sortOrder = documentTypeUsage.getSortOrder();
            Integer maximumInstances = documentTypeUsage.getMaximumInstances();
            
            documentTypeUsageTransfer = new DocumentTypeUsageTransfer(documentTypeUsageTypeTransfer, documentTypeTransfer, isDefault, sortOrder,
                    maximumInstances);
            put(documentTypeUsage, documentTypeUsageTransfer);
        }
        
        return documentTypeUsageTransfer;
    }
    
}
