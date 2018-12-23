// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.ui.web.main.action.contactlist.contactlistgroup;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.form.CreateCustomerTypeContactListGroupForm;
import com.echothree.control.user.contactlist.common.form.GetContactListGroupForm;
import com.echothree.control.user.contactlist.common.result.GetContactListGroupResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/ContactList/ContactListGroup/CustomerTypeContactListGroupAdd",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeContactListGroupAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListGroup/CustomerTypeContactListGroup", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlistgroup/customerTypeContactListGroupAdd.jsp")
    }
)
public class CustomerTypeContactListGroupAddAction
        extends MainBaseAddAction<CustomerTypeContactListGroupAddActionForm> {

    @Override
    public void setupParameters(CustomerTypeContactListGroupAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListGroupName(findParameter(request, ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName()));
    }
    
    @Override
    public void setupTransfer(CustomerTypeContactListGroupAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetContactListGroupForm commandForm = ContactListUtil.getHome().getGetContactListGroupForm();

        commandForm.setContactListGroupName(actionForm.getContactListGroupName());
        
        CommandResult commandResult = ContactListUtil.getHome().getContactListGroup(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetContactListGroupResult result = (GetContactListGroupResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.CONTACT_LIST_GROUP, result.getContactListGroup());
        }
    }
    
    @Override
    public CommandResult doAdd(CustomerTypeContactListGroupAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateCustomerTypeContactListGroupForm commandForm = ContactListUtil.getHome().getCreateCustomerTypeContactListGroupForm();

        commandForm.setContactListGroupName( actionForm.getContactListGroupName());
        commandForm.setCustomerTypeName(actionForm.getCustomerTypeChoice());
        commandForm.setAddWhenCreated(actionForm.getAddWhenCreated().toString());

        return ContactListUtil.getHome().createCustomerTypeContactListGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CustomerTypeContactListGroupAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName());
    }
    
}
