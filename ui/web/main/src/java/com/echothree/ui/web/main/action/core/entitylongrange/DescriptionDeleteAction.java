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

package com.echothree.ui.web.main.action.core.entitylongrange;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.DeleteEntityLongRangeDescriptionForm;
import com.echothree.control.user.core.common.form.GetEntityLongRangeDescriptionForm;
import com.echothree.control.user.core.common.result.GetEntityLongRangeDescriptionResult;
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
    path = "/Core/EntityLongRange/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "EntityLongRangeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityLongRange/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/entitylongrange/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.EntityLongRangeDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        actionForm.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
        actionForm.setEntityAttributeName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName()));
        actionForm.setEntityLongRangeName(findParameter(request, ParameterConstants.ENTITY_LONG_RANGE_NAME, actionForm.getEntityLongRangeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetEntityLongRangeDescriptionForm commandForm = CoreUtil.getHome().getGetEntityLongRangeDescriptionForm();
        
        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setEntityLongRangeName(actionForm.getEntityLongRangeName());
        commandForm.setEntityAttributeName(actionForm.getEntityAttributeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());
        
        CommandResult commandResult = CoreUtil.getHome().getEntityLongRangeDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetEntityLongRangeDescriptionResult result = (GetEntityLongRangeDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.ENTITY_LONG_RANGE_DESCRIPTION, result.getEntityLongRangeDescription());
        }
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteEntityLongRangeDescriptionForm commandForm = CoreUtil.getHome().getDeleteEntityLongRangeDescriptionForm();

        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setEntityAttributeName(actionForm.getEntityAttributeName());
        commandForm.setEntityLongRangeName(actionForm.getEntityLongRangeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return CoreUtil.getHome().deleteEntityLongRangeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
        parameters.put(ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName());
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName());
        parameters.put(ParameterConstants.ENTITY_LONG_RANGE_NAME, actionForm.getEntityLongRangeName());
    }
    
}
