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
import com.echothree.control.user.selector.common.form.CreateSelectorNodeDescriptionForm;
import com.echothree.control.user.selector.common.form.SelectorFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SelectorNodeHandler
        extends BaseHandler {
    SelectorService selectorService;
    String selectorKindName;
    String selectorTypeName;
    String selectorName;
    String selectorNodeName;
    
    /** Creates a new instance of SelectorNodeHandler */
    public SelectorNodeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String selectorKindName,
            String selectorTypeName, String selectorName, String selectorNodeName) {
        super(initialDataParser, parentHandler);
        
        try {
            selectorService = SelectorUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.selectorKindName = selectorKindName;
        this.selectorTypeName = selectorTypeName;
        this.selectorName = selectorName;
        this.selectorNodeName = selectorNodeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("selectorNodeDescription")) {
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
                CreateSelectorNodeDescriptionForm form = SelectorFormFactory.getCreateSelectorNodeDescriptionForm();
                
                form.setSelectorKindName(selectorKindName);
                form.setSelectorTypeName(selectorTypeName);
                form.setSelectorName(selectorName);
                form.setSelectorNodeName(selectorNodeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                selectorService.createSelectorNodeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("selectorNode")) {
            initialDataParser.popHandler();
        }
    }
    
}
