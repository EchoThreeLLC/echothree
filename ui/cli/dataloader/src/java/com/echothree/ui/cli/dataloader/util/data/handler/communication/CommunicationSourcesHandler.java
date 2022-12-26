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

package com.echothree.ui.cli.dataloader.util.data.handler.communication;

import com.echothree.control.user.communication.common.CommunicationUtil;
import com.echothree.control.user.communication.common.CommunicationService;
import com.echothree.control.user.communication.common.form.CommunicationFormFactory;
import com.echothree.control.user.communication.common.form.CreateCommunicationSourceForm;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import java.util.Map;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CommunicationSourcesHandler
        extends BaseHandler {
    CommunicationService communicationService;
    
    /** Creates a new instance of CommunicationSourcesHandler */
    public CommunicationSourcesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            communicationService = CommunicationUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("communicationSource")) {
            CreateCommunicationSourceForm commandForm = CommunicationFormFactory.getCreateCommunicationSourceForm();
            Map<String, Object> attrsMap = getAttrsMap(attrs);
            
            commandForm.set(attrsMap);
            
            communicationService.createCommunicationSource(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new CommunicationSourceHandler(initialDataParser, this, commandForm.getCommunicationSourceName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("communicationSources")) {
            initialDataParser.popHandler();
        }
    }
    
}
