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

package com.echothree.ui.web.main.action.configuration.partytypelockoutpolicy;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetPartyTypeForm;
import com.echothree.control.user.party.common.result.GetPartyTypeResult;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.PartyTypeLockoutPolicyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Configuration/PartyTypeLockoutPolicy/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/partytypelockoutpolicy/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        GetPartyTypeForm commandForm = PartyUtil.getHome().getGetPartyTypeForm();
        String partyTypeName = request.getParameter(ParameterConstants.PARTY_TYPE_NAME);
        
        commandForm.setPartyTypeName(partyTypeName);
        
        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyTypeIncludeLockoutPolicy);
        commandForm.setOptions(options);
        
        CommandResult commandResult = PartyUtil.getHome().getPartyType(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyTypeResult result = (GetPartyTypeResult)executionResult.getResult();
        PartyTypeTransfer partyType = result.getPartyType();
        PartyTypeLockoutPolicyTransfer partyTypeLockoutPolicy = partyType == null? null: partyType.getPartyTypeLockoutPolicy();
        
        if(partyTypeLockoutPolicy == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.PARTY_TYPE_LOCKOUT_POLICY, partyTypeLockoutPolicy);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
