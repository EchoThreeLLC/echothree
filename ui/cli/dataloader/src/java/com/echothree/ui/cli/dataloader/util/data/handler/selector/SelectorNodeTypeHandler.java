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

package com.echothree.ui.cli.dataloader.util.data.handler.selector;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.SelectorService;
import com.echothree.control.user.selector.common.form.CreateSelectorNodeTypeDescriptionForm;
import com.echothree.control.user.selector.common.form.SelectorFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SelectorNodeTypeHandler
        extends BaseHandler {
    SelectorService selectorService;
    String selectorNodeTypeName;
    
    /** Creates a new instance of SelectorNodeTypeHandler */
    public SelectorNodeTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String selectorNodeTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            selectorService = SelectorUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.selectorNodeTypeName = selectorNodeTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("selectorNodeTypeDescription")) {
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
                CreateSelectorNodeTypeDescriptionForm createSelectorNodeTypeDescriptionForm = SelectorFormFactory.getCreateSelectorNodeTypeDescriptionForm();
                
                createSelectorNodeTypeDescriptionForm.setSelectorNodeTypeName(selectorNodeTypeName);
                createSelectorNodeTypeDescriptionForm.setLanguageIsoName(languageIsoName);
                createSelectorNodeTypeDescriptionForm.setDescription(description);
                
                selectorService.createSelectorNodeTypeDescription(initialDataParser.getUserVisit(), createSelectorNodeTypeDescriptionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("selectorNodeType")) {
            initialDataParser.popHandler();
        }
    }
    
}
