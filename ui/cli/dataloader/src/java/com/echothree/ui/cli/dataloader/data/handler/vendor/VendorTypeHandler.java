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

package com.echothree.ui.cli.dataloader.data.handler.vendor;

import com.echothree.control.user.vendor.common.VendorService;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.form.VendorFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class VendorTypeHandler
        extends BaseHandler {

    VendorService vendorService = VendorUtil.getHome();

    String vendorTypeName;
    
    /** Creates a new instance of VendorTypeHandler */
    public VendorTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String vendorTypeName)
            throws NamingException {
        super(initialDataParser, parentHandler);

        this.vendorTypeName = vendorTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("vendorTypeDescription")) {
            var commandForm = VendorFormFactory.getCreateVendorTypeDescriptionForm();
            
            commandForm.setVendorTypeName(vendorTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            vendorService.createVendorTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("vendorType")) {
            initialDataParser.popHandler();
        }
    }
    
}
