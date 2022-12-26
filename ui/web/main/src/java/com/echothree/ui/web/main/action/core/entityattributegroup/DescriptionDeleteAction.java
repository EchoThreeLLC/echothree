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

package com.echothree.ui.web.main.action.core.entityattributegroup;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeGroupDescriptionForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeGroupDescriptionForm;
import com.echothree.control.user.core.common.result.GetEntityAttributeGroupDescriptionResult;
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
    path = "/Core/EntityAttributeGroup/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "EntityAttributeGroupDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAttributeGroup/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityattributegroup/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.EntityAttributeGroupDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setEntityAttributeGroupName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_GROUP_NAME, actionForm.getEntityAttributeGroupName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetEntityAttributeGroupDescriptionForm commandForm = CoreUtil.getHome().getGetEntityAttributeGroupDescriptionForm();
        
        commandForm.setEntityAttributeGroupName(actionForm.getEntityAttributeGroupName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = CoreUtil.getHome().getEntityAttributeGroupDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEntityAttributeGroupDescriptionResult result = (GetEntityAttributeGroupDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.ENTITY_ATTRIBUTE_GROUP_DESCRIPTION, result.getEntityAttributeGroupDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteEntityAttributeGroupDescriptionForm commandForm = CoreUtil.getHome().getDeleteEntityAttributeGroupDescriptionForm();

        commandForm.setEntityAttributeGroupName(actionForm.getEntityAttributeGroupName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return CoreUtil.getHome().deleteEntityAttributeGroupDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_GROUP_NAME, actionForm.getEntityAttributeGroupName());
    }
    
}
