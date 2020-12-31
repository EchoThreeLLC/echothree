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

package com.echothree.ui.cli.dataloader.data.handler.party;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.form.CreatePartyAliasTypeDescriptionForm;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PartyAliasTypeHandler
        extends BaseHandler {

    PartyService partyService;
    String partyTypeName;
    String partyAliasTypeName;
    
    /** Creates a new instance of PartyAliasTypeHandler */
    public PartyAliasTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyTypeName, String partyAliasTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.partyTypeName = partyTypeName;
        this.partyAliasTypeName = partyAliasTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partyAliasTypeDescription")) {
            CreatePartyAliasTypeDescriptionForm commandForm = PartyFormFactory.getCreatePartyAliasTypeDescriptionForm();
            
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setPartyAliasTypeName(partyAliasTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            partyService.createPartyAliasTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("partyAliasType")) {
            initialDataParser.popHandler();
        }
    }
    
}
