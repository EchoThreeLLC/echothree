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

package com.echothree.ui.web.main.action.configuration.securityrole;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.DeleteSecurityRoleForm;
import com.echothree.control.user.security.common.form.GetSecurityRoleForm;
import com.echothree.control.user.security.common.result.GetSecurityRoleResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Configuration/SecurityRole/Delete",
    mappingClass = SecureActionMapping.class,
    name = "SecurityRoleDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SecurityRole/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/securityrole/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.SecurityRole.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setSecurityRoleGroupName(findParameter(request, ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName()));
        actionForm.setSecurityRoleName(findParameter(request, ParameterConstants.SECURITY_ROLE_NAME, actionForm.getSecurityRoleName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetSecurityRoleForm commandForm = SecurityUtil.getHome().getGetSecurityRoleForm();
        
        commandForm.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());
        commandForm.setSecurityRoleName(actionForm.getSecurityRoleName());
        
        CommandResult commandResult = SecurityUtil.getHome().getSecurityRole(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetSecurityRoleResult result = (GetSecurityRoleResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.SECURITY_ROLE, result.getSecurityRole());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteSecurityRoleForm commandForm = SecurityUtil.getHome().getDeleteSecurityRoleForm();

        commandForm.setSecurityRoleGroupName(actionForm.getSecurityRoleGroupName());
        commandForm.setSecurityRoleName(actionForm.getSecurityRoleName());

        return SecurityUtil.getHome().deleteSecurityRole(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName());
    }
    
}
