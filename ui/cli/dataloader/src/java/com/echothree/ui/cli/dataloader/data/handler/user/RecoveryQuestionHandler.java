// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.user.common.UserService;
import com.echothree.control.user.user.common.form.CreateRecoveryQuestionDescriptionForm;
import com.echothree.control.user.user.common.form.UserFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RecoveryQuestionHandler
        extends BaseHandler {
    UserService userService;
    String recoveryQuestionName;
    
    /** Creates a new instance of RecoveryQuestionHandler */
    public RecoveryQuestionHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String recoveryQuestionName) {
        super(initialDataParser, parentHandler);
        
        try {
            userService = UserUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.recoveryQuestionName = recoveryQuestionName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("recoveryQuestionDescription")) {
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
                CreateRecoveryQuestionDescriptionForm createRecoveryQuestionDescriptionForm = UserFormFactory.getCreateRecoveryQuestionDescriptionForm();
                
                createRecoveryQuestionDescriptionForm.setRecoveryQuestionName(recoveryQuestionName);
                createRecoveryQuestionDescriptionForm.setLanguageIsoName(languageIsoName);
                createRecoveryQuestionDescriptionForm.setDescription(description);
                
                userService.createRecoveryQuestionDescription(initialDataParser.getUserVisit(), createRecoveryQuestionDescriptionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("recoveryQuestion")) {
            initialDataParser.popHandler();
        }
    }
    
}
