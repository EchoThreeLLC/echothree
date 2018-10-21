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

package com.echothree.ui.cli.dataloader.data.handler.printer;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.remote.PrinterService;
import com.echothree.control.user.printer.remote.form.CreatePrinterGroupUseTypeForm;
import com.echothree.control.user.printer.remote.form.PrinterFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PrinterGroupUseTypesHandler
        extends BaseHandler {

    PrinterService printerService;

    /** Creates a new instance of PrinterGroupUseTypesHandler */
    public PrinterGroupUseTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
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
        if(localName.equals("printerGroupUseType")) {
            CreatePrinterGroupUseTypeForm commandForm = PrinterFormFactory.getCreatePrinterGroupUseTypeForm();

            commandForm.set(getAttrsMap(attrs));

            printerService.createPrinterGroupUseType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new PrinterGroupUseTypeHandler(initialDataParser, this, commandForm.getPrinterGroupUseTypeName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("printerGroupUseTypes")) {
            initialDataParser.popHandler();
        }
    }

}
