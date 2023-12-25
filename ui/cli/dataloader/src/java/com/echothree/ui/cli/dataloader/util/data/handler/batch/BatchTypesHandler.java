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

package com.echothree.ui.cli.dataloader.util.data.handler.batch;

import com.echothree.control.user.batch.common.BatchUtil;
import com.echothree.control.user.batch.common.BatchService;
import com.echothree.control.user.batch.common.form.BatchFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class BatchTypesHandler
        extends BaseHandler {

    BatchService batchService = BatchUtil.getHome();
    
    /** Creates a new instance of BatchTypesHandler */
    public BatchTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws NamingException {
        super(initialDataParser, parentHandler);
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("batchType")) {
            var commandForm = BatchFormFactory.getCreateBatchTypeForm();

            commandForm.set(getAttrsMap(attrs));
            
            var commandResult = batchService.createBatchType(initialDataParser.getUserVisit(), commandForm);

            if(commandResult.hasErrors()) {
                getLogger().error(commandResult.toString());
            }

            initialDataParser.pushHandler(new BatchTypeHandler(initialDataParser, this, commandForm.getBatchTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("batchTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
