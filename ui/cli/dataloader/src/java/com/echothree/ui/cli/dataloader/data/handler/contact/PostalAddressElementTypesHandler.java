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
import com.echothree.control.user.contact.remote.ContactService;
import com.echothree.control.user.contact.remote.form.ContactFormFactory;
import com.echothree.control.user.contact.remote.form.CreatePostalAddressElementTypeForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PostalAddressElementTypesHandler
        extends BaseHandler {
    ContactService contactService;
    
    /** Creates a new instance of PostalAddressElementTypesHandler */
    public PostalAddressElementTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
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
        if(localName.equals("postalAddressElementType")) {
            String postalAddressElementTypeName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("postalAddressElementTypeName"))
                    postalAddressElementTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreatePostalAddressElementTypeForm form = ContactFormFactory.getCreatePostalAddressElementTypeForm();
                
                form.setPostalAddressElementTypeName(postalAddressElementTypeName);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);
                
                contactService.createPostalAddressElementType(initialDataParser.getUserVisit(), form);
                
                initialDataParser.pushHandler(new PostalAddressElementTypeHandler(initialDataParser, this, postalAddressElementTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("postalAddressElementTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
