// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.offer;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.OfferService;
import com.echothree.control.user.offer.common.form.CreateOfferItemPriceForm;
import com.echothree.control.user.offer.common.form.OfferFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class OfferItemHandler
        extends BaseHandler {
    OfferService offerService;
    String offerName;
    String itemName;
    
    /** Creates a new instance of OfferItemHandler */
    public OfferItemHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String offerName, String itemName) {
        super(initialDataParser, parentHandler);
        
        try {
            offerService = OfferUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.offerName = offerName;
        this.itemName = itemName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("offerItemPrice")) {
            String inventoryConditionName = null;
            String unitOfMeasureTypeName = null;
            String currencyIsoName = null;
            String unitPrice = null;
            String minimumUnitPrice = null;
            String maximumUnitPrice = null;
            String unitPriceIncrement = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("inventoryConditionName"))
                    inventoryConditionName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unitOfMeasureTypeName"))
                    unitOfMeasureTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("currencyIsoName"))
                    currencyIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unitPrice"))
                    unitPrice = attrs.getValue(i);
                else if(attrs.getQName(i).equals("minimumUnitPrice"))
                    minimumUnitPrice = attrs.getValue(i);
                else if(attrs.getQName(i).equals("maximumUnitPrice"))
                    maximumUnitPrice = attrs.getValue(i);
                else if(attrs.getQName(i).equals("unitPriceIncrement"))
                    unitPriceIncrement = attrs.getValue(i);
            }
            
            try {
                CreateOfferItemPriceForm form = OfferFormFactory.getCreateOfferItemPriceForm();
                
                form.setOfferName(offerName);
                form.setItemName(itemName);
                form.setInventoryConditionName(inventoryConditionName);
                form.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
                form.setCurrencyIsoName(currencyIsoName);
                form.setUnitPrice(unitPrice);
                form.setMinimumUnitPrice(minimumUnitPrice);
                form.setMaximumUnitPrice(maximumUnitPrice);
                form.setUnitPriceIncrement(unitPriceIncrement);
                
                offerService.createOfferItemPrice(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("offerItem")) {
            initialDataParser.popHandler();
        }
    }
    
}
