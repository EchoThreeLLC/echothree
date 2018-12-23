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

package com.echothree.ui.cli.dataloader.data.handler.communication;

import com.echothree.control.user.communication.common.CommunicationUtil;
import com.echothree.control.user.communication.common.CommunicationService;
import com.echothree.control.user.communication.common.form.CommunicationFormFactory;
import com.echothree.control.user.communication.common.form.CreateCommunicationEventPurposeDescriptionForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CommunicationEventPurposeHandler
        extends BaseHandler {
    CommunicationService communicationService;
    String communicationEventPurposeName;
    
    /** Creates a new instance of CommunicationEventPurposeHandler */
    public CommunicationEventPurposeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String communicationEventPurposeName) {
        super(initialDataParser, parentHandler);
        
        try {
            communicationService = CommunicationUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.communicationEventPurposeName = communicationEventPurposeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("communicationEventPurposeDescription")) {
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
                CreateCommunicationEventPurposeDescriptionForm form = CommunicationFormFactory.getCreateCommunicationEventPurposeDescriptionForm();
                
                form.setCommunicationEventPurposeName(communicationEventPurposeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                communicationService.createCommunicationEventPurposeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("communicationEventPurpose")) {
            initialDataParser.popHandler();
        }
    }
    
}
