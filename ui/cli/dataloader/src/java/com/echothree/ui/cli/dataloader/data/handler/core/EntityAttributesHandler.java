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
import com.echothree.control.user.core.remote.form.CreateEntityBooleanAttributeForm;
import com.echothree.control.user.core.remote.form.CreateEntityDateAttributeForm;
import com.echothree.control.user.core.remote.form.CreateEntityGeoPointAttributeForm;
import com.echothree.control.user.core.remote.form.CreateEntityIntegerAttributeForm;
import com.echothree.control.user.core.remote.form.CreateEntityListItemAttributeForm;
import com.echothree.control.user.core.remote.form.CreateEntityLongAttributeForm;
import com.echothree.control.user.core.remote.form.CreateEntityNameAttributeForm;
import com.echothree.control.user.core.remote.form.CreateEntityStringAttributeForm;
import com.echothree.control.user.core.remote.form.CreateEntityTimeAttributeForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EntityAttributesHandler
        extends BaseHandler {
    
    CoreService coreService;
    String entityRef;
    
    /** Creates a new instance of EntityAttributesHandler */
    public EntityAttributesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String entityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            coreService = CoreUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("entityNameAttribute")) {
            CreateEntityNameAttributeForm commandForm = CoreFormFactory.getCreateEntityNameAttributeForm();
            
            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createEntityNameAttribute(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityIntegerAttribute")) {
            CreateEntityIntegerAttributeForm commandForm = CoreFormFactory.getCreateEntityIntegerAttributeForm();
            
            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createEntityIntegerAttribute(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityLongAttribute")) {
            CreateEntityLongAttributeForm commandForm = CoreFormFactory.getCreateEntityLongAttributeForm();
            
            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createEntityLongAttribute(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityBooleanAttribute")) {
            CreateEntityBooleanAttributeForm commandForm = CoreFormFactory.getCreateEntityBooleanAttributeForm();
            
            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createEntityBooleanAttribute(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityStringAttribute")) {
            CreateEntityStringAttributeForm commandForm = CoreFormFactory.getCreateEntityStringAttributeForm();
            
            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createEntityStringAttribute(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityGeoPointAttribute")) {
            CreateEntityGeoPointAttributeForm commandForm = CoreFormFactory.getCreateEntityGeoPointAttributeForm();
            
            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createEntityGeoPointAttribute(initialDataParser.getUserVisit(), commandForm);
        } /* else if(localName.equals("entityBlobAttribute")) {
        } else if(localName.equals("entityClobAttribute")) {
        } else if(localName.equals("entityEntityAttribute")) {
        } else if(localName.equals("entityCollectionAttribute")) {
        }*/ else if(localName.equals("entityDateAttribute")) {
            CreateEntityDateAttributeForm commandForm = CoreFormFactory.getCreateEntityDateAttributeForm();

            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityDateAttribute(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityTimeAttribute")) {
            CreateEntityTimeAttributeForm commandForm = CoreFormFactory.getCreateEntityTimeAttributeForm();

            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityTimeAttribute(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityListItemAttribute")) {
            CreateEntityListItemAttributeForm commandForm = CoreFormFactory.getCreateEntityListItemAttributeForm();
            
            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createEntityListItemAttribute(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityMultipleListItemAttributes")) {
            String entityAttributeName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("entityAttributeName"))
                    entityAttributeName = attrs.getValue(i);
            }
            
            try {
                initialDataParser.pushHandler(new EntityMultipleListItemAttributesHandler(initialDataParser, this, entityRef, entityAttributeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("entityAttributes")) {
            initialDataParser.popHandler();
        }
    }
    
}
