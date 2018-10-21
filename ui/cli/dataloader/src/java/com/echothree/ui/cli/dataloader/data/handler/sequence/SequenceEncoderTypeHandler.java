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

package com.echothree.ui.cli.dataloader.data.handler.sequence;

import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.remote.SequenceService;
import com.echothree.control.user.sequence.remote.form.CreateSequenceEncoderTypeDescriptionForm;
import com.echothree.control.user.sequence.remote.form.SequenceFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SequenceEncoderTypeHandler
        extends BaseHandler {

    SequenceService sequenceService;
    String sequenceEncoderTypeName;

    /** Creates a new instance of SequenceEncoderTypeHandler */
    public SequenceEncoderTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String sequenceEncoderTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            sequenceService = SequenceUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.sequenceEncoderTypeName = sequenceEncoderTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("sequenceEncoderTypeDescription")) {
            CreateSequenceEncoderTypeDescriptionForm commandForm = SequenceFormFactory.getCreateSequenceEncoderTypeDescriptionForm();

            commandForm.setSequenceEncoderTypeName(sequenceEncoderTypeName);
            commandForm.set(getAttrsMap(attrs));

            sequenceService.createSequenceEncoderTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("sequenceEncoderType")) {
            initialDataParser.popHandler();
        }
    }
}