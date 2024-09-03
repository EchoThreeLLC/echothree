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

package com.echothree.ui.cli.dataloader.util.data.handler.scale;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.ScaleService;
import com.echothree.control.user.scale.common.form.ScaleFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ScalesHandler
        extends BaseHandler {

    ScaleService scaleService;

    /** Creates a new instance of ScalesHandler */
    public ScalesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            scaleService = ScaleUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("scale")) {
            var commandForm = ScaleFormFactory.getCreateScaleForm();

            commandForm.set(getAttrsMap(attrs));

            scaleService.createScale(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new ScaleHandler(initialDataParser, this, commandForm.getScaleName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("scales")) {
            initialDataParser.popHandler();
        }
    }

}
