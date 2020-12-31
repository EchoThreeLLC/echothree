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

package com.echothree.ui.cli.dataloader.data.handler.accounting;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.AccountingService;
import com.echothree.control.user.accounting.common.form.AccountingFormFactory;
import com.echothree.control.user.accounting.common.form.CreateTransactionGlAccountCategoryDescriptionForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TransactionGlAccountCategoryHandler
        extends BaseHandler {
    AccountingService accountingService;
    String transactionTypeName;
    String transactionGlAccountCategoryName;
    
    /** Creates a new instance of TransactionGlAccountCategoryHandler */
    public TransactionGlAccountCategoryHandler(InitialDataParser initialDataParser, BaseHandler parentHandler,
            String transactionTypeName, String transactionGlAccountCategoryName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            accountingService = AccountingUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.transactionTypeName = transactionTypeName;
        this.transactionGlAccountCategoryName = transactionGlAccountCategoryName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("transactionGlAccountCategoryDescription")) {
            CreateTransactionGlAccountCategoryDescriptionForm commandForm = AccountingFormFactory.getCreateTransactionGlAccountCategoryDescriptionForm();
            
            commandForm.setTransactionTypeName(transactionTypeName);
            commandForm.setTransactionGlAccountCategoryName(transactionGlAccountCategoryName);
            commandForm.set(getAttrsMap(attrs));
            
            accountingService.createTransactionGlAccountCategoryDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("transactionGlAccountCategory")) {
            initialDataParser.popHandler();
        }
    }
    
}
