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

package com.echothree.ui.web.main.action.configuration.partysecurityroletemplatetrainingclass;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.form.DeletePartySecurityRoleTemplateTrainingClassForm;
import com.echothree.control.user.security.common.form.GetPartySecurityRoleTemplateTrainingClassForm;
import com.echothree.control.user.security.common.result.GetPartySecurityRoleTemplateTrainingClassResult;
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
    path = "/Configuration/PartySecurityRoleTemplateTrainingClass/Delete",
    mappingClass = SecureActionMapping.class,
    name = "PartySecurityRoleTemplateTrainingClassDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartySecurityRoleTemplateTrainingClass/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partysecurityroletemplatetrainingclass/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.PartySecurityRoleTemplateTrainingClass.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartySecurityRoleTemplateName(findParameter(request, ParameterConstants.PARTY_SECURITY_ROLE_TEMPLATE_NAME, actionForm.getPartySecurityRoleTemplateName()));
        actionForm.setTrainingClassName(findParameter(request, ParameterConstants.TRAINING_CLASS_NAME, actionForm.getTrainingClassName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetPartySecurityRoleTemplateTrainingClassForm commandForm = SecurityUtil.getHome().getGetPartySecurityRoleTemplateTrainingClassForm();
        
        commandForm.setPartySecurityRoleTemplateName(actionForm.getPartySecurityRoleTemplateName());
        commandForm.setTrainingClassName(actionForm.getTrainingClassName());
        
        CommandResult commandResult = SecurityUtil.getHome().getPartySecurityRoleTemplateTrainingClass(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPartySecurityRoleTemplateTrainingClassResult result = (GetPartySecurityRoleTemplateTrainingClassResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.PARTY_SECURITY_ROLE_TEMPLATE_TRAINING_CLASS, result.getPartySecurityRoleTemplateTrainingClass());
        }
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeletePartySecurityRoleTemplateTrainingClassForm commandForm = SecurityUtil.getHome().getDeletePartySecurityRoleTemplateTrainingClassForm();

        commandForm.setPartySecurityRoleTemplateName(actionForm.getPartySecurityRoleTemplateName());
        commandForm.setTrainingClassName(actionForm.getTrainingClassName());

        return SecurityUtil.getHome().deletePartySecurityRoleTemplateTrainingClass(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_SECURITY_ROLE_TEMPLATE_NAME, actionForm.getPartySecurityRoleTemplateName());
    }
    
}
