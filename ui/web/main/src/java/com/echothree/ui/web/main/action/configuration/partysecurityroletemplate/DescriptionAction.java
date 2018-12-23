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

package com.echothree.ui.web.main.action.configuration.partysecurityroletemplate;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.GetPartySecurityRoleTemplateDescriptionsForm;
import com.echothree.control.user.security.common.result.GetPartySecurityRoleTemplateDescriptionsResult;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateTransfer;
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
    path = "/Configuration/PartySecurityRoleTemplate/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/partysecurityroletemplate/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            String partySecurityRoleTemplateName = request.getParameter(ParameterConstants.PARTY_SECURITY_ROLE_TEMPLATE_NAME);
            GetPartySecurityRoleTemplateDescriptionsForm commandForm = SecurityUtil.getHome().getGetPartySecurityRoleTemplateDescriptionsForm();
            
            commandForm.setPartySecurityRoleTemplateName(partySecurityRoleTemplateName);
            
            CommandResult commandResult = SecurityUtil.getHome().getPartySecurityRoleTemplateDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPartySecurityRoleTemplateDescriptionsResult result = (GetPartySecurityRoleTemplateDescriptionsResult)executionResult.getResult();
            PartySecurityRoleTemplateTransfer partySecurityRoleTemplateTransfer = result.getPartySecurityRoleTemplate();
            
            request.setAttribute(AttributeConstants.PARTY_SECURITY_ROLE_TEMPLATE, partySecurityRoleTemplateTransfer);
            request.setAttribute(AttributeConstants.PARTY_SECURITY_ROLE_TEMPLATE_NAME, partySecurityRoleTemplateTransfer.getPartySecurityRoleTemplateName());
            request.setAttribute(AttributeConstants.PARTY_SECURITY_ROLE_TEMPLATE_DESCRIPTIONS, result.getPartySecurityRoleTemplateDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}