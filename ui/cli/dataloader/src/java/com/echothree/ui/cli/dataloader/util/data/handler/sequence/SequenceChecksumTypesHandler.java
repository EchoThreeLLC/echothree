// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler.sequence;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.common.SequenceService;
import com.echothree.control.user.sequence.common.form.SequenceFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SequenceChecksumTypesHandler
        extends BaseHandler {

    SequenceService sequenceService;

    /** Creates a new instance of SequenceChecksumTypesHandler */
    public SequenceChecksumTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            sequenceService = SequenceUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("sequenceChecksumType")) {
            var commandForm = SequenceFormFactory.getCreateSequenceChecksumTypeForm();

            commandForm.set(getAttrsMap(attrs));

            sequenceService.createSequenceChecksumType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new SequenceChecksumTypeHandler(initialDataParser, this, commandForm.getSequenceChecksumTypeName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("sequenceChecksumTypes")) {
            initialDataParser.popHandler();
        }
    }
}