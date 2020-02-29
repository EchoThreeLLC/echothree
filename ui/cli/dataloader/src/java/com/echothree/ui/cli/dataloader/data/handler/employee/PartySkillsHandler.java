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
import com.echothree.control.user.employee.common.form.CreatePartySkillForm;
import com.echothree.control.user.employee.common.form.EmployeeFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PartySkillsHandler
        extends BaseHandler {

    EmployeeService employeeService;
    String partyName;
    
    /** Creates a new instance of PartySkillsHandler */
    public PartySkillsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            employeeService = EmployeeUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.partyName = partyName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partySkill")) {
            String skillTypeName = null;
            
            int attrCount = attrs.getLength();
            for(int i = 0; i < attrCount; i++) {
                if(attrs.getQName(i).equals("skillTypeName"))
                    skillTypeName = attrs.getValue(i);
            }
            
            try {
                CreatePartySkillForm commandForm = EmployeeFormFactory.getCreatePartySkillForm();
                
                commandForm.setPartyName(partyName);
                commandForm.setSkillTypeName(skillTypeName);

                checkCommandResult(employeeService.createPartySkill(initialDataParser.getUserVisit(), commandForm));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("partySkills")) {
            initialDataParser.popHandler();
        }
    }
    
}
