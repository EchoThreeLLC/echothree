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
import com.echothree.control.user.core.remote.form.CreateEntityIntegerRangeDescriptionForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EntityIntegerRangeHandler
        extends BaseHandler {
    
    CoreService coreService;
    String componentVendorName;
    String entityTypeName;
    String entityAttributeName;
    String entityIntegerRangeName;
    
    /** Creates a new instance of EntityIntegerRangeHandler */
    public EntityIntegerRangeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName, String entityTypeName,
            String entityAttributeName, String entityIntegerRangeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            coreService = CoreUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.entityAttributeName = entityAttributeName;
        this.entityIntegerRangeName = entityIntegerRangeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("entityIntegerRangeDescription")) {
            CreateEntityIntegerRangeDescriptionForm commandForm = CoreFormFactory.getCreateEntityIntegerRangeDescriptionForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityIntegerRangeName(entityIntegerRangeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityIntegerRangeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("entityIntegerRange")) {
            initialDataParser.popHandler();
        }
    }
    
}
