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

package com.echothree.ui.cli.dataloader.data.handler.party;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.remote.PartyService;
import com.echothree.control.user.party.remote.form.CreateLanguageDescriptionForm;
import com.echothree.control.user.party.remote.form.PartyFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LanguageHandler
        extends BaseHandler {
    PartyService partyService;
    String languageIsoName;
    
    /** Creates a new instance of LanguageHandler */
    public LanguageHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String languageIsoName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.languageIsoName = languageIsoName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("languageDescription")) {
            CreateLanguageDescriptionForm commandForm = PartyFormFactory.getCreateLanguageDescriptionForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.set(getAttrsMap(attrs));

            partyService.createLanguageDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("language")) {
            initialDataParser.popHandler();
        }
    }
    
}
