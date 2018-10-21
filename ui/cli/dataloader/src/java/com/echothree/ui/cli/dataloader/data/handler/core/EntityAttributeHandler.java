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
import com.echothree.control.user.core.remote.form.CreateEntityAttributeDescriptionForm;
import com.echothree.control.user.core.remote.form.CreateEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.remote.form.CreateEntityAttributeEntityTypeForm;
import com.echothree.control.user.core.remote.form.CreateEntityIntegerRangeForm;
import com.echothree.control.user.core.remote.form.CreateEntityListItemForm;
import com.echothree.control.user.core.remote.form.CreateEntityLongRangeForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EntityAttributeHandler
        extends BaseHandler {
    
    CoreService coreService;
    String componentVendorName;
    String entityTypeName;
    String entityAttributeName;
    
    /** Creates a new instance of EntityAttributeHandler */
    public EntityAttributeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName, String entityTypeName,
            String entityAttributeName)
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
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("entityAttributeDescription")) {
            CreateEntityAttributeDescriptionForm commandForm = CoreFormFactory.getCreateEntityAttributeDescriptionForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityAttributeDescription(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityAttributeEntityAttributeGroup")) {
            CreateEntityAttributeEntityAttributeGroupForm commandForm = CoreFormFactory.getCreateEntityAttributeEntityAttributeGroupForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityAttributeEntityAttributeGroup(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityListItem")) {
            CreateEntityListItemForm commandForm = CoreFormFactory.getCreateEntityListItemForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityListItem(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new EntityListItemHandler(initialDataParser, this, componentVendorName, entityTypeName, entityAttributeName,
                    commandForm.getEntityListItemName()));
        } else if(localName.equals("entityIntegerRange")) {
            CreateEntityIntegerRangeForm commandForm = CoreFormFactory.getCreateEntityIntegerRangeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityIntegerRange(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new EntityIntegerRangeHandler(initialDataParser, this, componentVendorName, entityTypeName, entityAttributeName,
                    commandForm.getEntityIntegerRangeName()));
        } else if(localName.equals("entityLongRange")) {
            CreateEntityLongRangeForm commandForm = CoreFormFactory.getCreateEntityLongRangeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityLongRange(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new EntityLongRangeHandler(initialDataParser, this, componentVendorName, entityTypeName, entityAttributeName,
                    commandForm.getEntityLongRangeName()));
        } else if(localName.equals("entityAttributeEntityType")) {
            CreateEntityAttributeEntityTypeForm commandForm = CoreFormFactory.getCreateEntityAttributeEntityTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityAttributeEntityType(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("entityAttribute")) {
            initialDataParser.popHandler();
        }
    }
    
}
