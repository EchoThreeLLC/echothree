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

package com.echothree.ui.web.main.action.accounting.divisioncontactmechanism;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetDivisionForm;
import com.echothree.control.user.party.common.result.GetDivisionResult;
import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.DivisionTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.string.ContactPostalAddressUtils;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/DivisionContactMechanism/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/accounting/divisioncontactmechanism/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        GetDivisionForm commandForm = PartyUtil.getHome().getGetDivisionForm();
        String companyName = request.getParameter(ParameterConstants.COMPANY_NAME);
        String divisionName = request.getParameter(ParameterConstants.DIVISION_NAME);
        String partyName = request.getParameter(ParameterConstants.PARTY_NAME);
        
        commandForm.setCompanyName(companyName);
        commandForm.setDivisionName(divisionName);
        commandForm.setPartyName(partyName);
        
        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyIncludePartyContactMechanisms);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismPurposes);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismRelationshipsByFromPartyContactMechanism);
        commandForm.setOptions(ContactPostalAddressUtils.getInstance().addOptions(options));
        
        CommandResult commandResult = PartyUtil.getHome().getDivision(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetDivisionResult result = (GetDivisionResult)executionResult.getResult();
        DivisionTransfer division = result.getDivision();
        
        if(division == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.DIVISION, division);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}