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

package com.echothree.ui.web.main.action.configuration.partysecurityroletemplaterole.Add;


import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.GetPartySecurityRoleTemplateForm;
import com.echothree.control.user.security.common.form.GetSecurityRoleGroupForm;
import com.echothree.control.user.security.common.form.GetSecurityRoleGroupsForm;
import com.echothree.control.user.security.common.form.GetSecurityRolesForm;
import com.echothree.control.user.security.common.result.GetPartySecurityRoleTemplateResult;
import com.echothree.control.user.security.common.result.GetSecurityRoleGroupResult;
import com.echothree.control.user.security.common.result.GetSecurityRoleGroupsResult;
import com.echothree.control.user.security.common.result.GetSecurityRolesResult;
import com.echothree.model.control.security.common.SecurityOptions;
import com.echothree.model.control.security.common.transfer.PartySecurityRoleTemplateTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleGroupTransfer;
import com.echothree.model.control.security.common.transfer.SecurityRoleTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
import java.util.List;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/PartySecurityRoleTemplateRole/Add/Step1",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Form", path = "/configuration/partysecurityroletemplaterole/add/step1.jsp")
    }
)
public class Step1Action
        extends MainBaseAction<ActionForm> {
    
    public PartySecurityRoleTemplateTransfer getPartySecurityRoleTemplate(UserVisitPK userVisitPK, String partySecurityRoleTemplateName)
            throws NamingException {
        GetPartySecurityRoleTemplateForm commandForm = SecurityUtil.getHome().getGetPartySecurityRoleTemplateForm();

        commandForm.setPartySecurityRoleTemplateName(partySecurityRoleTemplateName);

        CommandResult commandResult = SecurityUtil.getHome().getPartySecurityRoleTemplate(userVisitPK, commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartySecurityRoleTemplateResult result = (GetPartySecurityRoleTemplateResult)executionResult.getResult();

        return result.getPartySecurityRoleTemplate();
    }

    public SecurityRoleGroupTransfer getSecurityRoleGroup(UserVisitPK userVisitPK, String securityRoleGroupName)
            throws NamingException {
        GetSecurityRoleGroupForm commandForm = SecurityUtil.getHome().getGetSecurityRoleGroupForm();

        commandForm.setSecurityRoleGroupName(securityRoleGroupName);

        CommandResult commandResult = SecurityUtil.getHome().getSecurityRoleGroup(userVisitPK, commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetSecurityRoleGroupResult result = (GetSecurityRoleGroupResult)executionResult.getResult();

        return result.getSecurityRoleGroup();
    }

    public List<SecurityRoleGroupTransfer> getSecurityRoleGroups(UserVisitPK userVisitPK, String parentSecrurityRoleGroupName)
            throws NamingException {
        GetSecurityRoleGroupsForm commandForm = SecurityUtil.getHome().getGetSecurityRoleGroupsForm();

        commandForm.setParentSecurityRoleGroupName(parentSecrurityRoleGroupName);

        Set<String> options = new HashSet<>();
        options.add(SecurityOptions.SecurityRoleGroupIncludeSecurityRolesCount);
        commandForm.setOptions(options);

        CommandResult commandResult = SecurityUtil.getHome().getSecurityRoleGroups(userVisitPK, commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetSecurityRoleGroupsResult result = (GetSecurityRoleGroupsResult)executionResult.getResult();

        return result.getSecurityRoleGroups();
    }

    public List<SecurityRoleTransfer> getSecurityRoles(UserVisitPK userVisitPK, String securityRoleGroupName)
            throws NamingException {
        GetSecurityRolesForm commandForm = SecurityUtil.getHome().getGetSecurityRolesForm();
        
        commandForm.setSecurityRoleGroupName(securityRoleGroupName);
        
        CommandResult commandResult = SecurityUtil.getHome().getSecurityRoles(userVisitPK, commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetSecurityRolesResult result = (GetSecurityRolesResult)executionResult.getResult();
        
        return result.getSecurityRoles();
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        
        try {
            UserVisitPK userVisitPK = getUserVisitPK(request);
            String partySecurityRoleTemplateName = request.getParameter(ParameterConstants.PARTY_SECURITY_ROLE_TEMPLATE_NAME);
            String parentSecurityRoleGroupName = request.getParameter(ParameterConstants.PARENT_SECURITY_ROLE_GROUP_NAME);
            List<SecurityRoleGroupTransfer> securityRoleGroups = getSecurityRoleGroups(userVisitPK, parentSecurityRoleGroupName);
            List<SecurityRoleTransfer> securityRoles = getSecurityRoles(userVisitPK, parentSecurityRoleGroupName);
            
            request.setAttribute(AttributeConstants.PARTY_SECURITY_ROLE_TEMPLATE, getPartySecurityRoleTemplate(userVisitPK, partySecurityRoleTemplateName));
            request.setAttribute(AttributeConstants.PARENT_SECURITY_ROLE_GROUP, getSecurityRoleGroup(userVisitPK, parentSecurityRoleGroupName));
            request.setAttribute(AttributeConstants.SECURITY_ROLE_GROUPS, securityRoleGroups.isEmpty()? null: securityRoleGroups);
            request.setAttribute(AttributeConstants.SECURITY_ROLES, securityRoles.isEmpty()? null: securityRoles);
            
            forwardKey = ForwardConstants.FORM;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
