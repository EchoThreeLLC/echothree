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

package com.echothree.ui.cli.dataloader.util.data.handler.filter;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.FilterService;
import com.echothree.control.user.filter.common.form.CreateFilterAdjustmentTypeForm;
import com.echothree.control.user.filter.common.form.FilterFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class FilterAdjustmentTypesHandler
        extends BaseHandler {
    FilterService filterService;
    
    /** Creates a new instance of FilterAdjustmentTypesHandler */
    public FilterAdjustmentTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            filterService = FilterUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("filterAdjustmentType")) {
            String filterAdjustmentTypeName = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("filterAdjustmentTypeName"))
                    filterAdjustmentTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var commandForm = FilterFormFactory.getCreateFilterAdjustmentTypeForm();
                
                commandForm.setFilterAdjustmentTypeName(filterAdjustmentTypeName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                filterService.createFilterAdjustmentType(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new FilterAdjustmentTypeHandler(initialDataParser, this, filterAdjustmentTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("filterAdjustmentTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
