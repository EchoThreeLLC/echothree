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

package com.echothree.ui.web.main.action.core.entityintegerrange;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.CreateEntityIntegerRangeDescriptionForm;
import com.echothree.control.user.core.common.form.GetEntityIntegerRangeForm;
import com.echothree.control.user.core.common.result.GetEntityIntegerRangeResult;
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
    path = "/Core/EntityIntegerRange/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "CoreEntityIntegerRangeDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityIntegerRange/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityintegerrange/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        actionForm.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
        actionForm.setEntityAttributeName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName()));
        actionForm.setEntityIntegerRangeName(findParameter(request, ParameterConstants.ENTITY_INTEGER_RANGE_NAME, actionForm.getEntityIntegerRangeName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetEntityIntegerRangeForm();

        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setEntityAttributeName(actionForm.getEntityAttributeName());
        commandForm.setEntityIntegerRangeName(actionForm.getEntityIntegerRangeName());

        var commandResult = CoreUtil.getHome().getEntityIntegerRange(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEntityIntegerRangeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.ENTITY_INTEGER_RANGE, result.getEntityIntegerRange());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getCreateEntityIntegerRangeDescriptionForm();

        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setEntityAttributeName(actionForm.getEntityAttributeName());
        commandForm.setEntityIntegerRangeName(actionForm.getEntityIntegerRangeName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return CoreUtil.getHome().createEntityIntegerRangeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
        parameters.put(ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName());
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName());
        parameters.put(ParameterConstants.ENTITY_INTEGER_RANGE_NAME, actionForm.getEntityIntegerRangeName());
    }
    
}
