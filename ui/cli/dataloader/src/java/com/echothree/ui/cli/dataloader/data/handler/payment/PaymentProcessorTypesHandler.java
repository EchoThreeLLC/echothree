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

package com.echothree.ui.cli.dataloader.data.handler.payment;

import com.echothree.control.user.payment.common.PaymentService;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.form.PaymentFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PaymentProcessorTypesHandler
        extends BaseHandler {

    PaymentService paymentService;
    
    /** Creates a new instance of PaymentProcessorTypesHandler */
    public PaymentProcessorTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws NamingException {
        super(initialDataParser, parentHandler);
        
        paymentService = PaymentUtil.getHome();
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("paymentProcessorType")) {
            var commandForm = PaymentFormFactory.getCreatePaymentProcessorTypeForm();

            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(paymentService.createPaymentProcessorType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new PaymentProcessorTypeHandler(initialDataParser, this, commandForm.getPaymentProcessorTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("paymentProcessorTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
