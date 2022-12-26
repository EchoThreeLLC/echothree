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

package com.echothree.ui.cli.dataloader.util.data.handler.party;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.form.CreateBirthdayFormatDescriptionForm;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class BirthdayFormatHandler
        extends BaseHandler {
    PartyService partyService;
    String birthdayFormatName;
    
    /** Creates a new instance of BirthdayFormatHandler */
    public BirthdayFormatHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String birthdayFormatName) {
        super(initialDataParser, parentHandler);
        
        try {
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.birthdayFormatName = birthdayFormatName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("birthdayFormatDescription")) {
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
                CreateBirthdayFormatDescriptionForm commandForm = PartyFormFactory.getCreateBirthdayFormatDescriptionForm();
                
                commandForm.setBirthdayFormatName(birthdayFormatName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                partyService.createBirthdayFormatDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("birthdayFormat")) {
            initialDataParser.popHandler();
        }
    }
    
}
