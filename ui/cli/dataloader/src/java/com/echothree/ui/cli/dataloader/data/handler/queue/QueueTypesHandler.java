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

package com.echothree.ui.cli.dataloader.data.handler.queue;

import com.echothree.control.user.queue.common.QueueUtil;
import com.echothree.control.user.queue.common.QueueService;
import com.echothree.control.user.queue.common.form.CreateQueueTypeForm;
import com.echothree.control.user.queue.common.form.QueueFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class QueueTypesHandler
        extends BaseHandler {
    
    QueueService queueService;
    
    /** Creates a new instance of QueueTypesHandler */
    public QueueTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            queueService = QueueUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("queueType")) {
            CreateQueueTypeForm commandForm = QueueFormFactory.getCreateQueueTypeForm();

            commandForm.set(getAttrsMap(attrs));

            queueService.createQueueType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new QueueTypeHandler(initialDataParser, this, commandForm.getQueueTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("queueTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
