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

package com.echothree.ui.cli.dataloader.data.handler.order;

import com.echothree.control.user.order.common.OrderUtil;
import com.echothree.control.user.order.common.OrderService;
import com.echothree.control.user.order.common.form.CreateOrderTimeTypeDescriptionForm;
import com.echothree.control.user.order.common.form.OrderFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class OrderTimeTypeHandler
        extends BaseHandler {
    OrderService orderService;
    String orderTypeName;
    String orderTimeTypeName;
    
    /** Creates a new instance of OrderTimeTypeHandler */
    public OrderTimeTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String orderTypeName, String orderTimeTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            orderService = OrderUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }

        this.orderTypeName = orderTypeName;
        this.orderTimeTypeName = orderTimeTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("orderTimeTypeDescription")) {
            CreateOrderTimeTypeDescriptionForm commandForm = OrderFormFactory.getCreateOrderTimeTypeDescriptionForm();
            
            commandForm.setOrderTypeName(orderTypeName);
            commandForm.setOrderTimeTypeName(orderTimeTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(orderService.createOrderTimeTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("orderTimeType")) {
            initialDataParser.popHandler();
        }
    }
    
}
