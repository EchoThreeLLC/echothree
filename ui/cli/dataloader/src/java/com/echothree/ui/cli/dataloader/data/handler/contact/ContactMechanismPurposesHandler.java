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

package com.echothree.ui.cli.dataloader.data.handler.contact;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.ContactService;
import com.echothree.control.user.contact.common.form.ContactFormFactory;
import com.echothree.control.user.contact.common.form.CreateContactMechanismPurposeForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContactMechanismPurposesHandler
        extends BaseHandler {
    ContactService contactService;
    
    /** Creates a new instance of ContactMechanismPurposesHandler */
    public ContactMechanismPurposesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            contactService = ContactUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contactMechanismPurpose")) {
            String contactMechanismPurposeName = null;
            String contactMechanismTypeName = null;
            String eventSubscriber = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contactMechanismPurposeName"))
                    contactMechanismPurposeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("contactMechanismTypeName"))
                    contactMechanismTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("eventSubscriber"))
                    eventSubscriber = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateContactMechanismPurposeForm form = ContactFormFactory.getCreateContactMechanismPurposeForm();
                
                form.setContactMechanismPurposeName(contactMechanismPurposeName);
                form.setContactMechanismTypeName(contactMechanismTypeName);
                form.setEventSubscriber(eventSubscriber);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);
                
                contactService.createContactMechanismPurpose(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
            
            initialDataParser.pushHandler(new ContactMechanismPurposeHandler(initialDataParser, this, contactMechanismPurposeName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contactMechanismPurposes")) {
            initialDataParser.popHandler();
        }
    }
    
}
