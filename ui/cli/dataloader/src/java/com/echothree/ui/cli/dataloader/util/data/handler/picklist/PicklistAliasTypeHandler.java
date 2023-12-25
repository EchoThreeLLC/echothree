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

package com.echothree.ui.cli.dataloader.util.data.handler.picklist;

import com.echothree.control.user.picklist.common.PicklistUtil;
import com.echothree.control.user.picklist.common.PicklistService;
import com.echothree.control.user.picklist.common.form.CreatePicklistAliasTypeDescriptionForm;
import com.echothree.control.user.picklist.common.form.PicklistFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PicklistAliasTypeHandler
        extends BaseHandler {
    PicklistService picklistService;
    String picklistTypeName;
    String picklistAliasTypeName;
    
    /** Creates a new instance of PicklistAliasTypeHandler */
    public PicklistAliasTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String picklistTypeName, String picklistAliasTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            picklistService = PicklistUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.picklistTypeName = picklistTypeName;
        this.picklistAliasTypeName = picklistAliasTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("picklistAliasTypeDescription")) {
            String attrLanguageIsoName = null;
            String attrDescription = null;
            
            int attrCount = attrs.getLength();
            for(int i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    attrLanguageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    attrDescription = attrs.getValue(i);
            }
            
            try {
                CreatePicklistAliasTypeDescriptionForm createPicklistAliasTypeDescriptionForm = PicklistFormFactory.getCreatePicklistAliasTypeDescriptionForm();
                
                createPicklistAliasTypeDescriptionForm.setPicklistTypeName(picklistTypeName);
                createPicklistAliasTypeDescriptionForm.setPicklistAliasTypeName(picklistAliasTypeName);
                createPicklistAliasTypeDescriptionForm.setLanguageIsoName(attrLanguageIsoName);
                createPicklistAliasTypeDescriptionForm.setDescription(attrDescription);
                
                checkCommandResult(picklistService.createPicklistAliasTypeDescription(initialDataParser.getUserVisit(), createPicklistAliasTypeDescriptionForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("picklistAliasType")) {
            initialDataParser.popHandler();
        }
    }
    
}
