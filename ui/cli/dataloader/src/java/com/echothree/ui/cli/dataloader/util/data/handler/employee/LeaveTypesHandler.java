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
import com.echothree.control.user.employee.common.form.CreateLeaveTypeForm;
import com.echothree.control.user.employee.common.form.EmployeeFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LeaveTypesHandler
        extends BaseHandler {
    EmployeeService employeeService;
    
    /** Creates a new instance of LeaveTypesHandler */
    public LeaveTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            employeeService = EmployeeUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("leaveType")) {
            String leaveTypeName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("leaveTypeName"))
                    leaveTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateLeaveTypeForm form = EmployeeFormFactory.getCreateLeaveTypeForm();
                
                form.setLeaveTypeName(leaveTypeName);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);
                
                employeeService.createLeaveType(initialDataParser.getUserVisit(), form);
                
                initialDataParser.pushHandler(new LeaveTypeHandler(initialDataParser, this, leaveTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("leaveTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
