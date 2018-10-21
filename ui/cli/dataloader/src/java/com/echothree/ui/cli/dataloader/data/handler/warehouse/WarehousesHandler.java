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

package com.echothree.ui.cli.dataloader.data.handler.warehouse;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.remote.WarehouseService;
import com.echothree.control.user.warehouse.remote.form.CreateWarehouseForm;
import com.echothree.control.user.warehouse.remote.form.WarehouseFormFactory;
import com.echothree.control.user.warehouse.remote.result.CreateWarehouseResult;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class WarehousesHandler
        extends BaseHandler {

    WarehouseService warehouseService;

    /** Creates a new instance of WarehousesHandler */
    public WarehousesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            warehouseService = WarehouseUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("warehouse")) {
            CreateWarehouseForm commandForm = WarehouseFormFactory.getCreateWarehouseForm();

            commandForm.set(getAttrsMap(attrs));

            CommandResult commandResult = warehouseService.createWarehouse(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                CreateWarehouseResult result = (CreateWarehouseResult)executionResult.getResult();

                initialDataParser.pushHandler(new WarehouseHandler(initialDataParser, this, result.getPartyName(), result.getWarehouseName(), result.getEntityRef()));
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("warehouses")) {
            initialDataParser.popHandler();
        }
    }

}
