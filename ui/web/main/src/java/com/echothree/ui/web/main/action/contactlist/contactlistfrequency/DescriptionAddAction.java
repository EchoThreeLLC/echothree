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

package com.echothree.ui.web.main.action.contactlist.contactlistfrequency;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.form.CreateContactListFrequencyDescriptionForm;
import com.echothree.control.user.contactlist.common.form.GetContactListFrequencyForm;
import com.echothree.control.user.contactlist.common.result.GetContactListFrequencyResult;
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
    path = "/ContactList/ContactListFrequency/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "ContactListFrequencyDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListFrequency/Description", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlistfrequency/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListFrequencyName(findParameter(request, ParameterConstants.CONTACT_LIST_FREQUENCY_NAME, actionForm.getContactListFrequencyName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetContactListFrequencyForm commandForm = ContactListUtil.getHome().getGetContactListFrequencyForm();

        commandForm.setContactListFrequencyName(actionForm.getContactListFrequencyName());
        
        CommandResult commandResult = ContactListUtil.getHome().getContactListFrequency(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetContactListFrequencyResult result = (GetContactListFrequencyResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.CONTACT_LIST_FREQUENCY, result.getContactListFrequency());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateContactListFrequencyDescriptionForm commandForm = ContactListUtil.getHome().getCreateContactListFrequencyDescriptionForm();

        commandForm.setContactListFrequencyName( actionForm.getContactListFrequencyName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return ContactListUtil.getHome().createContactListFrequencyDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_FREQUENCY_NAME, actionForm.getContactListFrequencyName());
    }
    
}
