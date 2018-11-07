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

package com.echothree.ui.cli.dataloader.data.handler.contact;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.ContactService;
import com.echothree.control.user.contact.common.form.ContactFormFactory;
import com.echothree.control.user.contact.common.form.CreateContactMechanismAliasTypeDescriptionForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContactMechanismAliasTypeHandler
        extends BaseHandler {
    ContactService contactService;
    String contactMechanismAliasTypeName;
    
    /** Creates a new instance of ContactMechanismAliasTypeHandler */
    public ContactMechanismAliasTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contactMechanismAliasTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            contactService = ContactUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.contactMechanismAliasTypeName = contactMechanismAliasTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contactMechanismAliasTypeDescription")) {
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
                CreateContactMechanismAliasTypeDescriptionForm createContactMechanismAliasTypeDescriptionForm = ContactFormFactory.getCreateContactMechanismAliasTypeDescriptionForm();
                
                createContactMechanismAliasTypeDescriptionForm.setContactMechanismAliasTypeName(contactMechanismAliasTypeName);
                createContactMechanismAliasTypeDescriptionForm.setLanguageIsoName(languageIsoName);
                createContactMechanismAliasTypeDescriptionForm.setDescription(description);
                
                contactService.createContactMechanismAliasTypeDescription(initialDataParser.getUserVisit(), createContactMechanismAliasTypeDescriptionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contactMechanismAliasType")) {
            initialDataParser.popHandler();
        }
    }
    
}
