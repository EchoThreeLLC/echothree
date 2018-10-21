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

package com.echothree.ui.cli.dataloader.data.handler.user;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.remote.UserService;
import com.echothree.control.user.user.remote.form.CreateUserLoginPasswordTypeForm;
import com.echothree.control.user.user.remote.form.UserFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class UserLoginPasswordTypesHandler
        extends BaseHandler {
    UserService userService;
    
    /** Creates a new instance of UserLoginPasswordTypesHandler */
    public UserLoginPasswordTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            userService = UserUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("userLoginPasswordType")) {
            CreateUserLoginPasswordTypeForm commandForm = UserFormFactory.getCreateUserLoginPasswordTypeForm();
            String userLoginPasswordTypeName = null;
            String userLoginPasswordEncoderTypeName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("userLoginPasswordTypeName"))
                    userLoginPasswordTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("userLoginPasswordEncoderTypeName"))
                    userLoginPasswordEncoderTypeName = attrs.getValue(i);
            }
            
            commandForm.setUserLoginPasswordTypeName(userLoginPasswordTypeName);
            commandForm.setUserLoginPasswordEncoderTypeName(userLoginPasswordEncoderTypeName);
            
            userService.createUserLoginPasswordType(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new UserLoginPasswordTypeHandler(initialDataParser, this, userLoginPasswordTypeName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("userLoginPasswordTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
