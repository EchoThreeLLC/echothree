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

package com.echothree.ui.cli.dataloader.data.handler.club;

import com.echothree.control.user.club.common.ClubUtil;
import com.echothree.control.user.club.remote.ClubService;
import com.echothree.control.user.club.remote.form.ClubFormFactory;
import com.echothree.control.user.club.remote.form.CreateClubForm;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ClubsHandler
        extends BaseHandler {
    ClubService clubService;
    
    /** Creates a new instance of ClubsHandler */
    public ClubsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            clubService = ClubUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("club")) {
            String clubName = null;
            String subscriptionTypeName = null;
            String clubPriceFilterName = null;
            String currencyIsoName = null;
            String isDefault = null;
            String sortOrder = null;
            String description = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("clubName"))
                    clubName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("subscriptionTypeName"))
                    subscriptionTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("clubPriceFilterName"))
                    clubPriceFilterName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("currencyIsoName"))
                    currencyIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                CreateClubForm commandForm = ClubFormFactory.getCreateClubForm();
                
                commandForm.setClubName(clubName);
                commandForm.setSubscriptionTypeName(subscriptionTypeName);
                commandForm.setClubPriceFilterName(clubPriceFilterName);
                commandForm.setCurrencyIsoName(currencyIsoName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                commandForm.setDescription(description);
                
                clubService.createClub(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new ClubHandler(initialDataParser, this, clubName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("clubs")) {
            initialDataParser.popHandler();
        }
    }
    
}
