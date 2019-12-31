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

package com.echothree.ui.cli.dataloader.data.handler.payment;

import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.common.PaymentService;
import com.echothree.control.user.payment.common.form.CreatePaymentMethodDescriptionForm;
import com.echothree.control.user.payment.common.form.PaymentFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.data.handler.comment.CommentsHandler;
import com.echothree.ui.cli.dataloader.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.data.handler.tag.EntityTagsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PaymentMethodHandler
        extends BaseHandler {
    PaymentService paymentService;
    String paymentMethodName;
    String entityRef;
    
    /** Creates a new instance of PaymentMethodHandler */
    public PaymentMethodHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String paymentMethodName, String entityRef) {
        super(initialDataParser, parentHandler);
        
        try {
            paymentService = PaymentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.paymentMethodName = paymentMethodName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("paymentMethodDescription")) {
            CreatePaymentMethodDescriptionForm commandForm = PaymentFormFactory.getCreatePaymentMethodDescriptionForm();
            
            commandForm.setPaymentMethodName(paymentMethodName);
            commandForm.set(getAttrsMap(attrs));
            
            checkCommandResult(paymentService.createPaymentMethodDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("comments")) {
            initialDataParser.pushHandler(new CommentsHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityTags")) {
            initialDataParser.pushHandler(new EntityTagsHandler(initialDataParser, this, entityRef));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("paymentMethod")) {
            initialDataParser.popHandler();
        }
    }
    
}
