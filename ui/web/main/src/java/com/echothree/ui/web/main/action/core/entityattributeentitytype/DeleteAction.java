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

package com.echothree.ui.web.main.action.core.entityattributeentitytype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeEntityTypeForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeEntityTypeForm;
import com.echothree.control.user.core.common.result.GetEntityAttributeEntityTypeResult;
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
    path = "/Core/EntityAttributeEntityType/Delete",
    mappingClass = SecureActionMapping.class,
    name = "EntityAttributeEntityTypeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAttributeEntityType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityattributeentitytype/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.EntityAttributeEntityType.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        actionForm.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
        actionForm.setEntityAttributeName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName()));
        actionForm.setAllowedComponentVendorName(findParameter(request, ParameterConstants.ALLOWED_COMPONENT_VENDOR_NAME, actionForm.getAllowedComponentVendorName()));
        actionForm.setAllowedEntityTypeName(findParameter(request, ParameterConstants.ALLOWED_ENTITY_TYPE_NAME, actionForm.getAllowedEntityTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetEntityAttributeEntityTypeForm commandForm = CoreUtil.getHome().getGetEntityAttributeEntityTypeForm();
        
        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setEntityAttributeName(actionForm.getEntityAttributeName());
        commandForm.setAllowedComponentVendorName(actionForm.getAllowedComponentVendorName());
        commandForm.setAllowedEntityTypeName(actionForm.getAllowedEntityTypeName());
        
        CommandResult commandResult = CoreUtil.getHome().getEntityAttributeEntityType(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetEntityAttributeEntityTypeResult result = (GetEntityAttributeEntityTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.ENTITY_ATTRIBUTE_ENTITY_TYPE, result.getEntityAttributeEntityType());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteEntityAttributeEntityTypeForm commandForm = CoreUtil.getHome().getDeleteEntityAttributeEntityTypeForm();

        commandForm.setEntityAttributeName(actionForm.getEntityAttributeName());
        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());
        commandForm.setAllowedComponentVendorName(actionForm.getAllowedComponentVendorName());
        commandForm.setAllowedEntityTypeName(actionForm.getAllowedEntityTypeName());

        return CoreUtil.getHome().deleteEntityAttributeEntityType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
        parameters.put(ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName());
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName());
    }
    
}
