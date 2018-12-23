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

package com.echothree.ui.cli.dataloader.data.handler.document;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.DocumentService;
import com.echothree.control.user.document.common.form.CreateDocumentTypeUsageTypeForm;
import com.echothree.control.user.document.common.form.DocumentFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DocumentTypeUsageTypesHandler
        extends BaseHandler {
    DocumentService documentService;
    
    /** Creates a new instance of DocumentTypeUsageTypesHandler */
    public DocumentTypeUsageTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
    throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            documentService = DocumentUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("documentTypeUsageType")) {
            CreateDocumentTypeUsageTypeForm commandForm = DocumentFormFactory.getCreateDocumentTypeUsageTypeForm();

            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(documentService.createDocumentTypeUsageType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new DocumentTypeUsageTypeHandler(initialDataParser, this, commandForm.getDocumentTypeUsageTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("documentTypeUsageTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
