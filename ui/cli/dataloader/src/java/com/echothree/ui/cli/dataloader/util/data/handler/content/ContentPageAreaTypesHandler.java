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

package com.echothree.ui.cli.dataloader.util.data.handler.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.ContentService;
import com.echothree.control.user.content.common.form.ContentFormFactory;
import com.echothree.control.user.content.common.form.CreateContentPageAreaTypeForm;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContentPageAreaTypesHandler
        extends BaseHandler {
    ContentService contentService;
    
    /** Creates a new instance of ContentPageAreaTypesHandler */
    public ContentPageAreaTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            contentService = ContentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contentPageAreaType")) {
            String contentPageAreaTypeName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contentPageAreaTypeName"))
                    contentPageAreaTypeName = attrs.getValue(i);
            }
            
            try {
                CreateContentPageAreaTypeForm createContentPageAreaTypeForm = ContentFormFactory.getCreateContentPageAreaTypeForm();
                
                createContentPageAreaTypeForm.setContentPageAreaTypeName(contentPageAreaTypeName);
                
                contentService.createContentPageAreaType(initialDataParser.getUserVisit(), createContentPageAreaTypeForm);
                
                initialDataParser.pushHandler(new ContentPageAreaTypeHandler(initialDataParser, this, contentPageAreaTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentPageAreaTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
