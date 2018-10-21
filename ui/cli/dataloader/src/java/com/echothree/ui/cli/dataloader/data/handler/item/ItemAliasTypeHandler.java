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

package com.echothree.ui.cli.dataloader.data.handler.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.remote.ItemService;
import com.echothree.control.user.item.remote.form.CreateItemAliasTypeDescriptionForm;
import com.echothree.control.user.item.remote.form.ItemFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ItemAliasTypeHandler
        extends BaseHandler {
    ItemService itemService;
    String itemAliasTypeName;
    
    /** Creates a new instance of ItemAliasTypeHandler */
    public ItemAliasTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String itemAliasTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            itemService = ItemUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.itemAliasTypeName = itemAliasTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("itemAliasTypeDescription")) {
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
                CreateItemAliasTypeDescriptionForm createItemAliasTypeDescriptionForm = ItemFormFactory.getCreateItemAliasTypeDescriptionForm();
                
                createItemAliasTypeDescriptionForm.setItemAliasTypeName(itemAliasTypeName);
                createItemAliasTypeDescriptionForm.setLanguageIsoName(languageIsoName);
                createItemAliasTypeDescriptionForm.setDescription(description);
                
                itemService.createItemAliasTypeDescription(initialDataParser.getUserVisit(), createItemAliasTypeDescriptionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("itemAliasType")) {
            initialDataParser.popHandler();
        }
    }
    
}
