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

package com.echothree.ui.cli.dataloader.util.data.handler.payment;

import com.echothree.control.user.payment.common.PaymentService;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.form.PaymentFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PaymentProcessorActionTypeHandler
        extends BaseHandler {

    PaymentService paymentService;

    String paymentProcessorActionTypeName;
    
    /** Creates a new instance of PaymentProcessorActionTypeHandler */
    public PaymentProcessorActionTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String paymentProcessorActionTypeName)
            throws NamingException {
        super(initialDataParser, parentHandler);
        
        paymentService = PaymentUtil.getHome();

        this.paymentProcessorActionTypeName = paymentProcessorActionTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("paymentProcessorActionTypeDescription")) {
            var commandForm = PaymentFormFactory.getCreatePaymentProcessorActionTypeDescriptionForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(paymentService.createPaymentProcessorActionTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("paymentProcessorActionType")) {
            initialDataParser.popHandler();
        }
    }
    
}
