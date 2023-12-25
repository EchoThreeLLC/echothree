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

package com.echothree.ui.cli.dataloader.util.data.handler.geo;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.GeoService;
import com.echothree.control.user.geo.common.form.CreateGeoCodeScopeForm;
import com.echothree.control.user.geo.common.form.GeoFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GeoCodeScopesHandler
        extends BaseHandler {
    GeoService geoService;
    
    /** Creates a new instance of GeoCodeScopesHandler */
    public GeoCodeScopesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            geoService = GeoUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("geoCodeScope")) {
            String geoCodeScopeName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("geoCodeScopeName"))
                    geoCodeScopeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateGeoCodeScopeForm createGeoCodeScopeForm = GeoFormFactory.getCreateGeoCodeScopeForm();
                
                createGeoCodeScopeForm.setGeoCodeScopeName(geoCodeScopeName);
                createGeoCodeScopeForm.setIsDefault(isDefault);
                createGeoCodeScopeForm.setSortOrder(sortOrder);
                
                geoService.createGeoCodeScope(initialDataParser.getUserVisit(), createGeoCodeScopeForm);
                
                initialDataParser.pushHandler(new GeoCodeScopeHandler(initialDataParser, this, geoCodeScopeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("geoCodeScopes")) {
            initialDataParser.popHandler();
        }
    }
    
}
