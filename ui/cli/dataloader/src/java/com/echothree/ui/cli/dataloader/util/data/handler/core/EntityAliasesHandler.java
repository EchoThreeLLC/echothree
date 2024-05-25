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

import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.form.CreateEntityBooleanAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityDateAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityGeoPointAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityIntegerAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityListItemAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityLongAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityNameAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityStringAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityTimeAttributeForm;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EntityAliasesHandler
        extends BaseHandler {

    CoreService coreService;
    String entityRef;

    /** Creates a new instance of EntityAliasesHandler */
    public EntityAliasesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String entityRef)
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
        if(localName.equals("entityAlias")) {
            var commandForm = CoreFormFactory.getCreateEntityAliasForm();
            
            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));
            
            coreService.createEntityAlias(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("entityAliases")) {
            initialDataParser.popHandler();
        }
    }
    
}
