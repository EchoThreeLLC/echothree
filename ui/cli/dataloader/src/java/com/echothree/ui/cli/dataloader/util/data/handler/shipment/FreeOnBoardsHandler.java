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

package com.echothree.ui.cli.dataloader.util.data.handler.shipment;

import com.echothree.control.user.shipment.common.ShipmentService;
import com.echothree.control.user.shipment.common.ShipmentUtil;
import com.echothree.control.user.shipment.common.form.ShipmentFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class FreeOnBoardsHandler
        extends BaseHandler {

    ShipmentService shipmentService;
    
    /** Creates a new instance of FreeOnBoardsHandler */
    public FreeOnBoardsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws NamingException {
        super(initialDataParser, parentHandler);
        
        shipmentService = ShipmentUtil.getHome();
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("freeOnBoard")) {
            var commandForm = ShipmentFormFactory.getCreateFreeOnBoardForm();

            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(shipmentService.createFreeOnBoard(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new FreeOnBoardHandler(initialDataParser, this, commandForm.getFreeOnBoardName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("freeOnBoards")) {
            initialDataParser.popHandler();
        }
    }
    
}
