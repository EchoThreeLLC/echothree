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

package com.echothree.ui.cli.dataloader.data.handler.sales;

import com.echothree.control.user.sales.common.SalesUtil;
import com.echothree.control.user.sales.remote.SalesService;
import com.echothree.control.user.sales.remote.form.CreateSalesOrderBatchForm;
import com.echothree.control.user.sales.remote.form.SalesFormFactory;
import com.echothree.control.user.sales.remote.result.CreateSalesOrderBatchResult;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SalesOrderBatchesHandler
        extends BaseHandler {

    SalesService salesService;
    
    /** Creates a new instance of SalesOrderBatchesHandler */
    public SalesOrderBatchesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            salesService = SalesUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("salesOrderBatch")) {
            CreateSalesOrderBatchForm commandForm = SalesFormFactory.getCreateSalesOrderBatchForm();

            commandForm.set(getAttrsMap(attrs));

            CommandResult commandResult = salesService.createSalesOrderBatch(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                CreateSalesOrderBatchResult result = (CreateSalesOrderBatchResult)executionResult.getResult();
                String batchName = result.getBatchName();

                // TODO
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("salesOrderBatches")) {
            initialDataParser.popHandler();
        }
    }
    
}
