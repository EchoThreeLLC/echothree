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

package com.echothree.ui.web.main.action.contactlist.contactlist;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.form.DeleteContactListContactMechanismPurposeForm;
import com.echothree.control.user.contactlist.common.form.GetContactListContactMechanismPurposeForm;
import com.echothree.control.user.contactlist.common.result.GetContactListContactMechanismPurposeResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/ContactList/ContactList/ContactListContactMechanismPurposeDelete",
    mappingClass = SecureActionMapping.class,
    name = "ContactListContactMechanismPurposeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactList/ContactListContactMechanismPurpose", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlist/contactListContactMechanismPurposeDelete.jsp")
    }
)
public class ContactListContactMechanismPurposeDeleteAction
        extends MainBaseDeleteAction<ContactListContactMechanismPurposeDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ContactListContactMechanismPurpose.name();
    }
    
    @Override
    public void setupParameters(ContactListContactMechanismPurposeDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListName(findParameter(request, ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName()));
        actionForm.setContactMechanismPurposeName(findParameter(request, ParameterConstants.CONTACT_MECHANISM_PURPOSE_NAME, actionForm.getContactMechanismPurposeName()));
    }
    
    @Override
    public void setupTransfer(ContactListContactMechanismPurposeDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetContactListContactMechanismPurposeForm commandForm = ContactListUtil.getHome().getGetContactListContactMechanismPurposeForm();
        
        commandForm.setContactListName(actionForm.getContactListName());
        commandForm.setContactMechanismPurposeName(actionForm.getContactMechanismPurposeName());
        
        CommandResult commandResult = ContactListUtil.getHome().getContactListContactMechanismPurpose(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetContactListContactMechanismPurposeResult result = (GetContactListContactMechanismPurposeResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CONTACT_LIST_CONTACT_MECHANISM_PURPOSE, result.getContactListContactMechanismPurpose());
        }
    }
    
    @Override
    public CommandResult doDelete(ContactListContactMechanismPurposeDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteContactListContactMechanismPurposeForm commandForm = ContactListUtil.getHome().getDeleteContactListContactMechanismPurposeForm();

        commandForm.setContactListName(actionForm.getContactListName());
        commandForm.setContactMechanismPurposeName(actionForm.getContactMechanismPurposeName());

        return ContactListUtil.getHome().deleteContactListContactMechanismPurpose(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(ContactListContactMechanismPurposeDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName());
    }
    
}
