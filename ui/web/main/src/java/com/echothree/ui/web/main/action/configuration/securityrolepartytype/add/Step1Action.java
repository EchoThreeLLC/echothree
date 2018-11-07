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

package com.echothree.ui.web.main.action.configuration.securityrolepartytype.add;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetPartyTypesForm;
import com.echothree.control.user.party.common.result.GetPartyTypesResult;
import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.GetSecurityRoleForm;
import com.echothree.control.user.security.common.result.GetSecurityRoleResult;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/SecurityRolePartyType/Add/Step1",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/securityrolepartytype/add/step1.jsp")
    }
)
public class Step1Action
        extends MainBaseAction<ActionForm> {

    public void setupTransfer(HttpServletRequest request)
            throws NamingException {
        GetSecurityRoleForm commandForm = SecurityUtil.getHome().getGetSecurityRoleForm();

        commandForm.setSecurityRoleGroupName(request.getParameter(ParameterConstants.SECURITY_ROLE_GROUP_NAME));
        commandForm.setSecurityRoleName(request.getParameter(ParameterConstants.SECURITY_ROLE_NAME));
        
        CommandResult commandResult = SecurityUtil.getHome().getSecurityRole(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetSecurityRoleResult result = (GetSecurityRoleResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.SECURITY_ROLE, result.getSecurityRole());
        }
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        GetPartyTypesForm commandForm = PartyUtil.getHome().getGetPartyTypesForm();
        CommandResult commandResult = PartyUtil.getHome().getPartyTypes(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyTypesResult result = (GetPartyTypesResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_TYPES, result.getPartyTypes());

        setupTransfer(request);
        
        return mapping.findForward(ForwardConstants.DISPLAY);
    }

}
