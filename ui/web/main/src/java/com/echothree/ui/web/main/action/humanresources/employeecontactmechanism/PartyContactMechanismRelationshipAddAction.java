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

package com.echothree.ui.web.main.action.humanresources.employeecontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.form.CreatePartyContactMechanismRelationshipForm;
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
    path = "/HumanResources/EmployeeContactMechanism/PartyContactMechanismRelationshipAdd",
    mappingClass = SecureActionMapping.class,
    name = "EmployeePartyContactMechanismRelationshipAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/EmployeeContactMechanism/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employeecontactmechanism/partyContactMechanismRelationshipAdd.jsp")
    }
)
public class PartyContactMechanismRelationshipAddAction
        extends BaseEmployeeContactMechanismAction<PartyContactMechanismRelationshipAddActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, PartyContactMechanismRelationshipAddActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        CreatePartyContactMechanismRelationshipForm commandForm = ContactUtil.getHome().getCreatePartyContactMechanismRelationshipForm();
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        String fromContactMechanismName = request.getParameter(ParameterConstants.FROM_CONTACT_MECHANISM_NAME);
        
        if(partyName == null) {
            partyName = actionForm.getPartyName();
        }
        if(fromContactMechanismName == null) {
            fromContactMechanismName = actionForm.getFromContactMechanismName();
        }
        
        if(wasPost(request)) {
            commandForm.setPartyName(partyName);
            commandForm.setFromContactMechanismName(fromContactMechanismName);
            commandForm.setToContactMechanismName(actionForm.getToContactMechanismChoice());
            
            CommandResult commandResult = ContactUtil.getHome().createPartyContactMechanismRelationship(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setPartyName(partyName);
            actionForm.setFromContactMechanismName(fromContactMechanismName);
            forwardKey = ForwardConstants.FORM;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            setupEmployee(request, partyName);
            setupPartyContactMechanismTransfer(request, partyName, fromContactMechanismName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.PARTY_NAME, partyName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}