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

package com.echothree.ui.web.main.action.configuration.securityrolegroup;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.edit.SecurityRoleGroupDescriptionEdit;
import com.echothree.control.user.security.common.form.EditSecurityRoleGroupDescriptionForm;
import com.echothree.control.user.security.common.result.EditSecurityRoleGroupDescriptionResult;
import com.echothree.control.user.security.common.spec.SecurityRoleGroupDescriptionSpec;
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
    path = "/Configuration/SecurityRoleGroup/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "SecurityRoleGroupDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/SecurityRoleGroup/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/securityrolegroup/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, SecurityRoleGroupDescriptionSpec, SecurityRoleGroupDescriptionEdit, EditSecurityRoleGroupDescriptionForm, EditSecurityRoleGroupDescriptionResult> {
    
    @Override
    protected SecurityRoleGroupDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        SecurityRoleGroupDescriptionSpec spec = SecurityUtil.getHome().getSecurityRoleGroupDescriptionSpec();
        
        spec.setSecurityRoleGroupName(findParameter(request, ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected SecurityRoleGroupDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        SecurityRoleGroupDescriptionEdit edit = SecurityUtil.getHome().getSecurityRoleGroupDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditSecurityRoleGroupDescriptionForm getForm()
            throws NamingException {
        return SecurityUtil.getHome().getEditSecurityRoleGroupDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditSecurityRoleGroupDescriptionResult result, SecurityRoleGroupDescriptionSpec spec, SecurityRoleGroupDescriptionEdit edit) {
        actionForm.setSecurityRoleGroupName(spec.getSecurityRoleGroupName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSecurityRoleGroupDescriptionForm commandForm)
            throws Exception {
        return SecurityUtil.getHome().editSecurityRoleGroupDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SECURITY_ROLE_GROUP_NAME, actionForm.getSecurityRoleGroupName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditSecurityRoleGroupDescriptionResult result) {
        request.setAttribute(AttributeConstants.SECURITY_ROLE_GROUP_DESCRIPTION, result.getSecurityRoleGroupDescription());
    }

}
