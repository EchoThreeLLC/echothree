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

package com.echothree.ui.cli.dataloader.util.data.handler.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.ContentService;
import com.echothree.control.user.content.common.form.ContentFormFactory;
import com.echothree.control.user.content.common.form.CreateContentPageLayoutAreaForm;
import com.echothree.control.user.content.common.form.CreateContentPageLayoutDescriptionForm;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContentPageLayoutHandler
        extends BaseHandler {
    ContentService contentService;
    String contentPageLayoutName;
    
    /** Creates a new instance of ContentPageLayoutHandler */
    public ContentPageLayoutHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contentPageLayoutName) {
        super(initialDataParser, parentHandler);
        
        try {
            contentService = ContentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.contentPageLayoutName = contentPageLayoutName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contentPageLayoutDescription")) {
            String languageIsoName = null;
            String description = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                CreateContentPageLayoutDescriptionForm createContentPageLayoutDescriptionForm = ContentFormFactory.getCreateContentPageLayoutDescriptionForm();
                
                createContentPageLayoutDescriptionForm.setContentPageLayoutName(contentPageLayoutName);
                createContentPageLayoutDescriptionForm.setLanguageIsoName(languageIsoName);
                createContentPageLayoutDescriptionForm.setDescription(description);
                
                contentService.createContentPageLayoutDescription(initialDataParser.getUserVisit(), createContentPageLayoutDescriptionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("contentPageLayoutArea")) {
            String contentPageAreaTypeName = null;
            String showDescriptionField = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contentPageAreaTypeName"))
                    contentPageAreaTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("showDescriptionField"))
                    showDescriptionField = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateContentPageLayoutAreaForm createContentPageLayoutAreaForm = ContentFormFactory.getCreateContentPageLayoutAreaForm();
                
                createContentPageLayoutAreaForm.setContentPageLayoutName(contentPageLayoutName);
                createContentPageLayoutAreaForm.setContentPageAreaTypeName(contentPageAreaTypeName);
                createContentPageLayoutAreaForm.setShowDescriptionField(showDescriptionField);
                createContentPageLayoutAreaForm.setSortOrder(sortOrder);
                
                contentService.createContentPageLayoutArea(initialDataParser.getUserVisit(), createContentPageLayoutAreaForm);
                
                initialDataParser.pushHandler(new ContentPageLayoutAreaHandler(initialDataParser, this, contentPageLayoutName, sortOrder));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentPageLayout")) {
            initialDataParser.popHandler();
        }
    }
    
}
