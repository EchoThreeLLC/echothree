// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.party;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.PartyService;
import com.echothree.control.user.party.common.form.CreateTimeZoneForm;
import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TimeZonesHandler
        extends BaseHandler {
    PartyService partyService;
    
    /** Creates a new instance of TimeZonesHandler */
    public TimeZonesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            partyService = PartyUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("timeZone")) {
            String javaTimeZoneName = null;
            String unixTimeZoneName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("javaTimeZoneName"))
                    javaTimeZoneName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unixTimeZoneName"))
                    unixTimeZoneName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateTimeZoneForm createTimeZoneForm = PartyFormFactory.getCreateTimeZoneForm();
                
                createTimeZoneForm.setJavaTimeZoneName(javaTimeZoneName);
                createTimeZoneForm.setUnixTimeZoneName(unixTimeZoneName);
                createTimeZoneForm.setIsDefault(isDefault);
                createTimeZoneForm.setSortOrder(sortOrder);
                
                partyService.createTimeZone(initialDataParser.getUserVisit(), createTimeZoneForm);
                
                initialDataParser.pushHandler(new TimeZoneHandler(initialDataParser, this, javaTimeZoneName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("timeZones")) {
            initialDataParser.popHandler();
        }
    }
    
}
