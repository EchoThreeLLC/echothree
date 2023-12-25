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

package com.echothree.ui.web.main.action.accounting.companycontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.form.DeletePartyContactMechanismPurposeForm;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/CompanyContactMechanism/PartyContactMechanismPurposeDelete",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Review", path = "/action/Accounting/CompanyContactMechanism/Main", redirect = true)
    }
)
public class PartyContactMechanismPurposeDeleteAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DeletePartyContactMechanismPurposeForm commandForm = ContactUtil.getHome().getDeletePartyContactMechanismPurposeForm();
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        String contactMechanismName = request.getParameter(ParameterConstants.CONTACT_MECHANISM_NAME);
        String contactMechanismPurposeName = request.getParameter(ParameterConstants.CONTACT_MECHANISM_PURPOSE_NAME);
        
        commandForm.setPartyName(partyName);
        commandForm.setContactMechanismName(contactMechanismName);
        commandForm.setContactMechanismPurposeName(contactMechanismPurposeName);
        
        ContactUtil.getHome().deletePartyContactMechanismPurpose(getUserVisitPK(request), commandForm);
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(ForwardConstants.REVIEW));
        Map<String, String> parameters = new HashMap<>(1);
        
        parameters.put(ParameterConstants.PARTY_NAME, partyName);
        customActionForward.setParameters(parameters);
        
        return customActionForward;
    }
    
}
