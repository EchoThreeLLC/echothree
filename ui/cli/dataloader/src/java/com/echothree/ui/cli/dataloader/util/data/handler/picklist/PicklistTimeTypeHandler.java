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
import com.echothree.control.user.picklist.common.form.CreatePicklistTimeTypeDescriptionForm;
import com.echothree.control.user.picklist.common.form.PicklistFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PicklistTimeTypeHandler
        extends BaseHandler {
    
    PicklistService picklistService;
    String picklistTypeName;
    String picklistTimeTypeName;
    
    /** Creates a new instance of PicklistTimeTypeHandler */
    public PicklistTimeTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String picklistTypeName, String picklistTimeTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            picklistService = PicklistUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }

        this.picklistTypeName = picklistTypeName;
        this.picklistTimeTypeName = picklistTimeTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("picklistTimeTypeDescription")) {
            CreatePicklistTimeTypeDescriptionForm commandForm = PicklistFormFactory.getCreatePicklistTimeTypeDescriptionForm();
            
            commandForm.setPicklistTypeName(picklistTypeName);
            commandForm.setPicklistTimeTypeName(picklistTimeTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(picklistService.createPicklistTimeTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("picklistTimeType")) {
            initialDataParser.popHandler();
        }
    }
    
}
