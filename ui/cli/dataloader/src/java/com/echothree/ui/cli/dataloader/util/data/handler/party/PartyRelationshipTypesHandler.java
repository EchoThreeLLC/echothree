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

package com.echothree.ui.cli.dataloader.util.data.handler.party;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.form.CreatePartyRelationshipTypeForm;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PartyRelationshipTypesHandler
        extends BaseHandler {
    PartyService partyService;
    
    /** Creates a new instance of PartyRelationshipTypesHandler */
    public PartyRelationshipTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("partyRelationshipType")) {
            String partyRelationshipTypeName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("partyRelationshipTypeName"))
                    partyRelationshipTypeName = attrs.getValue(i);
            }
            
            try {
                CreatePartyRelationshipTypeForm createPartyRelationshipTypeForm = PartyFormFactory.getCreatePartyRelationshipTypeForm();
                
                createPartyRelationshipTypeForm.setPartyRelationshipTypeName(partyRelationshipTypeName);
                
                partyService.createPartyRelationshipType(initialDataParser.getUserVisit(), createPartyRelationshipTypeForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
            
            initialDataParser.pushHandler(new PartyRelationshipTypeHandler(initialDataParser, this, partyRelationshipTypeName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("partyRelationshipTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
