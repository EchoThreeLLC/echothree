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

package com.echothree.ui.cli.dataloader.data.handler.warehouse;

import com.echothree.control.user.warehouse.common.WarehouseUtil;
import com.echothree.control.user.warehouse.common.WarehouseService;
import com.echothree.control.user.warehouse.common.form.CreateLocationUseTypeForm;
import com.echothree.control.user.warehouse.common.form.WarehouseFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LocationUseTypesHandler
        extends BaseHandler {
    WarehouseService warehouseService;
    
    /** Creates a new instance of LocationUseTypesHandler */
    public LocationUseTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            warehouseService = WarehouseUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("locationUseType")) {
            String locationUseTypeName = null;
            String allowMultiple = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("locationUseTypeName"))
                    locationUseTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("allowMultiple"))
                    allowMultiple = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateLocationUseTypeForm form = WarehouseFormFactory.getCreateLocationUseTypeForm();
                
                form.setLocationUseTypeName(locationUseTypeName);
                form.setAllowMultiple(allowMultiple);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);
                
                warehouseService.createLocationUseType(initialDataParser.getUserVisit(), form);
                
                initialDataParser.pushHandler(new LocationUseTypeHandler(initialDataParser, this, locationUseTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("locationUseTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
