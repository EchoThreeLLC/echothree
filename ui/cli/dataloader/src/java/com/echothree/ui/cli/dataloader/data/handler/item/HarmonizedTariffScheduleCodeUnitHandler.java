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

package com.echothree.ui.cli.dataloader.data.handler.item;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.form.CreateHarmonizedTariffScheduleCodeUnitDescriptionForm;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class HarmonizedTariffScheduleCodeUnitHandler
        extends BaseHandler {

    ItemService itemService;
    String harmonizedTariffScheduleCodeUnitName;

    /** Creates a new instance of HarmonizedTariffScheduleCodeUnitHandler */
    public HarmonizedTariffScheduleCodeUnitHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String harmonizedTariffScheduleCodeUnitName)
            throws SAXException {

        super(initialDataParser, parentHandler);

        try {
            itemService = ItemUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.harmonizedTariffScheduleCodeUnitName = harmonizedTariffScheduleCodeUnitName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("harmonizedTariffScheduleCodeUnitDescription")) {
            CreateHarmonizedTariffScheduleCodeUnitDescriptionForm commandForm = ItemFormFactory.getCreateHarmonizedTariffScheduleCodeUnitDescriptionForm();

            commandForm.setHarmonizedTariffScheduleCodeUnitName(harmonizedTariffScheduleCodeUnitName);
            commandForm.set(getAttrsMap(attrs));

            itemService.createHarmonizedTariffScheduleCodeUnitDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("harmonizedTariffScheduleCodeUnit")) {
            initialDataParser.popHandler();
        }
    }

}
