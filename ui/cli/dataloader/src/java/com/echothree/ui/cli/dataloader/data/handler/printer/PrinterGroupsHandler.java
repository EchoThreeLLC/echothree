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

package com.echothree.ui.cli.dataloader.data.handler.printer;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.PrinterService;
import com.echothree.control.user.printer.common.form.CreatePrinterGroupForm;
import com.echothree.control.user.printer.common.form.PrinterFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PrinterGroupsHandler
        extends BaseHandler {

    PrinterService printerService;

    /** Creates a new instance of PrinterGroupsHandler */
    public PrinterGroupsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            printerService = PrinterUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("printerGroup")) {
            CreatePrinterGroupForm commandForm = PrinterFormFactory.getCreatePrinterGroupForm();

            commandForm.set(getAttrsMap(attrs));

            printerService.createPrinterGroup(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new PrinterGroupHandler(initialDataParser, this, commandForm.getPrinterGroupName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("printerGroups")) {
            initialDataParser.popHandler();
        }
    }

}
