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

package com.echothree.ui.web.main.action.shipping.carriercontactmechanism.contactmechanismadd;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.form.CreateContactTelephoneForm;
import com.echothree.ui.web.main.action.shipping.carriercontactmechanism.BaseCarrierContactMechanismAction;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Shipping/CarrierContactMechanism/ContactMechanismAdd/ContactTelephoneAdd",
    mappingClass = SecureActionMapping.class,
    name = "CarrierContactTelephoneAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Shipping/CarrierContactMechanism/Main", redirect = true),
        @SproutForward(name = "Form", path = "/shipping/carriercontactmechanism/contactmechanismadd/contactTelephoneAdd.jsp")
    }
)
public class ContactTelephoneAddAction
        extends BaseCarrierContactMechanismAction<ContactTelephoneAddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ContactTelephoneAddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        String countryName = request.getParameter(ParameterConstants.COUNTRY_NAME);
        CreateContactTelephoneForm commandForm = ContactUtil.getHome().getCreateContactTelephoneForm();

        if(partyName == null) {
            partyName = actionForm.getPartyName();
        }
        if(countryName == null) {
            countryName = actionForm.getCountryName();
        }
        
        if(wasPost(request)) {
            commandForm.setPartyName(partyName);
            commandForm.setCountryName(countryName);
            commandForm.setAllowSolicitation(actionForm.getAllowSolicitation().toString());
            commandForm.setAreaCode(actionForm.getAreaCode());
            commandForm.setTelephoneNumber(actionForm.getTelephoneNumber());
            commandForm.setTelephoneExtension(actionForm.getTelephoneExtension());
            commandForm.setDescription(actionForm.getDescription());

            CommandResult commandResult = ContactUtil.getHome().createContactTelephone(getUserVisitPK(request), commandForm);

            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setPartyName(partyName);
            actionForm.setCountryName(countryName);
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupCarrier(request, partyName);
            setupCountry(request, countryName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}