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

package com.echothree.ui.cli.dataloader.data.handler.subscription;

import com.echothree.control.user.subscription.common.SubscriptionUtil;
import com.echothree.control.user.subscription.common.SubscriptionService;
import com.echothree.control.user.subscription.common.form.CreateSubscriptionKindForm;
import com.echothree.control.user.subscription.common.form.SubscriptionFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SubscriptionKindsHandler
        extends BaseHandler {

    SubscriptionService subscriptionService;
    
    /** Creates a new instance of SubscriptionKindsHandler */
    public SubscriptionKindsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            subscriptionService = SubscriptionUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("subscriptionKind")) {
            CreateSubscriptionKindForm commandForm = SubscriptionFormFactory.getCreateSubscriptionKindForm();
            String subscriptionKindName = null;
            String isDefault = null;
            String sortOrder = null;
            String commandAction = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("subscriptionKindName"))
                    subscriptionKindName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("commandAction"))
                    commandAction = attrs.getValue(i);
            }
            
            commandForm.setSubscriptionKindName(subscriptionKindName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            
            if(commandAction == null || commandAction.equals("create")) {
                subscriptionService.createSubscriptionKind(initialDataParser.getUserVisit(), commandForm);
            }
            
            initialDataParser.pushHandler(new SubscriptionKindHandler(initialDataParser, this, subscriptionKindName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("subscriptionKinds")) {
            initialDataParser.popHandler();
        }
    }
    
}
