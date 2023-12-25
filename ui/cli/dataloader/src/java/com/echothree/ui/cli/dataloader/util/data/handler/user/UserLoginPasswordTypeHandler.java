// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler.user;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.UserService;
import com.echothree.control.user.user.common.form.CreateUserLoginPasswordTypeDescriptionForm;
import com.echothree.control.user.user.common.form.UserFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class UserLoginPasswordTypeHandler
        extends BaseHandler {
    UserService userService;
    String userLoginPasswordTypeName;
    
    /** Creates a new instance of UserLoginPasswordTypeHandler */
    public UserLoginPasswordTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String userLoginPasswordTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            userService = UserUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.userLoginPasswordTypeName = userLoginPasswordTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("userLoginPasswordTypeDescription")) {
            CreateUserLoginPasswordTypeDescriptionForm commandForm = UserFormFactory.getCreateUserLoginPasswordTypeDescriptionForm();
            String languageIsoName = null;
            String description = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            commandForm.setUserLoginPasswordTypeName(userLoginPasswordTypeName);
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setDescription(description);
            
            userService.createUserLoginPasswordTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("userLoginPasswordType")) {
            initialDataParser.popHandler();
        }
    }
    
}
