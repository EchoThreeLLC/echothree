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

package com.echothree.ui.cli.dataloader.data.handler.filter;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.FilterService;
import com.echothree.control.user.filter.common.form.CreateFilterAdjustmentSourceForm;
import com.echothree.control.user.filter.common.form.FilterFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class FilterAdjustmentSourcesHandler
        extends BaseHandler {
    FilterService filterService;
    
    /** Creates a new instance of FilterAdjustmentSourcesHandler */
    public FilterAdjustmentSourcesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
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
        if(localName.equals("filterAdjustmentSource")) {
            String filterAdjustmentSourceName = null;
            String allowedForInitialAmount = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("filterAdjustmentSourceName"))
                    filterAdjustmentSourceName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("allowedForInitialAmount"))
                    allowedForInitialAmount = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateFilterAdjustmentSourceForm commandForm = FilterFormFactory.getCreateFilterAdjustmentSourceForm();
                
                commandForm.setFilterAdjustmentSourceName(filterAdjustmentSourceName);
                commandForm.setAllowedForInitialAmount(allowedForInitialAmount);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                filterService.createFilterAdjustmentSource(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new FilterAdjustmentSourceHandler(initialDataParser, this, filterAdjustmentSourceName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("filterAdjustmentSources")) {
            initialDataParser.popHandler();
        }
    }
    
}
