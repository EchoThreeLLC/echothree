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
import com.echothree.control.user.offer.common.form.CreateUseTypeForm;
import com.echothree.control.user.offer.common.form.OfferFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class UseTypesHandler
        extends BaseHandler {
    OfferService offerService;
    
    /** Creates a new instance of UseTypesHandler */
    public UseTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            offerService = OfferUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("useType")) {
            String useTypeName = null;
            String isDefault = null;
            String sortOrder = null;

            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("useTypeName"))
                    useTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateUseTypeForm form = OfferFormFactory.getCreateUseTypeForm();
                
                form.setUseTypeName(useTypeName);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);
                
                offerService.createUseType(initialDataParser.getUserVisit(), form);
                
                initialDataParser.pushHandler(new UseTypeHandler(initialDataParser, this, useTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("useTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
