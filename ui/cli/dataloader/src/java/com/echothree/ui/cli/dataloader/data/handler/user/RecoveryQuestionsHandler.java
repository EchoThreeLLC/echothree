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

package com.echothree.ui.cli.dataloader.data.handler.user;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.UserService;
import com.echothree.control.user.user.common.form.CreateRecoveryQuestionForm;
import com.echothree.control.user.user.common.form.UserFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RecoveryQuestionsHandler
        extends BaseHandler {
    UserService userService;
    
    /** Creates a new instance of RecoveryQuestionsHandler */
    public RecoveryQuestionsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
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
        if(localName.equals("recoveryQuestion")) {
            String recoveryQuestionName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("recoveryQuestionName"))
                    recoveryQuestionName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateRecoveryQuestionForm createRecoveryQuestionForm = UserFormFactory.getCreateRecoveryQuestionForm();
                
                createRecoveryQuestionForm.setRecoveryQuestionName(recoveryQuestionName);
                createRecoveryQuestionForm.setIsDefault(isDefault);
                createRecoveryQuestionForm.setSortOrder(sortOrder);
                
                userService.createRecoveryQuestion(initialDataParser.getUserVisit(), createRecoveryQuestionForm);
                
                initialDataParser.pushHandler(new RecoveryQuestionHandler(initialDataParser, this, recoveryQuestionName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("recoveryQuestions")) {
            initialDataParser.popHandler();
        }
    }
    
}
