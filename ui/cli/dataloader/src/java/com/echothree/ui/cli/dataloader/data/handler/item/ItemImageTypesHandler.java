// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.form.CreateItemImageTypeForm;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ItemImageTypesHandler
        extends BaseHandler {
    
    ItemService itemService;
    
    /** Creates a new instance of ItemImageTypesHandler */
    public ItemImageTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            itemService = ItemUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("itemImageType")) {
            CreateItemImageTypeForm commandForm = ItemFormFactory.getCreateItemImageTypeForm();

            commandForm.set(getAttrsMap(attrs));

            itemService.createItemImageType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new ItemImageTypeHandler(initialDataParser, this, commandForm.getItemImageTypeName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("itemImageTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
