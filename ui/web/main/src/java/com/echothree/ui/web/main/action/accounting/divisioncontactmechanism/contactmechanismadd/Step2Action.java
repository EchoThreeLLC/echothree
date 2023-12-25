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

package com.echothree.ui.web.main.action.accounting.divisioncontactmechanism.contactmechanismadd;

import com.echothree.ui.web.main.action.accounting.divisioncontactmechanism.BaseDivisionContactMechanismAction;
import com.echothree.ui.web.main.framework.ForwardConstants;
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
    path = "/Accounting/DivisionContactMechanism/ContactMechanismAdd/Step2",
    mappingClass = SecureActionMapping.class,
    name = "DivisionContactMechanismAddStep2",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "POSTAL_ADDRESS", path = "/action/Accounting/DivisionContactMechanism/ContactMechanismAdd/ContactPostalAddressAdd", redirect = true),
        @SproutForward(name = "TELECOM_ADDRESS", path = "/action/Accounting/DivisionContactMechanism/ContactMechanismAdd/ContactTelephoneAdd", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/divisioncontactmechanism/contactmechanismadd/step2.jsp")
    }
)
public class Step2Action
        extends BaseDivisionContactMechanismAction<Step2ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, Step2ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        String contactMechanismTypeName = request.getParameter(ParameterConstants.CONTACT_MECHANISM_TYPE_NAME);
        String countryName = null;

        if(partyName == null) {
            partyName = actionForm.getPartyName();
        }
        if(contactMechanismTypeName == null) {
            contactMechanismTypeName = actionForm.getContactMechanismTypeName();
        }

        if(wasPost(request)) {
            countryName = actionForm.getCountryChoice();
            forwardKey = contactMechanismTypeName;
        } else {
            actionForm.setPartyName(partyName);
            actionForm.setContactMechanismTypeName(contactMechanismTypeName);
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupDivision(request, partyName);
        } else {
            Map<String, String> parameters = new HashMap<>(3);
            
            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            parameters.put(ParameterConstants.COUNTRY_NAME, countryName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}
