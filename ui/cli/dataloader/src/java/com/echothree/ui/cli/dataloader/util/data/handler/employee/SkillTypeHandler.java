// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.data.handler.employee;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.EmployeeService;
import com.echothree.control.user.employee.common.form.CreateSkillTypeDescriptionForm;
import com.echothree.control.user.employee.common.form.EmployeeFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SkillTypeHandler
        extends BaseHandler {
    EmployeeService employeeService;
    String skillTypeName;
    
    /** Creates a new instance of SkillTypeHandler */
    public SkillTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String skillTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            employeeService = EmployeeUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.skillTypeName = skillTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("skillTypeDescription")) {
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
                CreateSkillTypeDescriptionForm form = EmployeeFormFactory.getCreateSkillTypeDescriptionForm();
                
                form.setSkillTypeName(skillTypeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                employeeService.createSkillTypeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("skillType")) {
            initialDataParser.popHandler();
        }
    }
    
}
