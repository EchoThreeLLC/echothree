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

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.ContactListService;
import com.echothree.control.user.contactlist.common.form.ContactListFormFactory;
import com.echothree.control.user.contactlist.common.form.CreatePartyTypeContactListForm;
import com.echothree.control.user.contactlist.common.form.CreatePartyTypeContactListGroupForm;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.form.CreatePartyAliasTypeForm;
import com.echothree.control.user.party.common.form.CreatePartyTypeAuditPolicyForm;
import com.echothree.control.user.party.common.form.CreatePartyTypeDescriptionForm;
import com.echothree.control.user.party.common.form.CreatePartyTypeLockoutPolicyForm;
import com.echothree.control.user.party.common.form.CreatePartyTypePasswordStringPolicyForm;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PartyTypeHandler
        extends BaseHandler {

    ContactListService contactListService;
    PartyService partyService;
    String partyTypeName;
    
    /** Creates a new instance of PartyTypeHandler */
    public PartyTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            contactListService = ContactListUtil.getHome();
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.partyTypeName = partyTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partyTypeDescription")) {
            CreatePartyTypeDescriptionForm commandForm = PartyFormFactory.getCreatePartyTypeDescriptionForm();
            
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            partyService.createPartyTypeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("partyTypePasswordStringPolicy")) {
            CreatePartyTypePasswordStringPolicyForm commandForm = PartyFormFactory.getCreatePartyTypePasswordStringPolicyForm();
            
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            partyService.createPartyTypePasswordStringPolicy(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("partyTypeLockoutPolicy")) {
            CreatePartyTypeLockoutPolicyForm commandForm = PartyFormFactory.getCreatePartyTypeLockoutPolicyForm();
            
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            partyService.createPartyTypeLockoutPolicy(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("partyTypeAuditPolicy")) {
            CreatePartyTypeAuditPolicyForm commandForm = PartyFormFactory.getCreatePartyTypeAuditPolicyForm();
            
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            partyService.createPartyTypeAuditPolicy(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("partyTypeContactListGroup")) {
            CreatePartyTypeContactListGroupForm commandForm = ContactListFormFactory.getCreatePartyTypeContactListGroupForm();
            
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            contactListService.createPartyTypeContactListGroup(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("partyTypeContactList")) {
            CreatePartyTypeContactListForm commandForm = ContactListFormFactory.getCreatePartyTypeContactListForm();

            commandForm.setPartyTypeName(partyTypeName);
            commandForm.set(getAttrsMap(attrs));

            contactListService.createPartyTypeContactList(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("partyAliasType")) {
            CreatePartyAliasTypeForm commandForm = PartyFormFactory.getCreatePartyAliasTypeForm();

            commandForm.setPartyTypeName(partyTypeName);
            commandForm.set(getAttrsMap(attrs));

            partyService.createPartyAliasType(initialDataParser.getUserVisit(), commandForm);
            initialDataParser.pushHandler(new PartyAliasTypeHandler(initialDataParser, this, partyTypeName, commandForm.getPartyAliasTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("partyType")) {
            initialDataParser.popHandler();
        }
    }
    
}
