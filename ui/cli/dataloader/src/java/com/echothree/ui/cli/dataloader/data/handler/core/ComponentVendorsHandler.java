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

package com.echothree.ui.cli.dataloader.data.handler.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.remote.CoreService;
import com.echothree.control.user.core.remote.form.CoreFormFactory;
import com.echothree.control.user.core.remote.form.CreateComponentVendorForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ComponentVendorsHandler
        extends BaseHandler {

    CoreService coreService;

    /** Creates a new instance of ComponentVendorsHandler */
    public ComponentVendorsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            coreService = CoreUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("componentVendor")) {
            CreateComponentVendorForm commandForm = CoreFormFactory.getCreateComponentVendorForm();

            commandForm.set(getAttrsMap(attrs));

            String commandAction = (String)commandForm.get("CommandAction");
            if(commandAction == null || commandAction.equals("create")) {
                coreService.createComponentVendor(initialDataParser.getUserVisit(), commandForm);
            }

            initialDataParser.pushHandler(new ComponentVendorHandler(initialDataParser, this, commandForm.getComponentVendorName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("componentVendors")) {
            initialDataParser.popHandler();
        }
    }
}