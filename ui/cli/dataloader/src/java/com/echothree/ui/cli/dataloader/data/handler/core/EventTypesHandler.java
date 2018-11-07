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
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.form.CreateEventTypeForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EventTypesHandler
        extends BaseHandler {

    CoreService coreService;

    /** Creates a new instance of EventTypesHandler */
    public EventTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);

        try {
            coreService = CoreUtil.getHome();
        } catch(NamingException ne) {
            // TODO: Handle Exception
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("eventType")) {
            CreateEventTypeForm commandForm = CoreFormFactory.getCreateEventTypeForm();

            commandForm.set(getAttrsMap(attrs));

            coreService.createEventType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new EventTypeHandler(initialDataParser, this, commandForm.getEventTypeName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("eventTypes")) {
            initialDataParser.popHandler();
        }
    }
}