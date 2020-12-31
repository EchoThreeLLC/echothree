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

package com.echothree.ui.cli.dataloader.data.handler.filter;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.FilterService;
import com.echothree.control.user.filter.common.form.CreateFilterDescriptionForm;
import com.echothree.control.user.filter.common.form.CreateFilterEntranceStepForm;
import com.echothree.control.user.filter.common.form.CreateFilterStepDestinationForm;
import com.echothree.control.user.filter.common.form.CreateFilterStepForm;
import com.echothree.control.user.filter.common.form.FilterFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class FilterHandler
        extends BaseHandler {
    FilterService filterService;
    String filterKindName;
    String filterTypeName;
    String filterName;
    
    /** Creates a new instance of FilterHandler */
    public FilterHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String filterKindName, String filterTypeName, String filterName) {
        super(initialDataParser, parentHandler);
        
        try {
            filterService = FilterUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.filterKindName = filterKindName;
        this.filterTypeName = filterTypeName;
        this.filterName = filterName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("filterDescription")) {
            String languageIsoName = null;
            String description = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                CreateFilterDescriptionForm commandForm = FilterFormFactory.getCreateFilterDescriptionForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                filterService.createFilterDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("filterStep")) {
            String filterStepName = null;
            String filterItemSelectorName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("filterStepName"))
                    filterStepName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("filterItemSelectorName"))
                    filterItemSelectorName = attrs.getValue(i);
            }
            
            try {
                CreateFilterStepForm commandForm = FilterFormFactory.getCreateFilterStepForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.setFilterStepName(filterStepName);
                commandForm.setFilterItemSelectorName(filterItemSelectorName);
                
                filterService.createFilterStep(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new FilterStepHandler(initialDataParser, this, filterKindName, filterTypeName, filterName, filterStepName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("filterEntranceStep")) {
            String filterStepName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("filterStepName"))
                    filterStepName = attrs.getValue(i);
            }
            
            try {
                CreateFilterEntranceStepForm commandForm = FilterFormFactory.getCreateFilterEntranceStepForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.setFilterStepName(filterStepName);
                
                filterService.createFilterEntranceStep(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("filterStepDestination")) {
            String fromFilterStepName = null;
            String toFilterStepName = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("fromFilterStepName"))
                    fromFilterStepName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("toFilterStepName"))
                    toFilterStepName = attrs.getValue(i);
            }
            
            try {
                CreateFilterStepDestinationForm commandForm = FilterFormFactory.getCreateFilterStepDestinationForm();
                
                commandForm.setFilterKindName(filterKindName);
                commandForm.setFilterTypeName(filterTypeName);
                commandForm.setFilterName(filterName);
                commandForm.setFromFilterStepName(fromFilterStepName);
                commandForm.setToFilterStepName(toFilterStepName);
                
                filterService.createFilterStepDestination(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("filter")) {
            initialDataParser.popHandler();
        }
    }
    
}
