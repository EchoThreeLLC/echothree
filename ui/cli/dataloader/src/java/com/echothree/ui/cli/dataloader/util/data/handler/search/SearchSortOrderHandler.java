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

package com.echothree.ui.cli.dataloader.util.data.handler.search;

import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.SearchService;
import com.echothree.control.user.search.common.form.CreateSearchSortOrderDescriptionForm;
import com.echothree.control.user.search.common.form.SearchFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SearchSortOrderHandler
        extends BaseHandler {

    SearchService searchService;
    String searchKindName;
    String searchSortOrderName;

    /** Creates a new instance of SearchSortOrderHandler */
    public SearchSortOrderHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String searchKindName, String searchSortOrderName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            searchService = SearchUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.searchKindName = searchKindName;
        this.searchSortOrderName = searchSortOrderName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("searchSortOrderDescription")) {
            var commandForm = SearchFormFactory.getCreateSearchSortOrderDescriptionForm();

            commandForm.setSearchKindName(searchKindName);
            commandForm.setSearchSortOrderName(searchSortOrderName);
            commandForm.set(getAttrsMap(attrs));

            searchService.createSearchSortOrderDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("searchSortOrder")) {
            initialDataParser.popHandler();
        }
    }
}