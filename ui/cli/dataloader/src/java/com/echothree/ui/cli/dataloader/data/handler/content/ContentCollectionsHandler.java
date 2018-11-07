// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.ContentService;
import com.echothree.control.user.content.common.form.ContentFormFactory;
import com.echothree.control.user.content.common.form.CreateContentCollectionForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContentCollectionsHandler
        extends BaseHandler {
    ContentService contentService;
    
    /** Creates a new instance of ContentCollectionsHandler */
    public ContentCollectionsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
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
        if(localName.equals("contentCollection")) {
            String contentCollectionName = null;
            String doUpdate = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contentCollectionName"))
                    contentCollectionName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("doUpdate"))
                    doUpdate = attrs.getValue(i);
            }
            
            try {
                CreateContentCollectionForm createContentCollectionForm = ContentFormFactory.getCreateContentCollectionForm();
                
                createContentCollectionForm.setContentCollectionName(contentCollectionName);
                
                contentService.createContentCollection(initialDataParser.getUserVisit(), createContentCollectionForm);
                
                initialDataParser.pushHandler(new ContentCollectionHandler(initialDataParser, this, contentCollectionName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentCollections")) {
            initialDataParser.popHandler();
        }
    }
    
}
