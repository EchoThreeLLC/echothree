// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Customer/Customer/CustomerProfileAdd",
    mappingClass = SecureActionMapping.class,
    name = "CustomerProfileAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/Customer/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customer/customerProfileAdd.jsp")
    }
)
public class CustomerProfileAddAction
        extends MainBaseAction<CustomerProfileAddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, CustomerProfileAddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        var customerName = request.getParameter(ParameterConstants.CUSTOMER_NAME);
        
        if(partyName == null) {
            partyName = actionForm.getPartyName();
        }
        if(customerName == null) {
            customerName = actionForm.getCustomerName();
        }
        
        if(wasPost(request)) {
            var commandForm = PartyUtil.getHome().getCreateProfileForm();
            
            commandForm.setPartyName(actionForm.getPartyName());
            commandForm.setNickname(actionForm.getNickname());
            commandForm.setIconName(actionForm.getIconChoice());
            commandForm.setPronunciation(actionForm.getPronunciation());
            commandForm.setGenderName(actionForm.getGenderChoice());
            commandForm.setPronouns(actionForm.getPronouns());
            commandForm.setBirthday(actionForm.getBirthday());
            commandForm.setBirthdayFormatName(actionForm.getBirthdayFormatChoice());
            commandForm.setOccupation(actionForm.getOccupation());
            commandForm.setHobbies(actionForm.getHobbies());
            commandForm.setLocation(actionForm.getLocation());
            commandForm.setBioMimeTypeName(actionForm.getBioMimeTypeChoice());
            commandForm.setBio(actionForm.getBio());
            commandForm.setSignatureMimeTypeName(actionForm.getSignatureMimeTypeChoice());
            commandForm.setSignature(actionForm.getSignature());

            var commandResult = PartyUtil.getHome().createProfile(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setPartyName(partyName);
            actionForm.setCustomerName(customerName);
            forwardKey = ForwardConstants.FORM;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.CUSTOMER_NAME, customerName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.CUSTOMER_NAME, customerName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}