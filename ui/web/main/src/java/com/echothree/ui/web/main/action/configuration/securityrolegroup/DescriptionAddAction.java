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

package com.echothree.ui.web.main.action.configuration.securityrolegroup;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.CreateSecurityRoleGroupDescriptionForm;
import com.echothree.control.user.security.common.form.GetSecurityRoleGroupForm;
import com.echothree.control.user.security.common.result.GetSecurityRoleGroupResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/SecurityRoleGroup/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "SecurityRoleGroupDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SecurityRoleGroup/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/securityrolegroup/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setSecurityRoleGroupName(findParameter(request, ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetSecurityRoleGroupForm commandForm = SecurityUtil.getHome().getGetSecurityRoleGroupForm();

        commandForm.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());
        
        CommandResult commandResult = SecurityUtil.getHome().getSecurityRoleGroup(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetSecurityRoleGroupResult result = (GetSecurityRoleGroupResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.SECURITY_ROLE_GROUP, result.getSecurityRoleGroup());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CreateSecurityRoleGroupDescriptionForm commandForm = SecurityUtil.getHome().getCreateSecurityRoleGroupDescriptionForm();

        commandForm.setSecurityRoleGroupName( actionForm.getSecurityRoleGroupName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return SecurityUtil.getHome().createSecurityRoleGroupDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName());
    }
    
}
