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

package com.echothree.ui.cli.dataloader.util.data.handler.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.form.CreateEntityLongRangeDescriptionForm;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EntityLongRangeHandler
        extends BaseHandler {
    
    CoreService coreService;
    String componentVendorName;
    String entityTypeName;
    String entityAttributeName;
    String entityLongRangeName;
    
    /** Creates a new instance of EntityLongRangeHandler */
    public EntityLongRangeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName, String entityTypeName,
            String entityAttributeName, String entityLongRangeName)
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
        this.entityLongRangeName = entityLongRangeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("entityLongRangeDescription")) {
            var commandForm = CoreFormFactory.getCreateEntityLongRangeDescriptionForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.setEntityLongRangeName(entityLongRangeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityLongRangeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("entityLongRange")) {
            initialDataParser.popHandler();
        }
    }
    
}
