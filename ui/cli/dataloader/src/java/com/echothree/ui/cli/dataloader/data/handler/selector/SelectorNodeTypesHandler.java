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

package com.echothree.ui.cli.dataloader.data.handler.selector;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.SelectorService;
import com.echothree.control.user.selector.common.form.CreateSelectorNodeTypeForm;
import com.echothree.control.user.selector.common.form.SelectorFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SelectorNodeTypesHandler
        extends BaseHandler {
    SelectorService selectorService;
    
    /** Creates a new instance of SelectorNodeTypesHandler */
    public SelectorNodeTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            selectorService = SelectorUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("selectorNodeType")) {
            String selectorNodeTypeName = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("selectorNodeTypeName"))
                    selectorNodeTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateSelectorNodeTypeForm commandForm = SelectorFormFactory.getCreateSelectorNodeTypeForm();
                
                commandForm.setSelectorNodeTypeName(selectorNodeTypeName);
                commandForm.setSortOrder(sortOrder);
                
                selectorService.createSelectorNodeType(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new SelectorNodeTypeHandler(initialDataParser, this, selectorNodeTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("selectorNodeTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
