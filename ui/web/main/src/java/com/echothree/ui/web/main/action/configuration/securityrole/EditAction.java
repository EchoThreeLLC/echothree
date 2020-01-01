// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.security.common.edit.SecurityRoleEdit;
import com.echothree.control.user.security.common.form.EditSecurityRoleForm;
import com.echothree.control.user.security.common.result.EditSecurityRoleResult;
import com.echothree.control.user.security.common.spec.SecurityRoleSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/SecurityRole/Edit",
    mappingClass = SecureActionMapping.class,
    name = "SecurityRoleEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SecurityRole/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/securityrole/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, SecurityRoleSpec, SecurityRoleEdit, EditSecurityRoleForm, EditSecurityRoleResult> {
    
    @Override
    protected SecurityRoleSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        SecurityRoleSpec spec = SecurityUtil.getHome().getSecurityRoleSpec();
        
        spec.setSecurityRoleGroupName(findParameter(request, ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName()));
        spec.setSecurityRoleName(findParameter(request, ParameterConstants.ORIGINAL_SECURITY_ROLE_NAME, actionForm.getOriginalSecurityRoleName()));
        
        return spec;
    }
    
    @Override
    protected SecurityRoleEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        SecurityRoleEdit edit = SecurityUtil.getHome().getSecurityRoleEdit();

        edit.setSecurityRoleName(actionForm.getSecurityRoleName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditSecurityRoleForm getForm()
            throws NamingException {
        return SecurityUtil.getHome().getEditSecurityRoleForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditSecurityRoleResult result, SecurityRoleSpec spec, SecurityRoleEdit edit) {
        actionForm.setSecurityRoleGroupName(spec.getSecurityRoleGroupName());
        actionForm.setOriginalSecurityRoleName(spec.getSecurityRoleName());
        actionForm.setSecurityRoleName(edit.getSecurityRoleName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSecurityRoleForm commandForm)
            throws Exception {
        return SecurityUtil.getHome().editSecurityRole(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditSecurityRoleResult result) {
        request.setAttribute(AttributeConstants.SECURITY_ROLE, result.getSecurityRole());
    }

}
