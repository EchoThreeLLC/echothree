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

package com.echothree.ui.cli.dataloader.data.handler.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.form.CreateMimeTypeForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MimeTypesHandler
        extends BaseHandler {

    CoreService coreService;
    
    /** Creates a new instance of MimeTypesHandler */
    public MimeTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            coreService = CoreUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("mimeType")) {
            String mimeTypeName = null;
            String entityAttributeTypeName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("mimeTypeName"))
                    mimeTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("entityAttributeTypeName"))
                    entityAttributeTypeName = attrs.getValue(i);
            }
            
            try {
                CreateMimeTypeForm commandForm = CoreFormFactory.getCreateMimeTypeForm();
                
                commandForm.set(getAttrsMap(attrs));
                
                coreService.createMimeType(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new MimeTypeHandler(initialDataParser, this, commandForm.getMimeTypeName()));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("mimeTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
