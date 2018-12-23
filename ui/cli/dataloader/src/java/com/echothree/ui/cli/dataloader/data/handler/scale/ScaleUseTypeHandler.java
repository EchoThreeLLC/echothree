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

package com.echothree.ui.cli.dataloader.data.handler.scale;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.ScaleService;
import com.echothree.control.user.scale.common.form.CreateScaleUseTypeDescriptionForm;
import com.echothree.control.user.scale.common.form.ScaleFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ScaleUseTypeHandler
        extends BaseHandler {

    ScaleService scaleService;
    String scaleUseTypeName;

    /** Creates a new instance of ScaleUseTypeHandler */
    public ScaleUseTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String scaleUseTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            scaleService = ScaleUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.scaleUseTypeName = scaleUseTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("scaleUseTypeDescription")) {
            CreateScaleUseTypeDescriptionForm commandForm = ScaleFormFactory.getCreateScaleUseTypeDescriptionForm();

            commandForm.setScaleUseTypeName(scaleUseTypeName);
            commandForm.set(getAttrsMap(attrs));

            scaleService.createScaleUseTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("scaleUseType")) {
            initialDataParser.popHandler();
        }
    }

}
