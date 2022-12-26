// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

public class FreeOnBoardHandler
        extends BaseHandler {

    ShipmentService shipmentService;

    String freeOnBoardName;
    
    /** Creates a new instance of FreeOnBoardHandler */
    public FreeOnBoardHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String freeOnBoardName)
            throws NamingException {
        super(initialDataParser, parentHandler);
        
        shipmentService = ShipmentUtil.getHome();

        this.freeOnBoardName = freeOnBoardName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("freeOnBoardDescription")) {
            var commandForm = ShipmentFormFactory.getCreateFreeOnBoardDescriptionForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(shipmentService.createFreeOnBoardDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("freeOnBoard")) {
            initialDataParser.popHandler();
        }
    }
    
}
