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

package com.echothree.ui.cli.dataloader.data.handler.order;

import com.echothree.control.user.order.common.OrderUtil;
import com.echothree.control.user.order.common.OrderService;
import com.echothree.control.user.order.common.form.CreateOrderTypeForm;
import com.echothree.control.user.order.common.form.OrderFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class OrderTypesHandler
        extends BaseHandler {

    OrderService orderService;
    
    /** Creates a new instance of OrderTypesHandler */
    public OrderTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            orderService = OrderUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("orderType")) {
            CreateOrderTypeForm commandForm = OrderFormFactory.getCreateOrderTypeForm();

            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(orderService.createOrderType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new OrderTypeHandler(initialDataParser, this, commandForm.getOrderTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("orderTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
