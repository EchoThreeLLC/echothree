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

package com.echothree.ui.cli.dataloader.data.handler.employee;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.EmployeeService;
import com.echothree.control.user.employee.common.form.CreateTerminationReasonDescriptionForm;
import com.echothree.control.user.employee.common.form.EmployeeFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TerminationReasonHandler
        extends BaseHandler {
    EmployeeService employeeService;
    String terminationReasonName;
    
    /** Creates a new instance of TerminationReasonHandler */
    public TerminationReasonHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String terminationReasonName) {
        super(initialDataParser, parentHandler);
        
        try {
            employeeService = EmployeeUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.terminationReasonName = terminationReasonName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("terminationReasonDescription")) {
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
                CreateTerminationReasonDescriptionForm form = EmployeeFormFactory.getCreateTerminationReasonDescriptionForm();
                
                form.setTerminationReasonName(terminationReasonName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                employeeService.createTerminationReasonDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("terminationReason")) {
            initialDataParser.popHandler();
        }
    }
    
}
